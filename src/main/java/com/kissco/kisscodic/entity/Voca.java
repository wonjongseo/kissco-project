package com.kissco.kisscodic.entity;

import com.kissco.kisscodic.common.TimeEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Voca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String word;

    @Column(length = 50)
    private String mean;

}
