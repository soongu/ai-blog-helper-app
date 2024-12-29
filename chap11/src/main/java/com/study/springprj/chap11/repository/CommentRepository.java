package com.study.springprj.chap11.repository;

import com.study.springprj.chap11.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
