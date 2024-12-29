package com.study.springprj.chap03;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private int recommend;
}
