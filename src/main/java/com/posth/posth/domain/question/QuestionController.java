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
import java.util.Map;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<?> getRandomQuestionByCategory(
            @RequestParam("category") QuestionCategory category) {

        Optional<QuestionResponse> optionalQuestion = questionService.getRandomQuestionByCategory(category);

        if (optionalQuestion.isPresent()) {
            return ResponseEntity.ok(optionalQuestion.get());
        } else {
            return ResponseEntity.ok(Map.of("message", "해당 카테고리에 답변 가능한 질문이 없습니다."));
        }
    }

    @PostMapping("/{questionId}/reply")
    public ResponseEntity<Long> createReply(
            @PathVariable Long questionId,
            @RequestBody ReplyCreateRequest requestDto) {

        Long replyId = questionService.createReplyForQuestion(questionId,requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(replyId);
    }
}
