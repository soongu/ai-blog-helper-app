package com.example.bloghelper.jwt;

import com.example.bloghelper.config.JwtProperties;
import com.example.bloghelper.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private Key key;

    // 객체 초기화 시 비밀키를 설정
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성 메서드
    public String generateToken(Member member) {
        Claims claims = Jwts.claims(); // JWT의 클레임 생성
        claims.put("email", member.getEmail()); // 클레임에 사용자 이메일 추가
        claims.put("id", member.getId());       // 클레임에 사용자 ID 추가

        return Jwts.builder()
                .setClaims(claims)                            // 클레임 설정
                .setIssuedAt(new Date())                     // 토큰 발급 시간
                .setIssuer(jwtProperties.getIssuer())         // 발급자 설정
                .setExpiration(createExpiration())            // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512)     // 서명 알고리즘과 비밀키 설정
                .compact();                                   // 토큰 생성
    }

    // JWT 토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // 비밀키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱 및 검증
            return true; // 검증 성공 시 true 반환
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다."); // 잘못된 서명
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다."); // 만료된 토큰
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다."); // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다."); // 토큰이 잘못됨
        }
        return false; // 검증 실패 시 false 반환
    }

    // JWT 토큰에서 인증 정보 추출 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token); // 토큰에서 클레임을 파싱

        // 클레임에서 이메일 추출 (Principal 역할)
        String email = claims.get("email", String.class);

        // 간단하게 ROLE_USER 권한을 부여
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        authorityList.add(authority);

        // UsernamePasswordAuthenticationToken으로 Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(email, null, authorityList);
    }

    // JWT 토큰에서 클레임을 파싱하는 메서드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // 비밀키 설정
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱
                    .getBody(); // 클레임 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료된 토큰이라도 클레임 반환
        }
    }

    // 토큰 만료 시간 생성 (예: 1시간 후)
    private Date createExpiration() {
        return new Date(System.currentTimeMillis() + (jwtProperties.getExpirationMinutes() * 60 * 1000));
    }
}
