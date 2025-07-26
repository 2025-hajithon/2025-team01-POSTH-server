package com.posth.posth.domain.reply.service;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.Reply;
import com.posth.posth.domain.reply.dto.request.ReactionCreateRequest;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.dto.response.ReplySimpleResponse;
import com.posth.posth.domain.reply.repository.ReactionRepository;
import com.posth.posth.domain.reply.repository.ReplyRepository;
import com.posth.posth.global.exception.exception.ReplyException;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.posth.posth.global.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReactionRepository reactionRepository;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    public List<ReplySimpleResponse> getReplies() {
        Member member = authUtil.getCurrentMember();

        return replyRepository.findUnreadRepliesByQuestionAuthor(member)
                .stream()
                .map(ReplySimpleResponse::from)
                .toList();
    }

    @Transactional
    public ReplyResponse getReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(REPLY_NOT_EXISTS));
        reply.read();
        return ReplyResponse.from(reply);
    }

    @Transactional(readOnly = true)
    public ReplyResponse getMySentReplyDetail(Long replyId) {
        Member member = authUtil.getCurrentMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(REPLY_NOT_EXISTS));

        if (!reply.getMember().equals(member)) {
            throw new ReplyException(REPLY_NOT_MINE);
        }

        return ReplyResponse.from(reply);
    }

    @Transactional(readOnly = true)
    public List<ReplyResponse> getMySentReplies() {
        Member member = authUtil.getCurrentMember();
        return replyRepository.findAllByMemberAndNotDeletedReplier(member)
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
                .orElseThrow(() -> new ReplyException(REPLY_NOT_EXISTS));

        if (!reply.getQuestion().getMember().equals(member)) {
            throw new ReplyException(REPLY_NOT_FOR_MY_QUESTION);
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

    @Transactional
    public void deleteArchiveReply(Long replyId) {
        Member member = authUtil.getCurrentMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(REPLY_NOT_EXISTS));

        if (!reply.getMember().equals(member)) {
            throw new ReplyException(REPLY_NOT_MINE);
        }

        reply.getQuestion().deleteArchiveReplier();
    }
}
