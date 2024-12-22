package com.example.bloghelper.service;

import com.example.bloghelper.dto.LoginRequest;
import com.example.bloghelper.dto.LoginResponse;
import com.example.bloghelper.dto.MemberResponse;
import com.example.bloghelper.entity.Member;
import com.example.bloghelper.exception.AuthenticationException;
import com.example.bloghelper.jwt.JwtProvider;
import com.example.bloghelper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.generateToken(member);
        return new LoginResponse(token, MemberResponse.from(member));
    }
}

