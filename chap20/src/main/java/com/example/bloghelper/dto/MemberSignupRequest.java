package com.example.bloghelper.dto;

import com.example.bloghelper.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "회원가입 요청 DTO")
public record MemberSignupRequest(

        @Schema(
                description = "이메일",
                example = "user@example.com",
                required = true
        )

        @Email(message = "올바른 이메일 형식이 아닙니다")
        @NotBlank(message = "이메일은 필수입니다")
        String email,

        @Schema(
                description = "비밀번호 (8자 이상, 영문, 숫자, 특수문자 포함)",
                example = "Password1!",
                required = true
        )

        @NotBlank(message = "비밀번호는 필수입니다")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{8,}$",
                message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다"
        )
        String password,

        @Schema(
                description = "닉네임 (2-10자)",
                example = "블로거",
                required = true
        )

        @NotBlank(message = "닉네임은 필수입니다")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다")
        String nickname
) {}

