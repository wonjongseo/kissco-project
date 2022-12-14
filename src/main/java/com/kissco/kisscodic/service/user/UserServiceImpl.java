package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.voca.VocaResponseDTO;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.UserVoca;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import com.kissco.kisscodic.repository.user.UserRepository;
import com.kissco.kisscodic.repository.user_voca.UserVocaRepository;
import com.kissco.kisscodic.repository.voca.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserVocaRepository userVocaRepository;
    private final VocaRepository vocaRepository;

    @Override
    public List<VocaResponseDTO> findAllWordsByUserIdWherePage(Long userId, Integer page, String sort, Boolean isKnown) {
        if(isKnown == null)
            return userRepository.findVocas(userId, page,sort);

        isValidateFormForVocaList(userId, isKnown, page);
        return userRepository.findVocas(userId, page,sort, isKnown);

    }

    @Override
    @Transactional
    public Long deleteVoca(Long userId, Long vocaId) {

        Optional<UserVoca> isUserVoca = userVocaRepository.findByUserIdAndVocaId(userId, vocaId);

        if (isUserVoca.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_VOCA);
        }

        UserVoca userVoca = isUserVoca.get();
        userVocaRepository.deleteById(userVoca.getId());

        return userVoca.getId();
    }

    @Override
    @Transactional
    public int deleteAllVoca(Long userId) {
        List<UserVoca> userVocas =  userVocaRepository.findVocasByUserId(userId);

        int size = userVocas.size();

        for (UserVoca uv: userVocas){
            userVocaRepository.deleteById(uv.getId());
            if(uv.getVoca().isMine()) {
                Voca voca = uv.getVoca();
                vocaRepository.deleteById(voca.getId());
            }
        }

        return size;
    }

    @Override
    public List<Voca> test(Long userId,  Integer cnt, Boolean isKnown) {

        isValidateFormForTest(userId, isKnown, cnt);

        if(isKnown ==null) {
            List<Voca> words = userRepository.findWordsForTest(userId);

            Collections.shuffle(words);

            return words.subList(0,cnt);
        }
        else {
            List<Voca> words = userRepository.findWordsForTest(userId, isKnown);

            Collections.shuffle(words);

            return words.subList(0,cnt);
        }

    }

    @Override
    @Transactional
    public boolean toKnownVoca(Long userId, Long vocaId, Boolean isKnown) {
        UserVoca userVoca = userVocaRepository.findByUserIdAndVocaId(userId, vocaId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOCA));

        userVoca.setKnown(isKnown);

        return userVoca.isKnown();
    }



    private boolean isValidateFormForTest(Long userId, Boolean isKnown, Integer cnt) {
        Long totalCnt = 0L;
        if(isKnown == null) {
            totalCnt =userRepository.countVocaByUserId(userId);
        }
        else {
            totalCnt= userRepository.countVocaByUserId(userId ,isKnown);
        }
        if (cnt > totalCnt)
            throw new CustomException(ErrorCode.INVALID_VOCA_CNT);
        return true;
    }


    private boolean isValidateFormForVocaList(Long userId, Boolean isKnown, Integer page) {

        Long vocaCnt = userRepository.countVocaByUserId(userId, isKnown);

        if (page -1  > vocaCnt / 10)
            throw new CustomException(ErrorCode.INVALID_VOCA_CNT);
        return true;
    }

    @Override
    public Long getVocaCntByIsKnown(Long userId, Boolean isKnown) {
        if(isKnown == null) {
          return   userRepository.countVocaByUserId(userId);
        }
        return userRepository.countVocaByUserId(userId, isKnown);
    }

    @Override
    public User findById(Long userId) {
        List<User> users=  userRepository.findUserById(userId);

        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users=  userRepository.findByEmail(email);

        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return users.get(0);
    }
}
