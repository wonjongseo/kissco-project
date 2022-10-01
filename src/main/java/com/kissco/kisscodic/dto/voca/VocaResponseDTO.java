package com.kissco.kisscodic.dto.voca;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VocaResponseDTO {
    private Long id;
    private String word;
    private String mean;
    private boolean isKnown;
}
