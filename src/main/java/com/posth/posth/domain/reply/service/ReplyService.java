package com.posth.posth.domain.reply.service;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.question.QuestionRepository;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.Reply;
import com.posth.posth.domain.reply.dto.request.ReactionCreateRequest;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.repository.ReactionRepository;
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
    private final ReactionRepository reactionRepository;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    public List<Long> getReplies() {
        Member member = authUtil.getCurrentMember();

        return replyRepository.findUnreadRepliesByQuestionAuthor(member)
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

    public ReplyResponse getMySentReplyDetail(Long replyId) {
        Member member = authUtil.getCurrentMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if (!reply.getMember().equals(member)) {
            throw new RuntimeException("자신이 작성한 답변만 조회할 수 있습니다.");
        }

        return ReplyResponse.from(reply);
    }

    public List<ReplyResponse> getMySentReplies() {
        Member member = authUtil.getCurrentMember();
        return replyRepository.findAllByMember(member)
                .stream()
                .map(ReplyResponse::from)
                .toList();
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

    @Transactional
    public void createReaction(Long replyId, ReactionCreateRequest request) {
        Member member = authUtil.getCurrentMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("reply 가 존재하지 않습니다."));

        if (!reply.getQuestion().getMember().equals(member)) {
            throw new RuntimeException("나의 질문에 대한 답변이 아닙니다.");
        }

        String goodThings = "";
        for (int i = 0; i < request.goodThings().size(); i++) {
            goodThings += request.goodThings().get(i).toString();
            if (i < request.goodThings().size() - 1) {
                goodThings += ",";
            }
        }

        Reaction reaction = Reaction.create(
                request.reactionType(),
                goodThings,
                request.thankMessage(),
                reply
        );

        reactionRepository.save(reaction);
    }
}
