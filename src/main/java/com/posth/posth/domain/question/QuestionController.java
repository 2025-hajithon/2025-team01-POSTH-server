package com.posth.posth.domain.question;

import com.posth.posth.domain.member.Member;
import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.dto.QuestionCreateRequest;
import com.posth.posth.domain.question.dto.QuestionResponse;
import com.posth.posth.domain.question.dto.ReplyCreateRequest;
import com.posth.posth.domain.reply.service.ReplyService;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @RequestBody QuestionCreateRequest requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(requestDto));
    }

    @GetMapping()
    public ResponseEntity<QuestionResponse> getRandomQuestionByCategory(
            @RequestParam("category") QuestionCategory category) {

        QuestionResponse question = questionService.getRandomQuestionByCategory(category);

        return ResponseEntity.ok(question);
    }

    @PostMapping("/{questionId}/reply")
    public ResponseEntity<Long> createReply(
            @PathVariable Long questionId,
            @RequestBody ReplyCreateRequest requestDto) {

        Long replyId = questionService.createReplyForQuestion(questionId,requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(replyId);
    }
}
