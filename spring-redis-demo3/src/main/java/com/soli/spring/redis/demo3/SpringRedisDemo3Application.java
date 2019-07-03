package com.soli.spring.redis.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
public class SpringRedisDemo3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisDemo3Application.class, args);
    }

    public SpringRedisDemo3Application(RedisTemplate<String,String> localRedisTemplate,RedisTemplate<String,String> defaultRedisTemplate) throws InterruptedException {

        //10s的有效时间
        localRedisTemplate.delete("key");

        localRedisTemplate.opsForValue().set("key","value",100,TimeUnit.MICROSECONDS);

        String ans = localRedisTemplate.opsForValue().get("key");

        System.out.println("value".equals(ans));

        TimeUnit.MICROSECONDS.sleep(200);

        ans = localRedisTemplate.opsForValue().get("key");

        System.out.println("value".equals(ans) + " >> false ans should be null! ans=[" + ans + "]");

        defaultRedisTemplate.opsForValue().set("key", "value", 100, TimeUnit.MILLISECONDS);

        ans = defaultRedisTemplate.opsForValue().get("key");

        System.out.println(ans);
    }
}
