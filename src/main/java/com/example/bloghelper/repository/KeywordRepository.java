package com.example.bloghelper.repository;

import com.example.bloghelper.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    /**
     * 원본 키워드(originalKeyword)를 대소문자 구분 없이 검색하여
     * 해당 키워드를 Optional로 반환합니다.
     *
     * @param originalKeyword 검색할 키워드(대소문자 구분 없음)
     * @return 찾은 Keyword 엔티티를 Optional로 감싼 객체 (없으면 Optional.empty() 반환)
     */
    Optional<Keyword> findByOriginalKeywordIgnoreCase(String originalKeyword);

    /**
     * 특정 키워드(originalKeyword)가 데이터베이스에 존재하는지 대소문자를 구분하지 않고 확인합니다.
     *
     * @param originalKeyword 존재 여부를 확인할 키워드(대소문자 구분 없음)
     * @return 해당 키워드가 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByOriginalKeywordIgnoreCase(String originalKeyword);
}
