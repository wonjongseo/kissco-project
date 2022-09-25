package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;


public interface VocaService {
    Voca createVoca(VocaDO vocaDO,  Long userId) ;
    Voca createMyVoca(VocaDO vocaDO,  Long userId) ;
    Map<String,String> findVoca(String word ,String source) ;
    HSSFWorkbook download(Long userId);
}
