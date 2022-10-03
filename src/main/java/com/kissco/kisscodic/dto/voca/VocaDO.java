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

        if(isKorea(vocaDO.getWord())) {
            voca.setWord(vocaDO.getWord());
            voca.setMean(mean);
        }
        else {
            voca.setWord(mean);
            voca.setMean(vocaDO.getWord());
        }

        return voca;
    }

    public static boolean isKorea(String word) {
        if(!word.matches(".*[가-힣]+.*"))
            return false;
        return true;
    }


    public static Voca createMyVoca(VocaDO vocaDO) {
        if(!isKorea(vocaDO.getWord())){
            throw new CustomException(ErrorCode.MISMATCH_WORD);
        }

        Voca voca = new Voca();
        voca.setMine(true);
        voca.setWord(vocaDO.getWord());
        voca.setMean(vocaDO.getMean());

        return voca;
    }
}
