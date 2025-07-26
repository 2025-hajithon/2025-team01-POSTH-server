package com.posth.posth.domain.question;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByCategory(QuestionCategory category, Pageable pageable);
    Page<Question> findByMemberId(Long memberId, Pageable pageable);
}
