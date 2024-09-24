package com.example.demo.domain;

import java.util.Optional;

public interface CustomCacheManager {
    void saveData(String key, Object data);

    <T> Optional<T> getData(String key, Class<T> type);

    void deleteData(String key);

    boolean hasKey(String key);
}
