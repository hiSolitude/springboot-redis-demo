package com.soli.spring.redis.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootApplication
public class SpringRedisDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisDemo2Application.class, args);
    }

    public SpringRedisDemo2Application(RedisTemplate<String,String> redisTemplate){
        redisTemplate.opsForValue().set("hello","world");
        String ans = redisTemplate.opsForValue().get("hello");
        Assert.isTrue("world".equals(ans),"value 不匹配");
    }
}
