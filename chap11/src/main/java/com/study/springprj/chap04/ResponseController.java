package com.study.springprj.chap04;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v4-1")
public class ResponseController {

    // html을 응답하는 메서드
    // 응답 형태 : text
    @GetMapping("/html")
    public String htmlResponse(@RequestParam String subject) {
        return """
                <html>
                <body>
                    <h1>Hello %s!</h1>
                </body>
                </html>
                """.formatted(subject);
    }

    // JSON 배열 응답
    @GetMapping("/foods")
    public String[] foods() {
        return new String[] {"떡볶이", "족발", "파스타", "치킨"};
    }

    @GetMapping("/hobbies")
    public List<String> hobbies() {
        return List.of("산책", "수영", "러닝");
    }

    // JSON 객체 응답
    @GetMapping("/students")
    public Student student() {
        return new Student(10L, "김추추", 4);
    }

    @GetMapping("/pets")
    public Map<String, Object> pets() {
        return Map.of(
                "name", "뽀삐",
                "age", 3,
                "injection", true
        );
    }

    // JSON 객체 배열
    @GetMapping("/all")
    public List<Student> all() {
        return List.of(
                new Student(1L, "하츄핑", 2),
                new Student(2L, "또또핑", 3),
                new Student(3L, "채채핑", 4),
                new Student(4L, "키키핑", 1)
        );
    }


    // 학생 정보 클래스
    @Getter @Setter @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Student {
        private Long id;
        private String name;
        private int grade;
    }

}
