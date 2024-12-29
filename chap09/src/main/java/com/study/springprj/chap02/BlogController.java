package com.study.springprj.chap02;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/blogs")
public class BlogController {

    @GetMapping
    public String getBlogs() {
        return "read blogs!";
    }
    @PostMapping
    public String postBlog() {
        return "create blog!";
    }
    @PutMapping
    public String updateBlog() {
        return "update blog!";
    }
    @DeleteMapping
    public String deleteBlog() {
        return "delete blog!";
    }

}
