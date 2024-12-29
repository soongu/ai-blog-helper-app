package com.study.springprj.chap07.dto;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private String title;
    private String body;
    private Long id;
    private Long userId;
}
