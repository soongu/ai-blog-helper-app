package com.study.springprj.chap03;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BlogDto {

    private Long id;
    private String title;
    private String content;
    private int recommend;
}
