package com.posth.posth.domain.question.dto;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.ReactionType;
import com.posth.posth.domain.reply.domain.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class QuestionResponse {
    private Long questionId;
    private QuestionCategory category;
    private String content;
    private String authorNickname;
    private String replyContent;
    private LocalDateTime replyAt;
    private String replierNickname;
    private ReactionType reactionType;
    private String goodTypes;
    private String thankMessage;

    public QuestionResponse(Question question) {
        this.questionId = question.getId();
        this.category = question.getCategory();
        this.content = question.getContent();
        this.authorNickname = question.getMember().getNickname();

        Reply reply = question.getReply();
        if (reply != null) {
            this.replyContent = reply.getContent();
            this.replyAt = reply.getCreatedAt();
            this.replierNickname = reply.getMember().getNickname();

            Reaction reaction = reply.getReaction();
            if (reaction != null) {
                this.reactionType = reaction.getReactionType();
                this.goodTypes = reaction.getGoodTypes();
                this.thankMessage = reaction.getThankMessage();
            }
        }
    }
}
