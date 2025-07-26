package com.posth.posth.domain.reply.dto.response;

import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.ReactionType;
import com.posth.posth.domain.reply.domain.Reply;

import java.time.LocalDateTime;

public record ReplyResponse(
        Long replyId,
        String questionContent,
        String questionerNickname,
        LocalDateTime questionAt,
        String replyContent,
        String replierNickname,
        LocalDateTime replyAt,
        ReactionType reactionType,
        String goodTypes,
        String thankMessage
) {
    public static ReplyResponse from(Reply reply) {
        ReactionType reactionType = null;
        String goodTypes = null;
        String thankMessage = null;

        Reaction reaction = reply.getReaction();
        if (reaction != null) {
            reactionType = reaction.getReactionType();
            goodTypes = reaction.getGoodTypes();
            thankMessage = reaction.getThankMessage();
        }

        return new ReplyResponse(
                reply.getId(),
                reply.getQuestion().getContent(),
                reply.getQuestion().getMember().getNickname(),
                reply.getQuestion().getCreatedAt(),
                reply.getContent(),
                reply.getMember().getNickname(),
                reply.getCreatedAt(),
                reactionType,
                goodTypes,
                thankMessage
        );
    }
}
