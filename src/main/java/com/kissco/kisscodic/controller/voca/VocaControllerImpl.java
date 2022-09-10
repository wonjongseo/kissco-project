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


@RestController
@RequestMapping("/api/vocas")
@RequiredArgsConstructor
public class VocaControllerImpl implements  VocaController{

    private final VocaService vocaService;
    private final UserService userService;

    @GetMapping("/download")
    public void downloadMyVoca(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
        User user = userService.findByEmail("visionwill");

//        Long userId = (Long) session.getAttribute("userId");
        Long userId = user.getId();

//        String email = (String) session.getAttribute("email");
        String email = user.getEmail();
        HSSFWorkbook workbook = vocaService.download(userId);
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

    @GetMapping("/asdasd")
    public List<Voca> getVocas(Long userId, Integer page) {

        return null;
    }

    @GetMapping("/asdasdasdasd")
    public List<Voca> getVocasForTest(Long userId, String source, Integer start, Integer end) {
        return null;
    }

    @PostMapping
    public Voca addVoca(@RequestBody VocaDO vocaDO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long)session.getAttribute("userId");

        Voca voca = null;
        try {
            voca = vocaService.createVoca(vocaDO, userId);
        } catch (ParseException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return voca;
    }

    /**
     *
     * 단어 검색하기
     */
    @GetMapping
    public String addVoca(@RequestBody VocaDO vocaDO) {
        String voca = null;
        try {
            voca = vocaService.findVoca(vocaDO);
        } catch (ParseException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return voca;
    }
}
