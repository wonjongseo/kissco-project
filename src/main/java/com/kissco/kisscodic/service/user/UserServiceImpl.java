package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.exception.CUserException;
import com.kissco.kisscodic.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;



    @Override
    @Transactional
    public Long addUser(JoinDto joinDto) throws CUserException {
        List<User> isUser = userRepository.findByEmail(joinDto.getEmail());

        if (!isUser.isEmpty()) {
            throw new CUserException("user is dup");
        }

        if (!joinDto.getPassword().equals(joinDto.getPassword2())) {
            throw new CUserException("Password is Wrong");
        }

        User userEntity = joinDto.createUserEntity(joinDto);

        userEntity.hashPassword(passwordEncoder);

        userRepository.save(userEntity);

        return userEntity.getId();
    }

    @Override
    public Long login(LoginDto loginDto) throws Exception {

        List<User> isUser = userRepository.findByEmail(loginDto.getEmail());
        if (isUser.isEmpty()) {
            throw new Exception("user is not found");
        }

        User user = isUser.get(0);

        if (!user.checkPassword(loginDto.getPassword(),passwordEncoder)) {
            throw new Exception("Password is Wrong");
        }

        return user.getId();
    }

    @Override
    public User findById(Long userId) {
        List<User> users=  userRepository.findById(userId);

        if (users.isEmpty()) {
            throw new CUserException("해당 아이디의 유저가 존재하지 않습니다.");
        }

        return users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users=  userRepository.findByEmail(email);

        if (users.isEmpty()) {
            throw new CUserException("해당 이메일의 유저가 존재하지 않습니다.");
        }

        return users.get(0);
    }
}
