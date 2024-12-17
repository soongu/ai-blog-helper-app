package com.example.bloghelper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글(Post) 엔티티 클래스입니다.
 * 데이터베이스의 'posts' 테이블과 매핑되어 게시글 정보를 저장하고 관리합니다.
 */
@Entity // 이 클래스가 엔티티(데이터베이스 테이블과 매핑되는 클래스)임을 나타냅니다.
@Getter // Lombok 어노테이션으로, 엔티티의 필드에 대한 Getter 메서드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok 어노테이션으로, 파라미터가 없는 기본 생성자를 자동으로 생성합니다.
public class Post {
    @Id // 해당 필드가 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 데이터베이스가 키 값을 자동으로 생성하는 전략을 사용합니다. (예: MySQL의 AUTO_INCREMENT)
    private Long id;


    @Column(nullable = false)
    // title 컬럼은 null값을 허용하지 않도록 설정합니다.
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    // content 컬럼은 null을 허용하지 않으며, TEXT 타입을 사용하도록 명시합니다.
    private String content;

    // 생성 시각을 저장할 필드입니다.
    private LocalDateTime createdAt;

    // 수정 시각을 저장할 필드입니다.
    private LocalDateTime updatedAt;

    /**
     * 엔티티가 처음 persist(저장)될 때 자동으로 실행되는 메서드입니다.
     *
     * @PrePersist 어노테이션을 통해 JPA가 데이터베이스에 엔티티를 저장하기 전
     * 이 메서드를 호출하여 생성 시간(createdAt)과 수정 시간(updatedAt)을 현재 시간으로 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 업데이트될 때 자동으로 실행되는 메서드입니다.
     *
     * @PreUpdate 어노테이션을 통해 엔티티가 데이터베이스에 업데이트되기 전
     * 이 메서드가 호출되어 수정 시간(updatedAt)을 현재 시간으로 갱신합니다.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * 빌더 패턴을 사용한 생성자입니다.
     * Lombok의 @Builder 어노테이션을 사용하면 Post.builder().title("제목").content("내용").build()
     * 와 같은 형태로 객체를 손쉽게 생성할 수 있습니다.
     *
     * @param title   게시글 제목
     * @param content 게시글 내용
     */
    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 게시글 정보를 업데이트하는 메서드입니다.
     * 제목과 내용을 변경할 수 있습니다.
     *
     * @param title   변경할 새로운 제목
     * @param content 변경할 새로운 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
