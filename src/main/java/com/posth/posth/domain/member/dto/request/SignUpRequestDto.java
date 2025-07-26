package com.posth.posth.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String loginId;
    private String password;
    private String nickname;
}
