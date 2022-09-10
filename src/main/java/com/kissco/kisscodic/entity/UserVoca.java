package com.kissco.kisscodic.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class UserVoca {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voca_id")
    private Voca voca;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
