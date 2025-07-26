package com.posth.posth.domain.reply.repository;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByMember(Member member);

    @Query("select r from Reply r where r.question.member = :member and not r.isRead")
    List<Reply> findUnreadRepliesByQuestionAuthor(Member member);
}
