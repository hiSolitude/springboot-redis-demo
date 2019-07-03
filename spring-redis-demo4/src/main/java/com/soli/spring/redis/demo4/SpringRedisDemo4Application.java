package com.soli.spring.redis.demo4;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class SpringRedisDemo4Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisDemo4Application.class, args);
    }


    public SpringRedisDemo4Application(@Qualifier("localRedisTemplate") RedisTemplate<String,String> localRedisTemplate, @Qualifier("defaultRedisTemplate") RedisTemplate<String,String> defaultRedisTemplate){

        System.out.println(defaultRedisTemplate.opsForValue().get("hello"));

        System.out.println(localRedisTemplate.opsForValue().get("wwh"));

        //3库
        defaultRedisTemplate.opsForValue().set("zyf","9325");
        //7库
        localRedisTemplate.opsForValue().set("wwh","81720");



    }

}
