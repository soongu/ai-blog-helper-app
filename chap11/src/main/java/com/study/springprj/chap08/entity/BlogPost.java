package com.study.springprj.chap08.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Setter @Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor

@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id // 기본키 지정 (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blog_title", nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
