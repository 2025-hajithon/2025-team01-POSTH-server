package com.posth.posth.domain.reply.service;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.reply.domain.Reply;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.repository.ReplyRepository;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final AuthUtil authUtil;

    public List<Long> getReplies() {
        Member member = authUtil.getCurrentMember();

        return replyRepository.findAllByQuestion_Member(member)
                .stream()
                .map(Reply::getId)
                .toList();
    }

    public ReplyResponse getReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));
        return ReplyResponse.from(reply);
    }
}
