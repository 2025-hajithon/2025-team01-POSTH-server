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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/member/my/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final QuestionService questionService;
    private final ReplyService replyService;

    @GetMapping("/question/list")
    public ResponseEntity<Page<QuestionResponse>> getMyQuestions(Pageable pageable) {
        return ResponseEntity.ok(questionService.getMyQuestions(pageable));
    }

    @GetMapping("/reply/list")
    public ResponseEntity<List<ReplyResponse>> getMySentReplies() {
        return ResponseEntity.ok(replyService.getMySentReplies());
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionResponse> getMySentQuestion(@PathVariable Long questionId){
        return ResponseEntity.ok(questionService.getQuestion(questionId));
    }

    @GetMapping("/reply/{replyId}")
    public ResponseEntity<ReplyResponse> getMySentReplyDetail(@PathVariable Long replyId) {
        return ResponseEntity.ok(replyService.getMySentReplyDetail(replyId));
    }
}