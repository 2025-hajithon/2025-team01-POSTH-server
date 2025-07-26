package com.posth.posth.domain.member;

import com.posth.posth.domain.member.dto.request.SignUpRequestDto;
import com.posth.posth.domain.member.dto.response.MemberMyPageResponse;
import com.posth.posth.domain.member.dto.response.MemberStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/my")
    public ResponseEntity<MemberMyPageResponse> getMyPage() {
        var response = memberService.getMyPage();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my/statistics")
    public ResponseEntity<MemberStatisticsResponse> getMyStatistics() {
        var response = memberService.getMyStatistics();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/my")
    public ResponseEntity<String> withdraw() {
        memberService.withdraw();
        return ResponseEntity.ok("회원탈퇴가 성공적으로 처리되었습니다.");
    }
}
