package com.posth.posth.domain.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCreateRequest {
    private String content;
}
