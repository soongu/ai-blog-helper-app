package com.example.bloghelper.controller;

import com.example.bloghelper.dto.MemberResponse;
import com.example.bloghelper.dto.MemberSignupRequest;
import com.example.bloghelper.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(
            @RequestBody @Valid MemberSignupRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(memberService.signup(request));
    }
}

