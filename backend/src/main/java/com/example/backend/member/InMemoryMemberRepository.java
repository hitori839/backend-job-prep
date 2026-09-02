package com.example.backend.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMemberRepository implements MemberRepository {
    
    private final Map<Long, Member> members = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Member save(Member member) {
        validate(member);

        if (findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Long id = sequence.incrementAndGet();
        Member savedMember = new Member(id, member.getEmail(), member.getName());
        members.put(id, savedMember);
        return savedMember;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return members.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    @Override
    public void deleteById(Long id) {
        members.remove(id);
    }

    private void validate(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (member.getName() == null || member.getName().isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
    }
}
