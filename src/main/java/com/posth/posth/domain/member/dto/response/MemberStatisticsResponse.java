package com.posth.posth.domain.member.dto.response;

import java.util.Map;

public record MemberStatisticsResponse(
        int questionCount,
        int replyCount,
        int receivedLikeCount,
        Map<Integer, Integer> reactionCountInfo
) {
    public static MemberStatisticsResponse of(int questionCount, int replyCount, int receivedLikeCount, Map<Integer, Integer> reactionCountInfo) {
        return new MemberStatisticsResponse(questionCount, replyCount, receivedLikeCount, reactionCountInfo);
    }
}
