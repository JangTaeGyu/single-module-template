package com.example.demo.domain.post;

import com.example.demo.support.error.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new DomainException("Post not found"));
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }
}
