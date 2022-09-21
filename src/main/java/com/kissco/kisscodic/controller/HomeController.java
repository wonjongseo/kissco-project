package com.kissco.kisscodic.controller;

import com.kissco.kisscodic.controller.voca.VocaController;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.repository.voca.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final VocaRepository vocaRepository;

    @GetMapping("/")
    public List<Voca> getAllVocas() {
        List<Voca> all = vocaRepository.findAll();

        System.out.println("all.size() = " + all.size());
        return all;
    }
}
