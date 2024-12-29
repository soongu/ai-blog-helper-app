package com.study.springprj.chap04;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v4-2")
public class ResponseEntityController {

    @Getter @Setter @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class User {
        private Long id;
        private String nickName;
        private int age;
    }

    // 가상의 사용자 데이터 저장소
    private final Map<Long, User> userRepository = Map.of(
            1L, new User(1L, "홍길동", 25),
            2L, new User(2L, "김팥쥐", 30),
            3L, new User(3L, "박콩콩", 44)
    );

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> users(@PathVariable Long userId) {
        // 사용자 저장소에서 데이터 조회
        User foundUser = userRepository.get(userId);

        if (foundUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("사용자를 찾을 수 없습니다.");
        }

        return ResponseEntity
//                .status(HttpStatus.OK)
                .ok()
                .body(foundUser);
    }


}
