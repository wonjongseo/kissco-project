package com.kissco.kisscodic.repository.user;


import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final int MAX_PAGE_COUNT = 10;

    private final EntityManager em;

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class).setParameter("email", email).getResultList();
    }


    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findById(Long userId) {
        return em.createQuery("select  u from User u where u.id = :id", User.class).setParameter("id", userId).getResultList();

    }

    @Override
    public List<Voca> findWord(Long userId, String word) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId and v.word = :word", Voca.class).setParameter("userId", userId).setParameter("word", word).getResultList();
    }



    @Override
    public List<Voca> findAllWordsByUserIdWherePage(Long userId, Integer page) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId ", Voca.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * MAX_PAGE_COUNT)
                .setMaxResults((page - 1) * MAX_PAGE_COUNT + MAX_PAGE_COUNT)
                .getResultList();
    }

    @Override
    public List<Voca> findAllWordsByUserId(Long userId) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId ", Voca.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Voca> findWordsForTest(Long userId, Integer start, Integer end) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId ", Voca.class)
                .setParameter("userId", userId)
                .setFirstResult(start - 1)
                .setMaxResults(end)
                .getResultList();
    }


    @Override
    public Long countVocaByUserId(Long userId) {
        return em.createQuery("select count (v) from User u join u.userVocas vc join vc.voca v where u.id = :userId ", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }


}
