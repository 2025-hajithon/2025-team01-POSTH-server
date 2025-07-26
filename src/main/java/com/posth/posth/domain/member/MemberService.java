package com.posth.posth.domain.member;

import com.posth.posth.domain.member.dto.request.SignUpRequestDto;
import com.posth.posth.domain.member.dto.response.MemberMyPageResponse;
import com.posth.posth.domain.member.dto.response.MemberStatisticsResponse;
import com.posth.posth.domain.question.QuestionRepository;
import com.posth.posth.domain.reply.domain.Reaction;
import com.posth.posth.domain.reply.domain.ReactionType;
import com.posth.posth.domain.reply.repository.ReactionRepository;
import com.posth.posth.domain.reply.repository.ReplyRepository;
import com.posth.posth.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;
    private final ReactionRepository reactionRepository;
    private final PasswordEncoder passwordEncoder;  // 추가
    private final AuthUtil authUtil;

    @Transactional
    public Long signUp(SignUpRequestDto requestDto) {
        if (memberRepository.existsByLoginId(requestDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = Member.builder()
                .loginId(requestDto.getLoginId())
                .password(encodedPassword)  // 해싱된 비밀번호 저장
                .nickname(requestDto.getNickname())
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public MemberMyPageResponse getMyPage() {
        Member member = authUtil.getCurrentMember();
        return MemberMyPageResponse.from(member);
    }

    public MemberStatisticsResponse getMyStatistics() {
        Member member = authUtil.getCurrentMember();

        System.out.println(member.getNickname());

        Integer questionCount = questionRepository.countByMember(member);
        Integer replyCount = replyRepository.countByMember(member);
        Integer recievedLikeCount = 0;

        int[] goodThingsCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        List<Reaction> reactions = reactionRepository.findAllByMyReply(member);
        for (Reaction reaction : reactions) {
            if (reaction.getReactionType().equals(ReactionType.GOOD)) {
                recievedLikeCount++;
            }
            for (String goodThing : reaction.getGoodTypes().split(",")) {
                goodThingsCount[Integer.parseInt(goodThing)]++;
            }
        }

        Map<Integer, Integer> goodThingsCountMap = new HashMap<>();
        for (int i = 0; i < goodThingsCount.length; i++) {
            if (goodThingsCount[i] > 0) {
                goodThingsCountMap.put(i, goodThingsCount[i]);
            }
        }

        LinkedHashMap<Integer, Integer> sortedCountMap = goodThingsCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // 값 기준 정렬
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a, // 병합 함수
                        LinkedHashMap::new // 순서 유지
                ));

        return MemberStatisticsResponse.of(
                questionCount,
                replyCount,
                recievedLikeCount,
                sortedCountMap
        );
    }
}