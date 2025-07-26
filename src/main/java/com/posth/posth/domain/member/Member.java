package com.posth.posth.domain.member;

import com.posth.posth.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_login_id")
    private String loginId;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_nickname")
    private String nickname;

    public void withdraw() {
        this.loginId = null;
        this.password = null; // 비밀번호도 비활성화
        this.nickname = "탈퇴한 회원"; // 닉네임은 고유해야 할 수 있으므로 덮어쓰기
    }
}
