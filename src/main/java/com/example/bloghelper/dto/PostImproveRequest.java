package com.example.bloghelper.dto;

import jakarta.validation.constraints.NotNull;

public record PostImproveRequest(
        @NotNull(message = "개선 방향은 필수입니다")
        ImprovementType type,
        // 선택: 추가 지시
        String additionalInstructions
) {
    public enum ImprovementType {
        SEO_OPTIMIZE("SEO 최적화"),
        READABILITY("가독성 개선"),
        PROFESSIONAL("전문성 강화"),
        CASUAL("친근한 톤으로 변경");

        private final String description;

        ImprovementType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
