package com.example.backend.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceJpaTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void Service를_통해_회원가입하면_DB에_저장된다() {
        Member savedMember = memberService.register(
                "service@example.com", "Service 회원");

        assertThat(savedMember.getId()).isNotNull();
        assertThat(memberJpaRepository.findById(savedMember.getId()))
                .isPresent();
    }

    @Test
    void 같은_이메일은_Service에서_차단한다() {
        memberService.register("duplicate@example.com", "첫 번째");

        assertThatThrownBy(() -> memberService.register(
                "duplicate@example.com", "두 번째"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

    @Test
    void 회원_이름을_변경하면_변경_감지로_DB에_반영된다() {
        Member savedMember = memberService.register(
                "change@example.com", "변경 전");

        memberService.changeName(savedMember.getId(), "변경 후");

        Member foundMember = memberJpaRepository
                .findById(savedMember.getId())
                .orElseThrow();
        assertThat(foundMember.getName()).isEqualTo("변경 후");
    }
}
