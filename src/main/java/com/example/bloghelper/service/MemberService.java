package com.example.bloghelper.service;

import com.example.bloghelper.dto.MemberResponse;
import com.example.bloghelper.dto.MemberSignupRequest;
import com.example.bloghelper.entity.Member;
import com.example.bloghelper.exception.MemberEmailDuplicateException;
import com.example.bloghelper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse signup(MemberSignupRequest request) {
        validateSignupRequest(request);

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        Member savedMember = memberRepository.save(member);
        log.info("회원가입 완료: {}", savedMember.getEmail());

        return MemberResponse.from(savedMember);
    }

    private void validateSignupRequest(MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberEmailDuplicateException("이미 가입된 이메일입니다: " + request.email());
        }
    }
}

