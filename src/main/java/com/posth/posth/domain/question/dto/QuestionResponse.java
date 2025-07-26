package com.posth.posth.domain.question.dto;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.Question;
import lombok.Getter;

@Getter
public class QuestionResponse {
    private Long questionId;
    private QuestionCategory category;
    private String content;
    private String authorNickname;

    public QuestionResponse(Question question) {
        this.questionId = question.getId();
        this.category = question.getCategory();
        this.content = question.getContent();
        this.authorNickname = question.getMember().getNickname();
    }
}
