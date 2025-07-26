package com.posth.posth.domain.reply.repository;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.question.member = :member and not r.isRead")
    List<Reply> findUnreadRepliesByQuestionAuthor(Member member);

    Integer countByMember(Member member);

    @Query("select r from Reply r " +
            "left join fetch r.reaction " +
            "where r.member = :replier and not r.question.isDeletedReplier")
    List<Reply> findAllByMemberAndNotDeletedReplier(Member member);
}
