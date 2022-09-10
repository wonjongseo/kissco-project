package com.kissco.kisscodic.dto.voca;

import com.kissco.kisscodic.entity.Voca;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VocaDO {
    private String word;
    private String source;

    public static Voca createVoca(VocaDO vocaDO, String mean) {
        Voca voca = new Voca();

        if (vocaDO.getSource().equals("ja")) {
            voca.setWord(mean);
            voca.setMean(vocaDO.getWord());
        }
        else {
            voca.setWord(vocaDO.getWord());
            voca.setMean(mean);
        }

        voca.setCreatedAt(LocalDateTime.now());
        return voca;
    }
}
