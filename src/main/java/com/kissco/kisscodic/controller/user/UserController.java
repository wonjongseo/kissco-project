package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserController {

    ResponseEntity<List<Voca>> getAllVoca(Integer page,Long userId);

}


