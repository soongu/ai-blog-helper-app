package com.study.springprj.chap03;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3")
public class RequestController {

    @PostMapping("/blogs")
    public String blogs(
            @RequestBody BlogDto blog
    ) {
        return """
                글번호: %d,
                제목: %s,
                내용: %s,
                추천수: %d
                """.formatted(
                blog.getId()
                , blog.getTitle()
                , blog.getContent()
                , blog.getRecommend()
        );
    }
}
