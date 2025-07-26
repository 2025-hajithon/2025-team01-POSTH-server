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

    public List<ReplyResponse> getReplies() {
        Member member = authUtil.getCurrentMember();

        return replyRepository.findAllByQuestion_Member(member)
                .stream()
                .map(ReplyResponse::from)
                .toList();
    }
}
