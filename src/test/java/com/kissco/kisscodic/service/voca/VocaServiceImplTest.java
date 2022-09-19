package com.kissco.kisscodic.service.voca;

import com.kissco.kisscodic.dto.user.JoinDto;
import com.kissco.kisscodic.dto.user.LoginDto;
import com.kissco.kisscodic.dto.voca.VocaDO;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.UserVoca;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.service.user.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {
                "papago.clientId=4HhVSPcAZaA4F_6snB1x",
                "papago.clientSecret=dk9airxklJ",
                "papago.apiURL=https://openapi.naver.com/v1/papago/n2mt"}
)
@Transactional
public class VocaServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    VocaService vocaService;

    User user;


    @Test
    public void name() {
        
    }

    @Test
    @Rollback(value = false)
    public void addVoca() throws Exception {
        // given

        JoinDto joinDto = new JoinDto();
        joinDto.setUsername("test1");
        joinDto.setPassword("1234");
        joinDto.setPassword2("1234");
        joinDto.setEmail("email1");

        Long aLong = userService.addUser(joinDto);

        System.out.println("aLong = " + aLong);

        user = userService.findById(aLong);

        assertEquals(user.getId(), aLong);


        VocaDO vocaDO = new VocaDO();
        vocaDO.setSource("ko");
        vocaDO.setWord("한국");

        Voca voca = vocaService.createVoca(vocaDO, user.getId());
        VocaDO vocaDO2 = new VocaDO();
        vocaDO2.setSource("ko");
        vocaDO2.setWord("일본");

        Voca voca2 = vocaService.createVoca(vocaDO2, user.getId());


        System.out.println("voca.getWord() = " + voca.getWord());
        System.out.println("voca2.getWord() = " + voca2.getWord());


        assertEquals(user.getUserVocas().size(), 2);
        // when

        userService.deleteVoca(user.getId(), voca.getId());

        assertEquals(user.getUserVocas().size(), 1);

        // then
    }


}