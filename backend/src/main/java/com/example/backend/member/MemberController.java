package com.example.backend.member;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody CreateMemberRequest request) {
        Member member = new Member(null, request.email(), request.name());
        return memberRepository.save(member);
    }

    @GetMapping
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    public record CreateMemberRequest(String email, String name) {
    }
}
