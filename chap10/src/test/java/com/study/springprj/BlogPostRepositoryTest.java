package com.study.springprj;

import com.study.springprj.chap08.entity.BlogPost;
import com.study.springprj.chap09.BlogPostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BlogPostRepositoryTest {

    @Autowired
    BlogPostRepository repository;


    @BeforeEach
    void beforeEach() {
        for (int i = 1; i <= 3; i++) {
            BlogPost blogPost = new BlogPost();
            blogPost.setTitle("테스트 제목" + i);
            blogPost.setContent("테스트 내용" + i);

            repository.save(blogPost);
        }
    }


    @Test
    @DisplayName("블로그 게시물을 1개 생성해야 한다.")
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
    }

    @Test
    @DisplayName("게시물 목록을 전체조회한다.")
    void findAllTest() {
        // given

        // when
        List<BlogPost> postList = repository.findAll();

        // then
        assertThat(postList.size()).isEqualTo(3);

        postList.forEach(System.out::println);
    }

    @Test
    @DisplayName("아이디가 2인 게시물을 개별조회해야 한다.")
    void findOneTest() {
        //given
        Long id = 2L;

        //when
        BlogPost blogPost = repository.findById(id).orElseThrow();

        //then
        System.out.println("blogPost = " + blogPost);
        assertThat(blogPost).isNotNull();
        assertThat(blogPost.getTitle()).isEqualTo("테스트 제목2");
    }

    @Test
    @DisplayName("id가 3인 게시물을 삭제해야한다.")
    void deleteTest() {
        //given
        Long id = 3L;

        //when
        repository.deleteById(id);

        //then : 삭제가 완료되면 다시 3번게시물을 조회했을 때 예외가 발생할 것이다.
        assertThatThrownBy(() -> repository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("id가 2번인 게시물의 제목과 내용을 수정")
    void updateTest() {
        //given
        Long id = 2L;
        String newTitle = "새로운 제목";
        String newContent = "새로운 내용";

        //when
        BlogPost blogPost = repository.findById(id).orElseThrow();
        blogPost.setTitle(newTitle);
        blogPost.setContent(newContent);

        BlogPost updated = repository.save(blogPost);

        //then
        assertThat(updated.getTitle()).isEqualTo("새로운 제목");
    }

}