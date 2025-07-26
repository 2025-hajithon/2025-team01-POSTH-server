package com.posth.posth.domain.reply.controller;

import com.posth.posth.domain.reply.dto.request.ReactionCreateRequest;
import com.posth.posth.domain.reply.dto.response.ReplyResponse;
import com.posth.posth.domain.reply.dto.response.ReplySimpleResponse;
import com.posth.posth.domain.reply.service.ReplyService;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list")
    public ResponseEntity<List<ReplySimpleResponse>> getReplies() {
        var response = replyService.getReplies();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyResponse> getReply(@PathVariable Long replyId) {
        var response = replyService.getReply(replyId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{replyId}/reaction")
    public ResponseEntity<Void> createReaction(@PathVariable Long replyId, @RequestBody ReactionCreateRequest request) {
        replyService.createReaction(replyId, request);
        return ResponseEntity.ok().build();
    }
}
