package com.posth.posth.domain.reply.controller;

import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list")
    public ResponseEntity<List<ReplyResponse>> getReplies() {
        var response = replyService.getReplies();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyResponse> getReply(@PathVariable Long replyId) {
        var response = replyService.getReply(replyId);
        return ResponseEntity.ok(response);
    }
}
