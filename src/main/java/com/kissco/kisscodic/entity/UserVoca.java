package com.kissco.kisscodic.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean isKnown;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean isMine;


}
