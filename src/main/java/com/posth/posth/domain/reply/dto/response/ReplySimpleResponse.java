package com.posth.posth.domain.reply.dto.response;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.reply.domain.Reply;

import java.time.LocalDateTime;

public record ReplySimpleResponse(
        Long replyId,
        LocalDateTime replyAt,
        QuestionCategory questionCategory) {

    public static ReplySimpleResponse from(Reply reply) {
        return new ReplySimpleResponse(
                reply.getId(),
                reply.getCreatedAt(),
                reply.getQuestion().getCategory()
        );
    }
}
