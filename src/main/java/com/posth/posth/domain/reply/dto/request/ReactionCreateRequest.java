package com.posth.posth.domain.reply.dto.request;

import com.posth.posth.domain.reply.domain.ReactionType;

import java.util.List;

public record ReactionCreateRequest(
    ReactionType reactionType,
    List<Integer> goodThings,
    String thankMessage
) {}
