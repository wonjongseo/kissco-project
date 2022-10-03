package com.kissco.kisscodic.controller.voca;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.dto.voca.VocaDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface VocaController {
    Long deleteVoca( Long vocaId, Long userId);

//    Voca addVoca( VocaDO vocaDO, String userId);

    Map<String,String> searchVoca(String word );

    int deleteAllVoca(  Long userId);
}
