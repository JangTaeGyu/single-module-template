package com.example.demo.domain.post;

import com.example.demo.domain.CustomCacheManager;
import com.example.demo.support.error.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CustomCacheManager customCacheManager;

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return customCacheManager.getData(Post.generateCacheKey(postId), Post.class)
                .orElseGet(() -> {
                    Post post = postRepository.findById(postId).orElseThrow(() -> new DomainException("Post not found"));
                    customCacheManager.saveData(Post.generateCacheKey(postId), post);
                    return post;
                });
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
        customCacheManager.deleteData(Post.generateCacheKey(postId));
    }
}
