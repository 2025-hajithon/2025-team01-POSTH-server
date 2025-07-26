package com.posth.posth.domain.question;

import com.posth.posth.domain.common.BaseEntity;
import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Reply> replies = new ArrayList<>();



    @Builder
    public Question(QuestionCategory category, String content, Member member) {
        this.category = category;
        this.content = content;
        this.member = member;
    }
}
