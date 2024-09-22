package com.example.demo.api;

import com.example.demo.api.request.CreatePostRequest;
import com.example.demo.api.response.ApiResponse;
import com.example.demo.domain.post.Post;
import com.example.demo.domain.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getPostList() {
        List<Post> posts = postService.getPostList();

        return ApiResponse.successful(posts);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest request) {
        postService.createPost(request.toDomain());

        return ApiResponse.successful(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);

        return ApiResponse.successful(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ApiResponse.successful(HttpStatus.NO_CONTENT);
    }
}
