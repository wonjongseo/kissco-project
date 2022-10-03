package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.entity.UserVoca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import com.kissco.kisscodic.repository.user_voca.UserVocaRepository;
import com.kissco.kisscodic.service.ApiService;
import com.kissco.kisscodic.common.file_maker.FileMaker;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.repository.user.UserRepository;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.repository.voca.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VocaServiceImpl implements VocaService {

    private final VocaRepository vocaRepository;
    private final ApiService apiService;
    private final FileMaker fileMaker;
    private final UserRepository userRepository;
    private final UserVocaRepository userVocaRepository;



    @Transactional
    public Voca createVoca(VocaDO vocaDO, Long userId)  {

        Optional<Voca> isExist;



        String foundMean ;
        if(VocaDO.isKorea(vocaDO.getWord())){
            foundMean= apiService.getMean(vocaDO.getWord(), "ko");
            isExist = vocaRepository.findByWordAndMean(vocaDO.getWord(),foundMean);
        }
        else  {
            foundMean= apiService.getMean(vocaDO.getWord(), "ja");
            isExist = vocaRepository.findByMeanAndWord(vocaDO.getWord(),foundMean);
        }

        Voca voca = null;
        User user = userRepository.findUserById(userId).get(0);

        if (isExist.isEmpty()) {
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


    @Transactional
    public Voca createMyVoca(VocaDO vocaDO, Long userId) {

        if(userVocaRepository.IsExistMyVoca(userId, vocaDO.getWord(), vocaDO.getMean()).isPresent()){
            UserVoca userVoca = userVocaRepository.IsExistMyVoca(userId, vocaDO.getWord(), vocaDO.getMean()).get();
            throw new CustomException(ErrorCode.DUPLICATE_MY_VOCA);
        }


        User user = userRepository.findUserById(userId).get(0);
        Voca newVoca = VocaDO.createMyVoca(vocaDO);

        user.addUserVoca(newVoca);
        vocaRepository.save(newVoca);
        return newVoca;
    }

    public Map<String,String> findVoca(String word ) {

        String foundMean ;
        if(VocaDO.isKorea(word)){
            foundMean= apiService.getMean(word, "ko");
        }
        else {
            foundMean= apiService.getMean(word, "ja");
        }

        Map<String, String> returnJson = new HashMap<>();
        returnJson.put("mean", foundMean);

        return returnJson;
    }
//    public Map<String,String> findVoca(String word ,String source) {
//        String mean = apiService.getMean(word, source);
//
//        Map<String, String> returnJson = new HashMap<>();
//        returnJson.put("mean", mean);
//
//        return returnJson;
//    }

    public HSSFWorkbook download(Long userId) {
        List<Voca> allWordsByUserId = userRepository.findAllWordsByUserId(userId);
        return fileMaker.createFile(allWordsByUserId);
    }
}
