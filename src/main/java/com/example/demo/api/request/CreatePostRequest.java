package com.example.demo.api.request;

import com.example.demo.domain.post.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String body;

    public Post toDomain() {
        return new Post(title, body);
    }
}
