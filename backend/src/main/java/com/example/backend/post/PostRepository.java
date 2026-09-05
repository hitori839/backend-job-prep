package com.example.backend.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);

    @Query("select p from Post p join fetch p.member")
    List<Post> findAllWithMember();
}