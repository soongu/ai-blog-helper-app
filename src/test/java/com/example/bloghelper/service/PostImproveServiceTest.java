package com.example.bloghelper.service;

import com.example.bloghelper.dto.PostCreateRequest;
import com.example.bloghelper.dto.PostImproveRequest;
import com.example.bloghelper.dto.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostImproveServiceTest {

    @Autowired
    PostImproveService postImproveService;
    @Autowired
    PostService postService;

    @Test
    void test() {
        PostResponse draftResponse = postService.createPostDraft(new PostCreateRequest("스타벅스")).block();
        System.out.println("draftResponse = " + draftResponse);

        PostImproveRequest request = new PostImproveRequest(
                PostImproveRequest.ImprovementType.SEO_OPTIMIZE,
                "좀 더 젊은 말투로 해주세요"
        );

        String response
                = postImproveService.improvePost(1L, request).block();

        System.out.println("Improve Response = " + response);
    }
}