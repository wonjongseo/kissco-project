package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.parser.ParseException;


public interface VocaService {

    Voca createVoca(VocaDO vocaDO,  Long userId) throws ParseException;
    String findVoca(VocaDO vocaDO) throws ParseException;

    HSSFWorkbook download(Long userId);
}
