package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.member.MemberRepository;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.Question;
import com.posth.posth.domain.question.QuestionRepository;
import com.posth.posth.domain.question.dto.QuestionCreateRequest;
import com.posth.posth.domain.question.dto.QuestionResponse;
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
    private final MemberRepository memberRepository;

    @Transactional
    public Long createQuestion(Long memberId, QuestionCreateRequest requestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Question question = Question.builder()
                .member(member)
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .build();

        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    public Page<QuestionResponse> getQuestionsByCategory(QuestionCategory category, Pageable pageable) {
        Page<Question> questions = questionRepository.findByCategory(category, pageable);

        return questions.map(QuestionResponse::new);
    }
}