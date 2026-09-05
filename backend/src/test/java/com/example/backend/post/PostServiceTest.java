package com.example.backend.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.backend.member.Member;
import com.example.backend.member.MemberJpaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원이_작성한_게시글을_등록한다() {
        Member member = memberJpaRepository.save(
                new Member("writer@example.com", "작성자"));

        Post post = postService.create(
                member.getId(), "첫 게시글", "게시글 내용");

        assertThat(post.getId()).isNotNull();
        assertThat(post.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void 없는_회원은_게시글을_작성할_수_없다() {
        assertThatThrownBy(() -> postService.create(
                999999L, "제목", "내용"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원을 찾을 수 없습니다.");
    }

    @Test
    void 제목이_비어_있으면_게시글을_작성할_수_없다() {
        Member member = memberJpaRepository.save(
                new Member("title@example.com", "제목 테스트"));

        assertThatThrownBy(() -> postService.create(
                member.getId(), "", "내용"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 필수입니다.");
    }
}