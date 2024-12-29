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

    //HTML 응답
    @GetMapping("/html")
    public String htmlResponse(@RequestParam String subject) {
        return """
                <html>
                    <body>
                        <h1>Hello %s!!</h1>
                    </body>
                </html>
                """.formatted(subject);
    }

    // JSON 배열 응답
    @GetMapping("/foods")
    public String[] foods() {
        return new String[] {"떡볶이", "족발", "파스타"};
    }

    @GetMapping("/hobbies")
    public List<String> hobbies() {
        return List.of("수영", "산책", "러닝");
    }

    // JSON 객체 응답
    @GetMapping("/students")
    public Student student() {
        return new Student(10L, "김초코", 3);
    }

    @GetMapping("/pets")
    public Map<String, Object> pets() {
        return Map.of(
                "name", "뽀삐",
                "age", 4,
                "injection", true
        );
    }

    // JSON 객체 배열
    @GetMapping("/students/all")
    public List<Student> studentsAll() {
        return List.of(
                new Student(1L, "하츄핑", 2),
                new Student(2L, "또또핑", 1),
                new Student(3L, "차차핑", 4)
        );
    }



    @Getter @Setter @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Student {
        private Long id;
        private String name;
        private int grade;
    }

}
