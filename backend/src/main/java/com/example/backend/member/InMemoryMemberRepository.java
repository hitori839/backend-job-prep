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
}
