package com.kissco.kisscodic.service.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.dto.voca.VocaTestDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;


public interface UserService {

    User findById(Long userId);

    User findByEmail(String email);

    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page,String sort, Boolean isKnown);

    Long deleteVoca(Long userId, Long vocaId);


    List<Voca> test(Long userId, Integer cnt, Boolean isKnown);

    boolean toKnownVoca(Long userId, Long vocaId, Boolean isKnown);

    int deleteAllVoca(Long userId);
}
