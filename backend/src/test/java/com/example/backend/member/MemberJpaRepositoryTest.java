package com.example.backend.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원을_저장하고_ID로_조회한다() {
        Member member = new Member("repository@example.com", "Repository 회원");

        Member savedMember = memberJpaRepository.save(member);

        Optional<Member> foundMember = memberJpaRepository.findById(savedMember.getId());

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail())
                .isEqualTo("repository@example.com");
    }

    @Test
    void 이메일로_회원을_조회한다() {
        memberJpaRepository.save(
                new Member("email@example.com", "이메일 회원"));

        Optional<Member> foundMember = memberJpaRepository
                .findByEmail("email@example.com");

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getName())
                .isEqualTo("이메일 회원");
    }

    @Test
    void 전체_회원을_조회한다() {
        memberJpaRepository.save(new Member("one@example.com", "일번"));
        memberJpaRepository.save(new Member("two@example.com", "이번"));

        assertThat(memberJpaRepository.findAll()).hasSize(2);
    }
}
