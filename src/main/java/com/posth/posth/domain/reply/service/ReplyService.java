package com.posth.posth.domain.reply.service;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.question.QuestionRepository;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.domain.Reply;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.repository.ReplyRepository;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    public List<Long> getReplies() {
        Member member = authUtil.getCurrentMember();

        return replyRepository.findAllByQuestion_Member(member)
                .stream()
                .map(Reply::getId)
                .toList();
    }

    @Transactional
    public ReplyResponse getReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));
        reply.read();
        return ReplyResponse.from(reply);
    }

    @Transactional
    public Long createReply(Question question, ReplyCreateRequest requestDto) {
        Member member = authUtil.getCurrentMember();

        Reply reply = Reply.builder()
                .content(requestDto.getContent())
                .question(question)
                .member(member)
                .build();

        replyRepository.save(reply);
        return reply.getId();
    }
}
