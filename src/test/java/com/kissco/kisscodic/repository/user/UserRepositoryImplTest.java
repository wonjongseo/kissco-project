package com.kissco.kisscodic.repository.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.exception.CUserException;
import com.kissco.kisscodic.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryImplTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void joinUser()  {

        // given
        User user = new User();
        user.setUsername("username");
        user.setEmail("e1");
        // when
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("e1").get(0);
        // then
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user, foundUser);
    }

    @Test(expected = CUserException.class)
    public void checkDuplicateEmailAndPasswordCheck() {

        JoinDto joinDto = new JoinDto();
        joinDto.setEmail("e3");
        joinDto.setPassword("a");
        joinDto.setPassword2("a");
        userService.addUser(joinDto);

        JoinDto joinDto2 = new JoinDto();
        joinDto2.setEmail("e3");
        joinDto2.setPassword("a");
        joinDto2.setPassword2("a");

        userService.addUser(joinDto2);

        fail("Here is Fail");
    }



}