package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.dto.voca.VocaTestDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;


public interface UserService {
    Long addUser(JoinDto joinDto) ;
    User login(LoginDto loginDto) ;
    User findById(Long userId);
    User findByEmail(String email);
    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page);
    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page,Boolean isOk);
//    List<Voca> getTestOfUserFromStartToEnd(Long userId, VocaTestDto vocaTestDto);
    Long deleteVoca(Long userId, Long vocaId);


    List<Voca> test(Long userId, Integer start, Integer end);

    boolean toKnownVoca(Long userId, Long vocaId, Boolean isKnown);
}
