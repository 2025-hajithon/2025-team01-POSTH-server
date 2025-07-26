package com.posth.posth.global.util;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        String loginId = getLoggedInMemberStudentId();
        return memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 멤버입니다.")
        );
    }

    private String getLoggedInMemberStudentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }
}
