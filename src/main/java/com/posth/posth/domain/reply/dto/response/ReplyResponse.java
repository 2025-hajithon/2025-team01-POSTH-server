package com.posth.posth.domain.reply.dto.response;

import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.ReactionType;
import com.posth.posth.domain.reply.domain.Reply;

import java.time.LocalDateTime;
import java.util.List;

public record ReplyResponse(
        Long replyId,
        String questionContent,
        String questionerNickname,
        LocalDateTime questionAt,
        String replyContent,
        String replierNickname,
        LocalDateTime replyAt,
        ReactionType reactionType,
        List<Integer> goodTypes,
        String thankMessage
) {
    public static ReplyResponse from(Reply reply) {
        ReactionType reactionType = null;
        List<Integer> goodTypes = null;
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
