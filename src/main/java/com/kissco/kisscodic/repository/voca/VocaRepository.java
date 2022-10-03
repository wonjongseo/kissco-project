package com.kissco.kisscodic.repository.voca;

import com.kissco.kisscodic.entity.Voca;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VocaRepository extends JpaRepository<Voca, Long> {



    List<Voca> findAll();

    Optional<Voca> findByWordAndMean(String word, String mean);
    Optional<Voca> findByMeanAndWord(String word,String mean);
}
