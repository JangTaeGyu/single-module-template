package com.example.demo.infrastructure.database;

import com.example.demo.domain.post.Post;
import com.example.demo.domain.post.PostRepository;
import com.example.demo.infrastructure.database.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JpaPostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .map(PostEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId).map(PostEntity::toDomain);
    }

    @Transactional
    @Override
    public void save(Post post) {
        PostEntity postEntity = new PostEntity(post);
        postRepository.save(postEntity);
    }

    @Transactional
    @Override
    public void delete(Long postId) {
        Optional<PostEntity> result = postRepository.findById(postId);
        if (result.isPresent()) {
            PostEntity postEntity = result.get();
            postEntity.delete();
        }
    }
}
