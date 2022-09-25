package com.kissco.kisscodic.controller.user;

import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    /**
     * 개요)
     * 	 유저가 저장한 단어 리스트 확인.
     *
     * 설명)
     * 	1. 암기 혹은 미암기의 카테고리를 선택한 후 카테고리 별로 단어 리스트를 본다.
     * 	2. 최신순, 오래된 순으로 정렬 가능하다.
     * 	3. 페이지 당 10개의 단어를 보여준다.
     *
     *  예외)
     *  1. 저장된 단어 / 10 보다 큰 page 를 입력 시
     *  2. 최신순, 오래된 순 외의 정렬 방법 선택 시
     */
    @Override
    @GetMapping("/vocas/{userId}")
    public ResponseEntity<List<Voca>> getAllVoca(
            @PathVariable Long userId,
            @RequestParam Integer page,
            @RequestParam Boolean isKnown,
            @RequestParam String sort
            ) {

        List<Voca> allWordsByUserIdWherePage = userService.findAllWordsByUserIdWherePage(userId, page,sort, isKnown);

        return new ResponseEntity<>(allWordsByUserIdWherePage, HttpStatus.OK);
    }


    /**
     * (개요) 단어 개수 확인
     */
    @GetMapping("/vocas/cnt/{userId}")
    public Map<String, Long> getVocaCnt(@PathVariable Long userId, @RequestParam Boolean isKnown) {
        Map<String, Long> cnt = new HashMap<>();
        cnt.put("count", userService.getVocaCntByIsKnown(userId, isKnown));


        return cnt;

    }
    /**
     * (개요) 미암기 표시의 단어를 암기로 변경.
       (예외) 저장되지 않은 vocaId 를 입력할 시
     */

    @Override
    @PostMapping("/vocas/{vocaId}/{userId}")
    public boolean changeKnownVoca(
                                @PathVariable Long vocaId,
                                @RequestParam Boolean isKnown,
                                @PathVariable Long userId) {

        userService.toKnownVoca(userId, vocaId, isKnown);

        return true;
    }


    /**
     * 개요) 유저가 저장한 자료를 랜덤으로 섞어서 테스트.
     *
     * 설명)
     *  1. 유저는 암기 혹은 미암기 카테고리 중 하나를 선택해 카테고리의 단어를 테스트한다.
     * 	2. 개수를 입력 받고 저장된 단어를 무작위로 섞은 후 개수 만큼의 단어를 반환한다.
     *
     * 예외)
     *  1. 개수를 3이하로 입력 경우
     * 	2. 카테고리를 암기 혹은 비암기 외의 값으로 입력한 경우
     * 	3. 개수를 카테고리 별 저장된 데이터보다 높은 수를 입력한 경우.
     */
    @GetMapping("/vocas/test/{userId}")
    public List<Voca> userVocaTest(@PathVariable("userId") Long userId,
                           @RequestParam(name = "cnt") Integer cnt,
                           @RequestParam(name = "isKnown") Boolean isKnown
                           ) {


        return userService.test(userId,  cnt, isKnown);

    }
}
