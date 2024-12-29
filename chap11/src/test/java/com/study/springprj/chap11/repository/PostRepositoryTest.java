package com.study.springprj.chap11.repository;

import com.study.springprj.chap11.entity.Comment;
import com.study.springprj.chap11.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TestEntityManager em;

    Post post1, post2;
    Comment comment1, comment2, comment3;

    @BeforeEach
    void beforeEach() {
        // 2개의 게시물 생성
        post1 = new Post();
        post1.setTitle("첫번째 게시물");
        post1.setContent("첫번째 게시물 내용입니다.");

        post2 = new Post();
        post2.setTitle("두번째 게시물");
        post2.setContent("두번째 게시물 내용입니다.");

        postRepository.save(post1);
        postRepository.save(post2);

        // 3개의 댓글 생성
        comment1 = new Comment();
        comment1.setContent("1번 게시물의 1번 댓글");
        comment1.setPost(post1);

        comment2 = new Comment();
        comment2.setContent("1번 게시물의 2번 댓글");
        comment2.setPost(post1);

        comment3 = new Comment();
        comment3.setContent("2번 게시물의 1번 댓글");
        comment3.setPost(post2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("단방향 연관관계 테스트")
    void test1() {
        //given
        Long commentId = 1L;

        //when
        Comment foundComment = commentRepository.findById(commentId).orElseThrow();

        //then
        System.out.println("foundComment = " + foundComment);
        System.out.println("foundComment.getPost() = " + foundComment.getPost());
    }

    @Test
    @DisplayName("양방향 연관관계 테스트")
    void test2() {
        //given
        Long postId = 1L;

        //when
        Post foundPost = postRepository.findById(postId).orElseThrow();

        //then
        System.out.println("foundPost = " + foundPost);
        System.out.println("===== 댓글 목록 ====");
        foundPost.getComments().forEach(System.out::println);
    }

    @Test
    @DisplayName("게시물의 댓글목록에 댓글을 추가해보기")
    void test3() {
        //given
        Comment newComment1 = new Comment();
        newComment1.setContent("1번 게시물의 새로운 댓글1");

        Comment newComment2 = new Comment();
        newComment2.setContent("1번 게시물의 새로운 댓글2");

        //when
        Post post = postRepository.findById(1L).orElseThrow();
        post.addComment(newComment1);
        post.addComment(newComment2);

        System.out.println("새 댓글이 저장됨!");
        em.flush();

        // then
        System.out.println("새 댓글 목록");
        post.getComments().forEach(System.out::println);
    }

    @Test
    @DisplayName("게시물을 삭제하면 해당 게시물에 달린 댓글이 같이 지워져야 한다.")
    void test4() {

        postRepository.deleteById(1L);
        em.flush();

    }

    @Test
    @DisplayName("게시물 안에 댓글목록에서 댓글을 지우면 댓글이 삭제된다.")
    void test5() {

        Post post = postRepository.findById(1L).orElseThrow();
        Comment comment = commentRepository.findById(1L).orElseThrow();

        post.removeComment(comment);
        em.flush();

    }

}