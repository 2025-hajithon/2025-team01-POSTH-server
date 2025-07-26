package com.posth.posth.domain.archive;

import com.posth.posth.domain.question.QuestionService;
import com.posth.posth.domain.question.dto.QuestionResponse;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class ArchiveController {

    private final QuestionService questionService;
    private final ReplyService replyService;

    @GetMapping("/archive/questions")
    public ResponseEntity<Page<QuestionResponse>> getMyQuestions(Pageable pageable) {
        return ResponseEntity.ok(questionService.getMyQuestions(pageable));
    }

    // 내 답변 아카이브 조회 API
    @GetMapping("/archive/replies")
    public ResponseEntity<List<ReplyResponse>> getMySentReplies() {
        return ResponseEntity.ok(replyService.getMySentReplies());
    }

    // 우편함(읽지 않은 답장) 조회 API
    @GetMapping("/inbox/replies")
    public ResponseEntity<List<Long>> getInboxReplies() {
        return ResponseEntity.ok(replyService.getReplies());
    }
}