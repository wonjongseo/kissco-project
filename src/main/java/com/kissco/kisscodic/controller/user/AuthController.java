package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * (개요) 회원가입
     * (설명) 이메일, 비밀번호, 비밀번호2, 유저네임을 이용해 유저 회원가입.
     * (예외) 동일한 이메일이 존재할 경우 예외
             이메일 길이 100
     */
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinDto joinDto) {
        return new ResponseEntity<>(authService.addUser(joinDto), HttpStatus.OK);
    }

    /**
     * (개요)  로그인
       (설명)  이메일과 비밀번호를 입력하여 유저 로그인.
       (예외) 비밀번호가 일치하지 않은 경우 예외.
     */
    @PostMapping("/login")
    public ResponseEntity<User> login( @RequestBody LoginDto loginDto, HttpServletRequest request) {
        User user = authService.login(loginDto);
        HttpSession session = request.getSession();

        session.setAttribute("userId",user.getId());
        session.setAttribute("email",user.getEmail());

        return ResponseEntity.ok().body(user);

    }

}
