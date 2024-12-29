package com.study.springprj.chap04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v4-2")
public class ResponseEntityController {

    // 사용자 데이터 모델
    @Getter
    @Setter
    @AllArgsConstructor
    private static class User {
        private Long id;
        private String name;
        private int age;
    }

    // 가상의 사용자 데이터 저장소
    private final Map<Long, User> userRepository = Map.of(
            1L, new User(1L, "홍길동", 25),
            2L, new User(2L, "김철수", 30),
            3L, new User(3L, "이영희", 28)
    );

    /**
     * 사용자 정보 조회
     * @param userId 사용자 ID
     * @return ResponseEntity에 담긴 사용자 정보 또는 에러 메시지
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        // 사용자를 데이터 저장소에서 조회
        User user = userRepository.get(userId);

        if (user == null) {
            // 사용자 미존재: 404 Not Found와 에러 메시지 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "사용자가 존재하지 않습니다");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // 사용자 존재: 200 OK와 사용자 정보 반환
        return ResponseEntity.ok(user);

    }



}
