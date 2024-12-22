package com.example.bloghelper.dto;

public record LoginResponse(
        String token,
        MemberResponse member
) {}
