package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.ENUM.QuestionStatus;
import com.posth.posth.domain.question.dto.QuestionCreateRequest;
import com.posth.posth.domain.question.dto.QuestionResponse;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.service.ReplyService;
import com.posth.posth.global.exception.exception.QuestionException;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.posth.posth.global.exception.ErrorMessage.*;

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
                .orElseThrow(() -> new QuestionException(QUESTION_NOT_EXISTS));

        if (question.getStatus() == QuestionStatus.CLOSED) {
            throw new IllegalStateException(QUESTION_ALREADY_FINISHED);
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
                .orElseThrow(() -> new IllegalArgumentException(QUESTION_NOT_EXISTS));
    }

    @Transactional
    public void deleteArchiveQuestion(Long questionId) {
        Member member = authUtil.getCurrentMember();
        Question question =  questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException(QUESTION_NOT_EXISTS));

        if (!question.getMember().equals(member)) {
            throw new IllegalStateException(QUESTION_NOT_MINE);
        }

        question.deleteArchiveQuestioner();
    }
}