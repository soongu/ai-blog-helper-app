package com.example.bloghelper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // Lombok 어노테이션으로, 필드들의 Getter 메서드를 자동 생성합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Lombok 어노테이션으로 파라미터가 없는 생성자를 생성하되, 접근 레벨을 PROTECTED로 지정합니다.
// 외부에서 직접 기본 생성자를 사용할 수 없게 하여 DTO 객체 생성 시 필요한 조건을 강화합니다.
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수입니다")
    // 제목 필드는 비어있거나 공백(빈 문자열)일 수 없음을 검증합니다.
    // "제목은 필수입니다" 라는 메시지는 검증 실패 시 사용자에게 반환될 에러 메시지입니다.
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    // 내용 필드 또한 비어있거나 공백일 수 없음을 검증합니다.
    // "내용은 필수입니다"는 유효성 검증 실패 시 반환될 에러 메시지입니다.
    private String content;
}
