package com.kissco.kisscodic.repository.user;

import com.kissco.kisscodic.dto.voca.VocaTestDto;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;


public interface UserRepository  {

     List<User> findByEmail(String email);

    Long countVocaByUserId(Long userId ,Boolean isKnown);
    void save(User user);

    List<User> findUserById(Long userId);

    List<Voca> findWord(Long userId, String word);

    List<Voca> findVocas(Long userId, Integer page, String sort, Boolean isKnown);
    List<Voca> findAllWordsByUserId(Long  userId);

    List<Voca> findWordsForTest(Long userId, Boolean isKnown);

}
