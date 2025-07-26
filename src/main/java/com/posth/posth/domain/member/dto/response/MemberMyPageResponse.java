package com.posth.posth.domain.member.dto.response;

import com.posth.posth.domain.member.Member;

public record MemberMyPageResponse(
        String nickname
) {
    public static MemberMyPageResponse from(Member member) {
        return new MemberMyPageResponse(member.getNickname());
    }
}
