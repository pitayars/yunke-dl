package com.bjpowernode.service.impl;

import com.bjpowernode.manager.RedisManager;
import com.bjpowernode.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisManager redisManager;

    @Override
    public void setValue(String key, Object value) {
        redisManager.setValue(key, value);
    }

    @Override
    public Object getValue(String key) {
        return redisManager.getValue(key);
    }

    @Override
    public Boolean delete(String key) {
        return redisManager.delete(key);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisManager.expire(key, timeout, unit);
    }
}
