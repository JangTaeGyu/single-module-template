package com.example.demo.infrastructure.database;

import com.example.demo.infrastructure.database.entity.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends Repository<PostEntity, Long> {
    @Query(value = "SELECT p FROM PostEntity p WHERE p.deletedAt IS NULL")
    List<PostEntity> findAll();

    PostEntity save(PostEntity postEntity);

    @Query(value = "SELECT p FROM PostEntity p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<PostEntity> findById(Long id);
}
