package com.example.demo.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll();
    Optional<Post> findById(Long postId);
    void save(Post post);
    void delete(Long postId);
}
