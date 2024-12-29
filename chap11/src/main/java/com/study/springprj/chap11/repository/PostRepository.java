package com.study.springprj.chap11.repository;

import com.study.springprj.chap11.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


}
