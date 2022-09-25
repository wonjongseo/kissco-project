package com.kissco.kisscodic.controller.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.service.user.UserService;
import com.kissco.kisscodic.service.voca.VocaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;


@RestController
@RequestMapping("/api/vocas")
@RequiredArgsConstructor
public class VocaControllerImpl implements  VocaController{

    private final VocaService vocaService;
    private final UserService userService;

    /**
     * (개요) 단어 검색
     1. 파파고 api 를 통한 결과값를 저장 기능을 통해 데이터베이스에 저장.
     2. 검색 단어가 일본어 혹은 한국어 인 것을 선택
     */
    @Override
    @GetMapping
    public Map<String,String> searchVoca(@RequestParam String word , @RequestParam String source) {

        return vocaService.findVoca(word,source);

    }

    /**
     * (개요) 단어 저장
     * (설명)
     *  1. 파파고 api 를 통한 결과값를 저장 기능을 통해 데이터베이스에 저장.
     */
//    @Override
    @PostMapping
    public Voca addVoca(@RequestBody VocaDO vocaDO, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        System.out.println("userId = " + userId);
        Voca voca = vocaService.createVoca(vocaDO, userId);
        return voca;
    }

    /**
     * (개요) 나만의 단어 저장
     * (설명)
     * 1. 유저가 단어와 뜻을 직접 입력하여 회원 별로 DB에 저장.
     * 2. 나만의 단어를 단어장에서 삭제할 시 단어장 뿐 아니라 단어의 데이터까지 삭제.
          */
    @PostMapping("/my")
    public Voca addMyVoca(@RequestBody VocaDO vocaDO, @SessionAttribute("userId") Long userId) {
        Voca voca = vocaService.createMyVoca(vocaDO, userId);

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
     * (개요) 모든 단어 삭제
     * (설명)
     * 1. 회원 단어장 페이지에서 “모든 단어 삭제” 버튼을 눌러,
     * 2. 저장한 모든 단어를 삭제한다.
     */
    @Override
    @DeleteMapping("/all-vocas")
    public int deleteAllVoca( @SessionAttribute("userId") Long userId) {
        return userService.deleteAllVoca(userId);

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
