package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.Voca;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserController {

    ResponseEntity<Long> join(JoinDto joinDto);
    ResponseEntity<Boolean> login(LoginDto loginDto, HttpServletRequest request);
    ResponseEntity<List<Voca>> getAllVoca(Integer page,HttpServletRequest request);

}


