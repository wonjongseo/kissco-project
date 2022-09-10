package com.kissco.kisscodic.common.file_maker;

import com.kissco.kisscodic.entity.Voca;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public interface FileMaker {
    HSSFWorkbook createFile(List<Voca> list);

}
