package com.example.backend.post;

public record PostResponse(
        Long id,
        String title,
        String content,
        MemberResponse member
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                MemberResponse.from(post.getMember())
        );
    }

    public record MemberResponse(
            Long id,
            String name,
            String email
    ) {
        public static MemberResponse from(com.example.backend.member.Member member) {
            return new MemberResponse(
                    member.getId(),
                    member.getName(),
                    member.getEmail()
            );
        }
    }
}