package com.posth.posth.domain.member;

import com.posth.posth.domain.question.QuestionService;
import com.posth.posth.domain.question.dto.QuestionResponse;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/member/my/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final QuestionService questionService;
    private final ReplyService replyService;

    @GetMapping("/question/list")
    public ResponseEntity<List<QuestionResponse>> getMyQuestions() {
        return ResponseEntity.ok(questionService.getMyQuestions());
    }

    @GetMapping("/reply/list")
    public ResponseEntity<List<ReplyResponse>> getMySentReplies() {
        return ResponseEntity.ok(replyService.getMySentReplies());
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionResponse> getMySentQuestion(@PathVariable Long questionId){
        return ResponseEntity.ok(questionService.getQuestion(questionId));
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteMySentQuestion(@PathVariable Long questionId){
        questionService.deleteArchiveQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reply/{replyId}")
    public ResponseEntity<ReplyResponse> getMySentReplyDetail(@PathVariable Long replyId) {
        return ResponseEntity.ok(replyService.getMySentReplyDetail(replyId));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<Void> deleteMySentReply(@PathVariable Long replyId){
        replyService.deleteArchiveReply(replyId);
        return ResponseEntity.noContent().build();
    }
}