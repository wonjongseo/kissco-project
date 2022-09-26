package com.kissco.kisscodic.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String username;

    @Column(length = 100, unique = true)
    private String email;

    @JsonIgnore
    @Column(length = 100)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<UserVoca> userVocas = new ArrayList<>();

    private LocalDateTime createdAt;

    public User hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }


    public void addUserVoca(Voca voca) {
        UserVoca userVoca = new UserVoca();
        userVoca.setVoca(voca);

        this.userVocas.add(userVoca);
        userVoca.setUser(this);
    }

}
