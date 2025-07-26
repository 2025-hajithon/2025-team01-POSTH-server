package com.posth.posth.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String loginId;
    private String password;
    private String nickname;
}
