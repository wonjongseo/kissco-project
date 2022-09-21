package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.entity.Voca;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface UserController {
//
//    ResponseEntity<List<Voca>> getAllVoca(Integer page,Long userId);
    ResponseEntity<List<Voca>> getAllVoca(Integer page , Boolean isKnown,Long userId);
    boolean changeKnownVoca(Long vocaId, Boolean isKnown, Long userId);

}


