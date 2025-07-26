package com.posth.posth.domain.question;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByCategory(QuestionCategory category, Pageable pageable);
    Page<Question> findByMemberId(Long memberId, Pageable pageable);

    @Query(value = "SELECT * FROM question WHERE question_category = :category ORDER BY RAND()",
            nativeQuery = true)
    Page<Question> findByCategoryRandomly(@Param("category") String category, Pageable pageable);
}
