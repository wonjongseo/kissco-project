package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 회원가입
     * 이메일, 비밀번호, 비밀번호2, 유저네임을 이용해 유저 회원가입.
     * 동일한 이메일이 존재할 경우 예외

     */
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinDto joinDto) {
        return new ResponseEntity<>(userService.addUser(joinDto), HttpStatus.OK);
    }

    /**
     * 로그인
     이메일과 비밀번호를 이용해 유저 로그인.
     비밀번호가 일치하지 않은 경우 예외.
     */
    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto, HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = userService.login(loginDto);
        session.setAttribute("userId", user.getId());
        session.setAttribute("email", user.getEmail());

        return user;

    }

}
