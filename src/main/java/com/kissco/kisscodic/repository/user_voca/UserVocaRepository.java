package com.kissco.kisscodic.repository.user_voca;

import com.kissco.kisscodic.entity.UserVoca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVocaRepository extends JpaRepository<UserVoca, Long> {
    Optional<UserVoca> findByUserIdAndVocaId(Long userId, Long vocaId);
}
