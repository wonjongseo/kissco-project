package com.kissco.kisscodic.controller.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface VocaController {

    List<Voca> getVocas(Long userId, Integer page);
    List<Voca> getVocasForTest(Long userId, String source, Integer start, Integer end);

    Voca addVoca(VocaDO vocaDO, HttpServletRequest request);

    String addVoca(VocaDO vocaDO);
}
