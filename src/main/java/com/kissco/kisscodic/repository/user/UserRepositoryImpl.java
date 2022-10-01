package com.kissco.kisscodic.repository.user;


import com.kissco.kisscodic.dto.voca.VocaResponseDTO;
import com.kissco.kisscodic.entity.User;
import com.kissco.kisscodic.entity.Voca;
import com.kissco.kisscodic.exception.CustomException;
import com.kissco.kisscodic.exception.ErrorCode;
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
    public List<User> findUserById(Long userId) {
        return em.createQuery("select  u from User u where u.id = :id", User.class).setParameter("id", userId).getResultList();

    }

    @Override
    public List<Voca> findWord(Long userId, String word) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId and v.word = :word", Voca.class).setParameter("userId", userId).setParameter("word", word).getResultList();
    }




    @Override
    public List<VocaResponseDTO> findVocas(Long userId, Integer page, String sort, Boolean isKnown) {
        String jpql = "";

        if (sort.equals("asc")) {
            jpql = "select new com.kissco.kisscodic.dto.voca.VocaResponseDTO(v.id, v.word,v.mean,vc.isKnown) from User u join u.userVocas vc join vc.voca v where u.id = :userId and vc.isKnown = :isKnown order by vc.id asc";

        } else if (sort.equals("desc")) {
            jpql = "select new com.kissco.kisscodic.dto.voca.VocaResponseDTO(v.id, v.word,v.mean,vc.isKnown) from User u join u.userVocas vc join vc.voca v where u.id = :userId and vc.isKnown = :isKnown order by vc.id desc";
        } else throw new CustomException(ErrorCode.MISMATCH_ARGUMENT);
        return em.createQuery(jpql, VocaResponseDTO.class)
                .setParameter("userId", userId)
                .setParameter("isKnown", isKnown)
                .setFirstResult((page - 1) * MAX_PAGE_COUNT)
                .setMaxResults(MAX_PAGE_COUNT)

                .getResultList();
    }

    @Override
    public List<VocaResponseDTO> findVocas(Long userId, Integer page, String sort) {
        String jpql = "select new com.kissco.kisscodic.dto.voca.VocaResponseDTO(v.id, v.word,v.mean,vc.isKnown) from User u join u.userVocas vc join vc.voca v where u.id = :userId ";

        if (sort.equals("asc")) {
            jpql += "order by vc.id asc";
        } else if (sort.equals("desc")) {
            jpql += "order by vc.id desc";
        } else throw new CustomException(ErrorCode.MISMATCH_ARGUMENT);
        return em.createQuery(jpql, VocaResponseDTO.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * MAX_PAGE_COUNT)
                .setMaxResults(MAX_PAGE_COUNT)
                .getResultList();

        // 0 * 10
        // 10 ~  (10+ 10)
        // 10 ~ 20
    }

    @Override
    public List<Voca> findAllWordsByUserId(Long userId) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId ", Voca.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Voca> findWordsForTest(Long userId, Boolean isKnown) {
        return em.createQuery("select v from User u join u.userVocas vc join vc.voca v where u.id = :userId and vc.isKnown= :isKnown ", Voca.class)
                .setParameter("userId", userId)
                .setParameter("isKnown",isKnown)
                .getResultList();
    }


    @Override
    public Long countVocaByUserId(Long userId ,Boolean isKnown) {
        return em.createQuery("select count (v) from User u join u.userVocas vc join vc.voca v where u.id = :userId and vc.isKnown = :isKnown", Long.class)
                .setParameter("userId", userId)
                .setParameter("isKnown", isKnown)
                .getSingleResult();
    }


}
