package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select q from Question q " +
            "left join fetch q.reply r " +
            "left join fetch r.member " +
            "left join fetch r.reaction " +
            "where q.member.id = :memberId and not q.isDeletedQuestioner",
            countQuery = "select count(q) from Question q where q.member.id = :memberId and not q.isDeletedQuestioner")
    Page<Question> findByMemberId(Long memberId, Pageable pageable);

    @Query(value = "SELECT * FROM question WHERE question_category = :category AND status = 'OPEN' AND member_id <> :memberId ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Optional<Question> findRandomOneByCategory(@Param("category") String category, @Param("memberId") Long memberId);

    Integer countByMember(Member member);
}
