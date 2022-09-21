package com.kissco.kisscodic.repository.user;

import com.kissco.kisscodic.dto.voca.VocaTestDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;
import java.util.Optional;


public interface UserRepository  {

     List<User> findByEmail(String email);

    Long countVocaByUserId(Long userId);
    void save(User user);

    List<User> findById(Long userId);

    List<Voca> findWord(Long userId, String word);

    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page);
    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page, Boolean isKnown);
    List<Voca> findAllWordsByUserId(Long userId);

    List<Voca> findWordsForTest(Long userId, Integer start, Integer end);

//    List<Voca> findWordsByUserIdForTest(Long userId, VocaTestDto vocaTestDto);
}
