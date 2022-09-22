package com.kissco.kisscodic.dto.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Scanner;

@Data
public class VocaDO {

    private String word;
    private String source;
    private String mean;

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

        return voca;
    }

    private static boolean isKorea(String word) {
        if(!word.matches(".*[가-힣]+.*"))
            throw new CustomException(ErrorCode.MISMATCH_WORD);
        return true;
    }
    public static Voca createMyVoca(VocaDO vocaDO) {
        isKorea(vocaDO.getWord());

        Voca voca = new Voca();

        voca.setWord(vocaDO.getWord());
        voca.setMean(vocaDO.getMean());

        return voca;
    }
}
