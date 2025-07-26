package com.posth.posth.domain.auth.service;

import com.posth.posth.domain.auth.dto.request.LoginRequest;
import com.posth.posth.domain.auth.dto.response.LoginResponse;
import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findBy
        return new LoginResponse("");
    }
}
