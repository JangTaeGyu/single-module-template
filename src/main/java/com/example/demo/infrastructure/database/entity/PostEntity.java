package com.example.demo.infrastructure.database.entity;

import com.example.demo.domain.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class PostEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String body;

    private LocalDateTime deletedAt;

    public PostEntity(Post post) {
        this.slug = post.getSlug();
        this.title = post.getTitle();
        this.body = post.getBody();
    }

    public Post toDomain() {
        return new Post(id, slug, title, body, createdAt, updatedAt);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
