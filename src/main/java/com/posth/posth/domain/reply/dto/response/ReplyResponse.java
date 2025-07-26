package com.posth.posth.domain.reply.dto.response;

import com.posth.posth.domain.reply.domain.Reply;

import java.time.LocalDateTime;

public record ReplyResponse(
        Long replyId,
        String questionContent,
        String questionerNickname,
        LocalDateTime questionAt,
        String replyContent,
        String replierNickname,
        LocalDateTime replyAt
) {
    public static ReplyResponse from(Reply reply) {
        return new ReplyResponse(
                reply.getId(),
                reply.getQuestion().getContent(),
                reply.getQuestion().getMember().getNickname(),
                reply.getQuestion().getCreatedAt(),
                reply.getContent(),
                reply.getMember().getNickname(),
                reply.getCreatedAt()
        );
    }
}
