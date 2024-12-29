package com.study.springprj.chap09.repository;

import com.study.springprj.chap08.entity.BlogPost;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class BlogPostRepositoryTest {

    @Autowired
    BlogPostRepository repository;

    @Autowired
    TestEntityManager em;

    @BeforeEach
    void before() {
        for (int i = 1; i <= 3; i++) {
            BlogPost blogPost = new BlogPost();
            blogPost.setTitle("테스트 제목" + i);
            blogPost.setContent("테스트 내용" + i);

            repository.save(blogPost);

            em.flush();
            em.clear();
        }
    }

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void saveTest() {

        // given
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("테스트 제목");
        blogPost.setContent("테스트 내용");

        // when
        BlogPost saved = repository.save(blogPost);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("테스트 제목");
        assertThat(saved.getContent()).isEqualTo("테스트 내용");
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void readTest() {

        // given
        Long givenId = 1L;

        // When
        BlogPost foundPost = repository.findById(givenId).orElseThrow();

        // Then
        assertThat(foundPost.getId()).isEqualTo(givenId);
        assertThat(foundPost.getTitle()).isEqualTo("테스트 제목1");
        assertThat(foundPost.getContent()).isEqualTo("테스트 내용1");
    }

    @Test
    void deleteTest() {
        // Given
        Long givenId = 3L;

        // When
        repository.deleteById(givenId);

        em.flush();
        em.clear();

        // Then
        assertThatThrownBy(() -> repository.findById(givenId).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTest() {
        // Given
        Long givenId = 2L;
        String newTitle = "업데이트된 제목";
        String newContent = "업데이트된 내용";

        // When
        BlogPost foundPost = repository.findById(givenId).orElseThrow();
        foundPost.setTitle(newTitle);
        foundPost.setContent(newContent);

        em.flush();
        em.clear();

        BlogPost updatedPost = repository.save(foundPost);

        // Then
        assertThat(updatedPost.getId()).isEqualTo(foundPost.getId());
        assertThat(updatedPost.getTitle()).isEqualTo("업데이트된 제목");
        assertThat(updatedPost.getContent()).isEqualTo("업데이트된 내용");
    }

}