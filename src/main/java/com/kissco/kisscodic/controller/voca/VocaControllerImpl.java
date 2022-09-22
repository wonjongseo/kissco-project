package com.kissco.kisscodic.controller.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.service.user.UserService;
import com.kissco.kisscodic.service.voca.VocaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@RestController
@RequestMapping("/api/vocas")
@RequiredArgsConstructor
public class VocaControllerImpl implements  VocaController{

    private final VocaService vocaService;
    private final UserService userService;

    /**
     * (개요) 단어 저장
     * (설명)
     *  1. 파파고 api 를 통한 결과값를 저장 기능을 통해 데이터베이스에 저장.
    */
    @PostMapping
    public Voca addVoca(VocaDO vocaDO, @SessionAttribute("userId") Long userId) {

        Voca voca = null;
        try {
            voca = vocaService.createVoca(vocaDO, userId);
        } catch (ParseException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return voca;
    }

    /**
     * (개요) 단어 검색
     1. 파파고 api 를 통한 결과값를 저장 기능을 통해 데이터베이스에 저장.
     2. 검색 단어가 일본어 혹은 한국어 인 것을 선택
     */
    @GetMapping
    public String addVoca( VocaDO vocaDO) {
        String voca = null;
        try {
            voca = vocaService.findVoca(vocaDO);
        } catch (ParseException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return voca;
    }


    /**
     * (개요) 단어 삭제
     * (설명) 유저 단어장에서 해당 단어를 삭제
     */
    @Override
    @DeleteMapping("/{vocaId}")
    public Long deleteVoca(@PathVariable Long vocaId, @SessionAttribute("userId") Long userId) {


        Long aLong = userService.deleteVoca(userId, vocaId);
        return aLong;
    }

    /**
     (개요) 단어 다운로드
     (설명) 유저가 저장한 파일을 브라우저를 통해 다운로드한다.
     */
//    @Override
    @GetMapping("/download")
    public void downloadMyVoca(
            HttpServletResponse response
//            @SessionAttribute(name = "email") String email
    ) {
        /**
         * TODO REMOVE
         * email = "admin";
         */
        String email = "admin";

        HSSFWorkbook workbook = vocaService.download(email);

        String fileName = email+ ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(workbook != null) { workbook.close(); }
                if(outputStream != null) { outputStream.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
