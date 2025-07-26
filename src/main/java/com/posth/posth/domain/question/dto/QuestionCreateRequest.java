package com.posth.posth.domain.question.dto;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionCreateRequestDto {
    private QuestionCategory category;
    private String content;
}
