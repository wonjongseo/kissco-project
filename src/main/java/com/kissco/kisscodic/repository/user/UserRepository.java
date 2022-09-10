package com.kissco.kisscodic.repository.user;

import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;

import java.util.List;
import java.util.Optional;


public interface UserRepository  {

     List<User> findByEmail(String email);

    void save(User user);

    List<User> findById(Long userId);

    List<Voca> findWord(Long userId, String word);
    List<Voca> findMean(Long userId, String mean);

    List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page);
    List<Voca> findAllWordsByUserId(Long userId);


}
