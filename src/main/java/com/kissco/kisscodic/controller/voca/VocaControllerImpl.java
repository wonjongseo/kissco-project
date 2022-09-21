package com.kissco.kisscodic.controller.voca;


import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.service.user.UserService;
import com.kissco.kisscodic.service.voca.VocaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


// /api/vocas/download
@RestController
@RequestMapping("/api/vocas")
@RequiredArgsConstructor
public class VocaControllerImpl implements  VocaController{

    private final VocaService vocaService;
    private final UserService userService;

    /**
     유저의 저장된 파일을 브라우저를 통해 다운로드
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


    /**
     * 단어 저장
    파파고 api 를 통한 결과값과 유저가 입력한 단어를
    유저 단어장에 저장
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
     * 단어 검색
     파파고 api 를 통해 유저가 입력한 단어의 뜻을 반환
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
     * 단어 삭제
     유저 단어장에서 해당 단어를 삭제
     */
    @Override
    @DeleteMapping("/{vocaId}")
    public Long deleteVoca(@PathVariable Long vocaId, @SessionAttribute("userId") Long userId) {


        Long aLong = userService.deleteVoca(userId, vocaId);
        return aLong;
    }
}
