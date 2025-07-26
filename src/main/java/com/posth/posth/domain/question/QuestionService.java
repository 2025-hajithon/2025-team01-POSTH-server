package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ReplyService replyService;
    private final AuthUtil authUtil;

    @Transactional
    public Long createQuestion(QuestionCreateRequest requestDto) {
        Member member = authUtil.getCurrentMember();

        Question question = Question.builder()
                .member(member)
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .build();

        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    public Page<QuestionResponse> getQuestionsByCategory(QuestionCategory category, Pageable pageable) {
        Page<Question> questions = questionRepository.findByCategoryRandomly(category.name(), pageable);

        return questions.map(QuestionResponse::new);
    }

    @Transactional
    public Long createReplyForQuestion(Long questionId, ReplyCreateRequest requestDto) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

        return replyService.createReply(question,requestDto);
    }

}