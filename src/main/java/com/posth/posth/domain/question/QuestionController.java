package com.posth.posth.domain.question;

import com.posth.posth.domain.question.ENUM.QuestionCategory;
import com.posth.posth.domain.question.dto.QuestionCreateRequest;
import com.posth.posth.domain.question.dto.QuestionResponse;
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
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Long> createQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody QuestionCreateRequest requestDto) {

        Long memberId = userPrincipal.getMemberId();
        Long questionId = questionService.createQuestion(memberId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(questionId);
    }

    @GetMapping
    public ResponseEntity<Page<QuestionResponse>> getQuestionsByCategory(
            @RequestParam("category") QuestionCategory category,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<QuestionResponse> questions = questionService.getQuestionsByCategory(category, pageable);

        return ResponseEntity.ok(questions);
    }
}
