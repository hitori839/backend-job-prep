package com.example.backend.post;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.backend.member.Member;
import com.example.backend.member.MemberJpaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원의_게시글을_조회한다() {
        Member member = memberJpaRepository.save(
                new Member("post@example.com", "게시글 회원"));

        postRepository.save(new Post("제목", "내용", member));

        List<Post> posts = postRepository.findByMemberId(member.getId());

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목");
    }

    @Test
    void fetch_join으로_게시글과_회원을_함께_조회한다() {
        Member member = memberJpaRepository.save(
                new Member("fetch@example.com", "Fetch 회원"));

        postRepository.save(new Post("Fetch 제목", "Fetch 내용", member));

        List<Post> posts = postRepository.findAllWithMember();

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getMember().getEmail())
                .isEqualTo("fetch@example.com");
    }
}