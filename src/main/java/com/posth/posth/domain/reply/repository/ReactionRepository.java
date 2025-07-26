package com.posth.posth.domain.reply.repository;

import com.posth.posth.domain.reply.domain.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction,Long> {}
