package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.dto.voca.VocaTestDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.UserVoca;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import com.kissco.kisscodic.repository.user.UserRepository;
import com.kissco.kisscodic.repository.user_voca.UserVocaRepository;
import com.kissco.kisscodic.repository.voca.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserVocaRepository userVocaRepository;

    @Override
    @Transactional
    public Long addUser(JoinDto joinDto)  {
        List<User> isUser = userRepository.findByEmail(joinDto.getEmail());

        if (!isUser.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        if (!joinDto.getPassword().equals(joinDto.getPassword2())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        User userEntity = joinDto.createUserEntity(joinDto);

        userEntity.hashPassword(passwordEncoder);

        userRepository.save(userEntity);

        return userEntity.getId();
    }

    @Override
    public User login(LoginDto loginDto)  {

        List<User> isUser = userRepository.findByEmail(loginDto.getEmail());
        if (isUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = isUser.get(0);

        if (!user.checkPassword(loginDto.getPassword(),passwordEncoder)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return user;
    }

    @Override
    public User findById(Long userId) {
        List<User> users=  userRepository.findById(userId);

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

    @Override
    public List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page) {
        return userRepository.findAllWordsByUserIdWherePage(userId, page);
    }

    @Override
    @Transactional
    public Long deleteVoca(Long userId, Long vocaId) {

        Optional<UserVoca> isUserVoca = userVocaRepository.findByUserIdAndVocaId(userId, vocaId);

        if (isUserVoca.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_DELETE_VOCA);
        }

        UserVoca userVoca = isUserVoca.get();
        userVocaRepository.deleteById(userVoca.getId());

        return userVoca.getId();
    }

    @Override
    public List<Voca> test(Long userId,  Integer start, Integer end) {

        isValidateForm(userId, start, end);

        return userRepository.findWordsForTest(userId, start, end);
    }


    private boolean isValidateForm(Long userId, Integer start, Integer end) {


        if(start <= 0)
            throw new CustomException(ErrorCode.INVALID_TEST_START);

        if (end < 4)
            throw  new CustomException(ErrorCode.LESS_TEST_COUNT);

        if (end > userRepository.countVocaByUserId(userId))
            throw new CustomException(ErrorCode.INVALID_TEST_END);


        return true;
    }
}
