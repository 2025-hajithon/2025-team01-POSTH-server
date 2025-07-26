package com.posth.posth.domain.reply.domain;

import com.posth.posth.domain.common.BaseEntity;
import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "reply_content")
    private String content;

    @Column(name = "reply_is_read")
    private boolean isRead = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void read() {
        this.isRead = true;
    }
}
