package com.main19.server.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    public void setValues(String name, String age) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(name, age, Duration.ofMinutes(1)); // 1분뒤 메모리에서 삭제
    }

    public String getValues(String name){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(name);
    }
}
