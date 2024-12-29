package com.study.springprj.chap03;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3")
public class RequestController {

    // ===  http://localhost:9000/api/v3/products?category=electronics&name=aircon&price=2000000
    @GetMapping("/products")
    public String products(
            String category
            , String name
            , @RequestParam(required = false, defaultValue = "1000") int price
    ) {
        return """
                카테고리: %s,
                제품명: %s,
                가격: %d원
                """.formatted(category, name, price);
    }

    // === /api/v3/blogs/99?page=14
    @GetMapping("/blogs/{id}")
    public String blogs(
            @PathVariable("id") String id
            , @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return """
                %d페이지의 %s번 블로그 게시물을 조회합니다.
                """.formatted(page, id);
    }


    @PostMapping("/blogs")
    public String blogs(
           @RequestBody BlogDto blog
    ) {
        return """
                글번호: %d,
                제목: %s,
                내용: %s,
                추천수: %d
                """.formatted(blog.getId(), blog.getTitle(),
                blog.getContent(), blog.getRecommend());
    }

}
