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

import java.util.List;
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
    public Voca createVoca(VocaDO vocaDO, Long userId) throws ParseException {

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
    public String findVoca(VocaDO vocaDO) throws ParseException {
        String mean = apiService.getMean(vocaDO.getWord(), vocaDO.getSource());

        return mean;
    }

    @Override
    public HSSFWorkbook download(Long userId) {
        List<Voca> allWordsByUserId = userRepository.findAllWordsByUserId(userId);
        return fileMaker.createFile(allWordsByUserId);

    }


}
