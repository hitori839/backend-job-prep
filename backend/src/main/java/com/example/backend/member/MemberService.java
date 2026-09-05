package com.example.backend.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public MemberService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public Member register(String email, String name) {
        validateRequired(email, name);

        if (memberJpaRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = new Member(email, name);
        return memberJpaRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void changeName(Long id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        Member member = findById(id);
        member.changeName(name);
    }

    private void validateRequired(String email, String name) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
    }
}
