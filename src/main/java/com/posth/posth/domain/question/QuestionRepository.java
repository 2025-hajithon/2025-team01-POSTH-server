package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByMemberId(Long memberId, Pageable pageable);

    @Query(value = "SELECT * FROM question WHERE question_category = :category AND status = 'OPEN' ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Optional<Question> findRandomOneByCategory(@Param("category") String category);

    Integer countByMember(Member member);
}
