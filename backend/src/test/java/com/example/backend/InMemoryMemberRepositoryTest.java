package com.example.backend.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMemberRepositoryTest {
    private InMemoryMemberRepository repository;

    @BeforeEach
    void setUp( ) {
        repository = new InMemoryMemberRepository();
    }

    @Test
    void 회원을_저장하고_ID로_조회한다() {
        Member member = new Member(null, "minsu@example.com", "민수");

        Member savedMember = repository.save(member);

        assertThat(savedMember.getId()).isEqualTo(1L);
        assertThat(repository.findById(1L)).contains(savedMember);
    }

    @Test
    void 이메일로_회원을_조회한다() {
        Member member = new Member(null, "jiyoung@example.com", "지영");
        repository.save(member);

        assertThat(repository.findByEmail("jiyoung@example.com"))
                .isPresent()
                .get()
                .extracting(Member::getName)
                .isEqualTo("지영");
    }

    @Test
    void 같은_이메일은_저장할_수_없다() {
        repository.save(new Member(null, "same@example.com", "첫 번째"));

        assertThatThrownBy(() -> repository.save(
                new Member(null, "same@example.com", "두 번째")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

    @Test
    void 이름이_비어_있으면_저장할_수_없다() {
        assertThatThrownBy(() -> repository.save(
                new Member(null, "empty@example.com", "")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다.");
    }

    @Test
    void 없는_회원은_빈_Optional을_반환한다() {
        assertThat(repository.findById(999L)).isEmpty();
    }
}
