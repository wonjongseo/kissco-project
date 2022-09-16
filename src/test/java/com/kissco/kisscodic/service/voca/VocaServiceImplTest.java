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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VocaServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    VocaService vocaService;


    LoginDto loginDto = new LoginDto();
    JoinDto joinDto = new JoinDto();

    @BeforeEach
    public void beforeEach() {
        System.out.println("\"asD?ASD?ASd\" = " + "asD?ASD?ASd");
        joinDto.setEmail("visionwill");
        joinDto.setPassword("1234");
        joinDto.setPassword2("1234");


        loginDto.setEmail("visionwill");
        loginDto.setPassword("1234");
    }
    @Test
    @Rollback(value = false)
    public void add() throws Exception {

        joinDto.setEmail("visionwill");
        joinDto.setPassword("1234");
        joinDto.setPassword2("1234");
        joinDto.setUsername("종서");

        loginDto.setEmail("visionwill");
        loginDto.setPassword("1234");
        userService.addUser(joinDto);
        Long userId = userService.login(loginDto);


        String word = "공부";

        VocaDO vocaDO = new VocaDO();
        vocaDO.setWord(word);
        vocaDO.setSource("ko");

        Voca voca = vocaService.createVoca(vocaDO, userId);
        Voca voca2 = vocaService.createVoca(vocaDO, userId);

        assertEquals(voca2.getWord(), voca.getWord());
        assertEquals(voca2.getMean(), voca.getMean());

        // given

        // when

        // then
    }


    @Test
    @Rollback(value = false)
    public void addMulitiWordToUser() throws Exception {

        joinDto.setEmail("visionwill");
        joinDto.setPassword("1234");
        joinDto.setPassword2("1234");
        joinDto.setUsername("종서");

        loginDto.setEmail("visionwill");
        loginDto.setPassword("1234");


        userService.addUser(joinDto);
        Long userId = userService.login(loginDto);


        String word = "공부";

        VocaDO vocaDO = new VocaDO();
        vocaDO.setWord(word);
        vocaDO.setSource("ko");

        String word2 = "멋쟁이";

        VocaDO vocaDO2 = new VocaDO();
        vocaDO2.setWord(word2);
        vocaDO2.setSource("ko");

        Voca voca = vocaService.createVoca(vocaDO, userId);
        Voca voca2 = vocaService.createVoca(vocaDO2, userId);


        User user = userService.findById(userId);

        UserVoca userVoca = new UserVoca();
        userVoca.setVoca(voca);

        user.addUserVoca(userVoca);

        UserVoca userVoca2 = new UserVoca();
        userVoca2.setVoca(voca2);

        user.addUserVoca(userVoca2);


        List<UserVoca> userVocas = user.getUserVocas();


        for (int i = 0; i < userVocas.size(); i++) {
            System.out.println("userVocas.get(i).getVoca().getWord() = " + userVocas.get(i).getVoca().getWord());
        }

        // given

        // when

        // then
    }

    @Test
    public void deteleUserOfVoca()throws Exception {
    // given
        joinDto.setEmail("visionwill");
        joinDto.setPassword("1234");
        joinDto.setPassword2("1234");
        joinDto.setUsername("종서");

        loginDto.setEmail("visionwill");
        loginDto.setPassword("1234");

        userService.addUser(joinDto);
        Long userId = userService.login(loginDto);

        String word = "공부";

        VocaDO vocaDO = new VocaDO();
        vocaDO.setWord(word);
        vocaDO.setSource("ko");

        String word2 = "멋쟁이";

        VocaDO vocaDO2 = new VocaDO();
        vocaDO2.setWord(word2);
        vocaDO2.setSource("ko");

        Voca voca = vocaService.createVoca(vocaDO, userId);
        Voca voca2 = vocaService.createVoca(vocaDO2, userId);


        User user = userService.findById(userId);

        UserVoca userVoca = new UserVoca();
        userVoca.setVoca(voca);

        user.addUserVoca(userVoca);

        UserVoca userVoca2 = new UserVoca();
        userVoca2.setVoca(voca2);

        user.addUserVoca(userVoca2);


        // when

        

    // then
    }


}