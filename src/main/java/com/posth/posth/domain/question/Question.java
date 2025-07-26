package com.posth.posth.domain.question;

import com.posth.posth.domain.common.BaseEntity;
import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.ENUM.QuestionStatus;
import com.posth.posth.domain.reply.domain.Reply;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_category", nullable = false)
    private QuestionCategory category;

    @Column(name = "question_content", nullable = false, length = 3000)
    private String content;

    @Column(name = "question_is_deleted_questioner")
    private Boolean isDeletedQuestioner = false;

    @Column(name = "question_is_deleted_replier")
    private Boolean isDeletedReplier = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Reply reply;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionStatus status = QuestionStatus.OPEN;

    @Builder
    public Question(QuestionCategory category, String content, Member member) {
        this.category = category;
        this.content = content;
        this.member = member;
    }


    public void close(){
        this.status=QuestionStatus.CLOSED;
    }

    public void deleteArchiveQuestioner() {
        this.isDeletedQuestioner = true;
    }

    public void deleteArchiveReplier() {
        this.isDeletedReplier = true;
    }
}
