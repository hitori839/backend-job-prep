package com.example.backend.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setUp( ) {
        MemberRepository repository = new InMemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @Test
    void 회원가입에_성공한다() {
        Member member = memberService.register("minsu@example.com", "민수");

        assertThat(member.getId()).isEqualTo(1L);
        assertThat(member.getEmail()).isEqualTo("minsu@example.com");
        assertThat(member.getName()).isEqualTo("민수");
    }

    @Test
    void 같은_이메일로_가입할_수_없다() {
        memberService.register("same@example.com", "첫 번째");

        assertThatThrownBy(() -> memberService.register(
                "same@example.com", "두 번째"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

    @Test
    void 이메일이_비어_있으면_가입할_수_없다() {
        assertThatThrownBy(() -> memberService.register("", "민수"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 필수입니다.");
    }

    @Test
    void 이름이_비어_있으면_가입할_수_없다() {
        assertThatThrownBy(() -> memberService.register(
                "empty@example.com", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다.");
    }

    @Test
    void 가입한_회원을_ID로_조회한다() {
        Member savedMember = memberService.register(
                "find@example.com", "찾을 회원");

        Member foundMember = memberService.findById(savedMember.getId());

        assertThat(foundMember.getEmail()).isEqualTo("find@example.com");
    }
}