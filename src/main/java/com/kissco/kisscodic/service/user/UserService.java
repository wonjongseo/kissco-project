package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.exception.CUserException;


public interface UserService {
    Long addUser(JoinDto joinDto) throws CUserException;
    Long login(LoginDto loginDto) throws Exception;
    User findById(Long userId);
    User findByEmail(String email);


}
