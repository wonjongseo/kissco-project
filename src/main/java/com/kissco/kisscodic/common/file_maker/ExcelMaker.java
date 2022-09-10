package com.kissco.kisscodic.common.file_maker;

import com.kissco.kisscodic.entity.Voca;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelMaker implements FileMaker {

    @Override
    public HSSFWorkbook createFile(List<Voca> list) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        CellStyle defaultStyle = workbook.createCellStyle();

        defaultStyle.setBorderTop(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        defaultStyle.setBorderBottom(BorderStyle.THIN);
        defaultStyle.setBorderRight(BorderStyle.THIN);


        defaultStyle.setWrapText(true);
        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
        defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFSheet sheet = workbook.createSheet("Jongseo");
        sheet.setDefaultRowHeightInPoints(30);

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellStyle(defaultStyle);
        cell.setCellValue("단어");

        cell = row.createCell(1);
        cell.setCellStyle(defaultStyle);
        cell.setCellValue("뜻");

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);

            cell = row.createCell(0);
            cell.setCellStyle(defaultStyle);
            cell.setCellValue(list.get(i).getWord());

            cell = row.createCell(1);
            cell.setCellStyle(defaultStyle);
            cell.setCellValue(list.get(i).getMean());
        }
        return workbook;
    }
}

