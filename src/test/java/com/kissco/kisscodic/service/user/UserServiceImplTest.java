package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.exception.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test(expected = CustomException.class)
    public void checkWrongPassword() {

        JoinDto joinDto = new JoinDto();
        joinDto.setPassword("1234");
        joinDto.setPassword2("123");

        userService.addUser(joinDto);

        fail("here is fail");
    }


    @Test
    public void enctipyPassword()throws Exception {
        // given
        JoinDto joinDto1 = new JoinDto();
        joinDto1.setPassword("1234");
        joinDto1.setPassword2("1234");
        joinDto1.setUsername("hello");
        // when

        Long aLong = userService.addUser(joinDto1);
        User userById = userService.findById(aLong);

        // then

        assertNotEquals(userById.getId(), joinDto1.getPassword());
    }
    @Test
    @Rollback(value = false)
    public void checkEnctipyPassword()throws Exception {
        // given
        JoinDto joinDto1 = new JoinDto();
        joinDto1.setPassword("1234");
        joinDto1.setEmail("eee");
        joinDto1.setPassword2("1234");
        joinDto1.setUsername("hello");
        // when

        Long aLong = userService.addUser(joinDto1);


        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("eee");
        loginDto.setPassword("1234");
        User login = userService.login(loginDto);

        assertEquals(aLong,login.getId());
    }



}