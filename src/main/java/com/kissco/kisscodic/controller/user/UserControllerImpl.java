package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CAuthException;
import com.kissco.kisscodic.repository.user.UserRepository;
import com.kissco.kisscodic.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserControllerImpl implements  UserController{

    private final UserService userService;

    @Override
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinDto joinDto) {
        try {
            return new ResponseEntity<> (userService.addUser(joinDto),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();

            Long userId = userService.login(loginDto);
            session.setAttribute("userId" ,userId );
            session.setAttribute("email" , loginDto.getEmail());

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }




    private final UserRepository userRepository;

    @Override
    @GetMapping("/user/vocas")
    public ResponseEntity<List<Voca>> getAllVoca(@RequestParam Integer page, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new CAuthException("로그인 하세요");
        }


        List<Voca> allWordsByUserIdWherePage = userRepository.findAllWordsByUserIdWherePage(userId, page);


        return new ResponseEntity<>(allWordsByUserIdWherePage, HttpStatus.OK);
    }


}
