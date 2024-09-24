package com.example.demo.infrastructure.cache;

import com.example.demo.domain.CustomCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CustomCacheManagerImpl implements CustomCacheManager {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveData(String key, Object data) {
        redisTemplate.opsForValue().set(key, data);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }

    @Override
    public <T> Optional<T> getData(String key, Class<T> type) {
        Object data = redisTemplate.opsForValue().get(key);
        if (type.isInstance(data)) {
            return Optional.of(type.cast(data));
        }

        return Optional.empty();
    }

    @Override
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
