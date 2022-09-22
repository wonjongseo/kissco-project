package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public interface VocaService {
    Voca createVoca(VocaDO vocaDO,  Long userId) ;
    Voca createMyVoca(VocaDO vocaDO,  Long userId) ;
    String findVoca(VocaDO vocaDO);
    HSSFWorkbook download(String email);
}
