package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.ENUM.QuestionStatus;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.question.QuestionRepository;
import com.posth.posth.domain.question.dto.QuestionCreateRequest;
import com.posth.posth.domain.question.dto.QuestionResponse;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.domain.Reply;
import com.posth.posth.domain.reply.service.ReplyService;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ReplyService replyService;
    private final AuthUtil authUtil;

    @Transactional
    public QuestionResponse createQuestion(QuestionCreateRequest requestDto) {
        Member member = authUtil.getCurrentMember();

        Question question = Question.builder()
                .member(member)
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .build();

        Question savedQuestion = questionRepository.save(question);
        return new QuestionResponse(savedQuestion);
    }

    public Optional<QuestionResponse> getRandomQuestionByCategory(QuestionCategory category) {
        Member member = authUtil.getCurrentMember();

        return questionRepository.findRandomOneByCategory(category.name(), member.getId())
                .map(QuestionResponse::new);
    }

    @Transactional
    public Long createReplyForQuestion(Long questionId, ReplyCreateRequest requestDto) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

        if (question.getStatus() == QuestionStatus.CLOSED) {
            throw new IllegalStateException("이미 답변이 달려 마감된 질문입니다.");
        }

        question.close();

        return replyService.createReply(question, requestDto);
    }

    public Page<QuestionResponse> getMyQuestions(Pageable pageable) {
        Member member = authUtil.getCurrentMember();
        return questionRepository.findByMemberId(member.getId(), pageable)
                .map(QuestionResponse::new);
    }

    public QuestionResponse getQuestion(Long questionId){
        return questionRepository.findById(questionId)
                .map(QuestionResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
    }
}