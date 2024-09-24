package com.example.demo.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    private static final String CACHE_VALUE = "posts";

    @JsonIgnore
    private String cacheKey;

    private Long id;
    private String title;
    private String body;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public Post(String title, String body) {
        Assert.notNull(title, "title not null");
        Assert.notNull(body, "body not null");
        this.title = title;
        this.body = body;
    }

    public Post(Long id, String title, String body, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cacheKey = Post.generateCacheKey(id);
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static String generateCacheKey(Long id) {
        return String.format("%s:%s", CACHE_VALUE, id);
    }
}
