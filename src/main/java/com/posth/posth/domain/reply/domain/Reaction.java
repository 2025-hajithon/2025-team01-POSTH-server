package com.posth.posth.domain.reply.domain;

import com.posth.posth.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long id;

    @Column(name = "reaction_type")
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @Column(name = "reaction_good_type")
    private String goodTypes;

    @Column(name = "reaction_thank_message")
    private String thankMessage;

    @JoinColumn(name = "reply_id")
    @OneToOne
    private Reply reply;

    @Builder(access = AccessLevel.PRIVATE)
    private Reaction(ReactionType reactionType, String goodTypes, String thankMessage, Reply reply) {
        this.reactionType = reactionType;
        this.goodTypes = goodTypes;
        this.thankMessage = thankMessage;
        this.reply = reply;
    }

    public static Reaction create(ReactionType reactionType, String goodTypes, String thankMessage, Reply reply) {
        return Reaction.builder()
                .reactionType(reactionType)
                .goodTypes(goodTypes)
                .thankMessage(thankMessage)
                .reply(reply)
                .build();
    }
}
