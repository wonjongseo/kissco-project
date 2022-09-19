package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
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
    public ResponseEntity<List<Voca>> getAllVoca(@RequestParam Integer page, @SessionAttribute("userId") Long userId) {


        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        List<Voca> allWordsByUserIdWherePage = userService.findAllWordsByUserIdWherePage(userId, page);

        return new ResponseEntity<>(allWordsByUserIdWherePage, HttpStatus.OK);
    }



    @GetMapping("/vocas/test")
    public List<Voca> test(
                           @RequestParam(name = "start") Integer start,
                           @RequestParam(name = "end") Integer end,
                           @SessionAttribute("userId") Long userId) {

        return userService.test(userId,  start, end);
    }
}
