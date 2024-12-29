package com.study.springprj.chap08;

import com.study.springprj.chap08.entity.BlogPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// BlogPostRepositoryTest.java
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BlogPostRepositoryTest {

    @Autowired
    private BlogPostRepository blogPostRepository;


    @Test
    void 블로그_포스트를_저장할_수_있다() {
        // given
        final String title = "JPA 학습하기";
        final String content = "JPA는 자바 진영의 ORM 표준입니다.";
        BlogPost post = BlogPost.createPost(title, content);

        // when
        BlogPost savedPost = blogPostRepository.save(post);

        // then
        assertAll(
                () -> assertThat(savedPost.getId()).isNotNull(),
                () -> assertThat(savedPost.getTitle()).isEqualTo(title),
                () -> assertThat(savedPost.getContent()).isEqualTo(content),
                () -> assertThat(savedPost.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void ID로_블로그_포스트를_조회할_수_있다() {
        // given
        BlogPost post = BlogPost.createPost(
                "Spring Data JPA 활용하기",
                "Spring Data JPA는 JPA를 더 쉽게 사용할 수 있게 해줍니다."
        );
        BlogPost savedPost = blogPostRepository.save(post);

        // when
        BlogPost foundPost = blogPostRepository.findById(savedPost.getId())
                .orElseThrow();

        // then
        assertThat(foundPost)
                .isEqualTo(savedPost);
    }

    @Test
    void 블로그_포스트를_수정할_수_있다() {
        // given
        BlogPost post = blogPostRepository.save(
                BlogPost.createPost("원본 제목", "원본 내용")
        );
        final String updatedTitle = "수정된 제목";
        final String updatedContent = "수정된 내용";

        // when
        post.setTitle(updatedTitle);
        post.setContent(updatedContent);
        BlogPost updatedPost = blogPostRepository.save(post);

        // then
        assertAll(
                () -> assertThat(updatedPost.getTitle()).isEqualTo(updatedTitle),
                () -> assertThat(updatedPost.getContent()).isEqualTo(updatedContent)
        );
    }

    @Test
    void 블로그_포스트를_삭제할_수_있다() {
        // given
        BlogPost post = blogPostRepository.save(
                BlogPost.createPost("삭제될 포스트", "이 포스트는 곧 삭제됩니다.")
        );

        // when
        blogPostRepository.delete(post);

        // then
        assertThat(blogPostRepository.findById(post.getId()))
                .isEmpty();
    }


}