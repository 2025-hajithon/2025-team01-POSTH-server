package com.posth.posth.domain.auth.service;

import com.posth.posth.domain.auth.dto.request.LoginRequest;
import com.posth.posth.domain.auth.dto.response.LoginResponse;
import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import com.posth.posth.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));

        if (encoder.matches(request.password(), member.getPassword())) {
            String token = jwtUtil.generateToken(request.loginId());
            return new LoginResponse(token, member.getNickname());
        }

        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
}
