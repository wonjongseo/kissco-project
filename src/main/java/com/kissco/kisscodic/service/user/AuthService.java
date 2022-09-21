package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import com.kissco.kisscodic.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
}
