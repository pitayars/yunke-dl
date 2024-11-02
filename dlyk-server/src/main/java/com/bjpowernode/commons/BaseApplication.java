package com.bjpowernode.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication implements CommandLineRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final Map<String, Object> cacheMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        //对key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //由4行固定的代码，设置value采用json序列化
        //设置value的序列化方式使用json的序列化方式（默认情况下是jdk序列化） （在项目开发中者4行代码写一次就行了，也是固定的）
        ObjectMapper objectMapper = new ObjectMapper(); //对象映射工具类， 可以实现 java对象 <----> json对象 的相互转化
        //设置对象里面的字段的可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);// 可见度; 能见度; 能见距离; 可见性; 明显性;
        //设置多态行为的
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);
        //创建jackson序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(objectMapper, Object.class);

        //对值的序列化(采用的是json序列化)
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
