package com.main19.server.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


//@Service
//@RequiredArgsConstructor
//public class RedisService {
//    private final RedisTemplate redisTemplate;
//
//    public void setValues(String email, String age) {
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//    }
//
//    public String getValues(String email){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        return values.get(email);
//    }
//}
