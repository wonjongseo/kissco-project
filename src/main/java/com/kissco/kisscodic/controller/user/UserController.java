package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.voca.VocaResponseDTO;
import com.kissco.kisscodic.entity.Voca;
import org.springframework.http.ResponseEntity;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserController {
//
//    ResponseEntity<List<Voca>> getAllVoca(Integer page,Long userId);
    ResponseEntity<List<VocaResponseDTO>> getAllVoca(Long userId, Integer page , Boolean isKnown, String sort);
    boolean changeKnownVoca(Long vocaId, Boolean isKnown, Long userId);

}


