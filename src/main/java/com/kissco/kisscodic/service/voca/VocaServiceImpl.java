package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.service.ApiService;
import com.kissco.kisscodic.common.file_maker.FileMaker;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.repository.user.UserRepository;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.repository.voca.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VocaServiceImpl implements VocaService{

    private final VocaRepository vocaRepository;
    private final ApiService apiService;
    private final FileMaker fileMaker;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Voca createVoca(VocaDO vocaDO, Long userId)  {

        Optional<Voca> isExist;

        if(vocaDO.getSource().equals("ko")){
            isExist = vocaRepository.findByWord(vocaDO.getWord());
        }
        else  {
            isExist = vocaRepository.findByMean(vocaDO.getWord());
        }

        Voca voca = null;
        User user = userRepository.findById(userId).get(0);

        if (isExist.isEmpty()) {

            String foundMean = apiService.getMean(vocaDO.getWord(), vocaDO.getSource());

            voca = VocaDO.createVoca(vocaDO, foundMean);

            user.addUserVoca(voca);
            vocaRepository.save(voca);
        }
        else {
            voca =  isExist.get();
            List<Voca> vocas =userRepository.findWord(userId, voca.getWord());
            if(vocas.isEmpty()){
                user.addUserVoca(voca);
                vocaRepository.save(voca);
            }
        }

        return voca;
    }

    @Override
    @Transactional
    public Voca createMyVoca(VocaDO vocaDO, Long userId) {
        Optional<Voca> isExist = vocaRepository.findByWord(vocaDO.getWord());

        User user = userRepository.findById(userId).get(0);

        Voca voca ;
        if (isExist.isEmpty()) {
            voca = VocaDO.createMyVoca(vocaDO);
            user.addUserVoca(voca);
            vocaRepository.save(voca);
        }
        else {
            voca =  isExist.get();
            List<Voca> vocas =userRepository.findWord(userId, voca.getWord());
            if(vocas.isEmpty()){
                user.addUserVoca(voca);
                vocaRepository.save(voca);
            }
        }
        return voca;
    }

    @Override
    public Map<String,String> findVoca(String word ,String source) {
        String mean = apiService.getMean(word, source);

        Map<String, String> returnJson = new HashMap<>();
        returnJson.put("mean", mean);

        return returnJson;
    }

    @Override
    public HSSFWorkbook download(String email) {
        List<Voca> allWordsByUserId = userRepository.findAllWordsByUserId(email);
        return fileMaker.createFile(allWordsByUserId);
    }


}
