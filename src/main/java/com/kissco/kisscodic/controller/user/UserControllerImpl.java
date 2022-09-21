package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
import com.kissco.kisscodic.repository.user_voca.UserVocaRepository;
import com.kissco.kisscodic.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    /**
     * 단어장 보기
     * 유저가 저장한 단어 보기
     * 페이지 당 최대 10개의 단어 표시
     */
    @Override
    @GetMapping("/vocas")
    public ResponseEntity<List<Voca>> getAllVoca(
            @RequestParam Integer page,
            @RequestParam Boolean isKnown,
            @RequestParam String sort,
            @SessionAttribute("userId") Long userId) {

        List<Voca> allWordsByUserIdWherePage = userService.findAllWordsByUserIdWherePage(userId, page,sort, isKnown);

        return new ResponseEntity<>(allWordsByUserIdWherePage, HttpStatus.OK);
    }

    @Override
    @PostMapping("/vocas/{vocaId}")
    public boolean changeKnownVoca(
                                @PathVariable Long vocaId,
                                @RequestParam Boolean isKnown,
                                @SessionAttribute(name = "userId") Long userId) {

        return  userService.toKnownVoca(userId, vocaId, isKnown);
    }


    @GetMapping("/vocas/test")
    public List<Voca> userVocaTest(
                           @RequestParam(name = "cnt") Integer cnt,
                           @RequestParam(name = "isKnown") Boolean isKnown,
                           @SessionAttribute("userId") Long userId) {

        return userService.test(userId,  cnt, isKnown);
    }


}
