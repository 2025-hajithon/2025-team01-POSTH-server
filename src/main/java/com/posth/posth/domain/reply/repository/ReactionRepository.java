package com.posth.posth.domain.reply.repository;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.reply.domain.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction,Long> {

    @Query("select r from Reaction r where r.reply.member = :member")
    List<Reaction> findAllByMyReply(Member member);
}
