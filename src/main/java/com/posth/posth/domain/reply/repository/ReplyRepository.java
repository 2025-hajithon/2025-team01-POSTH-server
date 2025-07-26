package com.posth.posth.domain.reply.repository;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByMember(Member member);

    List<Reply> findAllByQuestion_Member(Member member);
}
