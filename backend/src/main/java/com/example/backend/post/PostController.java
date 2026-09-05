package com.example.backend.post;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@RequestBody CreatePostRequest request) {
        Post post = postService.create(
                request.memberId(),
                request.title(),
                request.content());
        return PostResponse.from(post);
    }

    @GetMapping
    public List<PostResponse> findAll(
            @RequestParam(defaultValue = "false") boolean withMember) {
        List<Post> posts = withMember ? postService.findAllWithMember() : postService.findAll();
        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }

    @GetMapping("/member/{memberId}")
    public List<PostResponse> findByMemberId(@PathVariable Long memberId) {
        return postService.findByMemberId(memberId).stream()
                .map(PostResponse::from)
                .toList();
    }

    public record CreatePostRequest(
            Long memberId,
            String title,
            String content) {
    }
}