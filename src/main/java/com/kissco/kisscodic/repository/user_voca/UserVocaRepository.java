package com.kissco.kisscodic.repository.user_voca;

import com.kissco.kisscodic.entity.UserVoca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Optional;

public interface UserVocaRepository extends JpaRepository<UserVoca, Long> {
    Optional<UserVoca> findByUserIdAndVocaId(Long userId, Long vocaId);

    List<UserVoca> findVocasByUserId(Long userId);


    @Query("select uv From UserVoca uv join uv.user u join uv.voca v where  v.isMine= true  and u.id = :id and v.mean = :mean and v.word = :word")
    Optional<UserVoca> IsExistMyVoca(@Param("id")Long userId, @Param("word") String word, @Param("mean") String mean);

    @Query("select uv From UserVoca uv join uv.user u join uv.voca v where  v.isMine= false  and v.mean = :mean and v.word = :word")
    Optional<UserVoca> IsExistVoca( @Param("word") String word, @Param("mean") String mean);
}
