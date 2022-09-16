package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;


public interface UserService {
    Long addUser(JoinDto joinDto) ;
    Long login(LoginDto loginDto) ;
    User findById(Long userId);
    User findByEmail(String email);
    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page);
}
