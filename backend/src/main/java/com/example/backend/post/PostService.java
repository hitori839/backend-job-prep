package com.example.backend.post;

import java.util.List;

import com.example.backend.member.Member;
import com.example.backend.member.MemberJpaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberJpaRepository memberJpaRepository;

    public PostService(PostRepository postRepository,
                       MemberJpaRepository memberJpaRepository) {
        this.postRepository = postRepository;
        this.memberJpaRepository = memberJpaRepository;
    }

    public Post create(Long memberId, String title, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "회원을 찾을 수 없습니다."));

        Post post = new Post(title, content, member);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> findAllWithMember() {
        return postRepository.findAllWithMember();
    }

    @Transactional(readOnly = true)
    public List<Post> findByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }
}