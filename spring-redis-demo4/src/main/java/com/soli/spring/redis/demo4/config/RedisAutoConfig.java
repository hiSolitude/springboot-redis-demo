package com.soli.spring.redis.demo4.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisAutoConfig
 *
 * @author yufei.zhao()
 * @date 2019/4/2
 */
@Configuration
public class RedisAutoConfig {

    @Bean(name = "defaultLettuceConnectionFactory")
    @Primary
    public LettuceConnectionFactory defaultLettuceConnectionFactory(@Qualifier("defaultSentinelConfiguration") RedisSentinelConfiguration defaultSentinelConfiguration,@Qualifier("defaultLettucePoolingClientConfiguration")LettucePoolingClientConfiguration defaultLettucePoolingClientConfiguration){

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory( defaultSentinelConfiguration,defaultLettucePoolingClientConfiguration);
        lettuceConnectionFactory.setDatabase(3);
        return lettuceConnectionFactory;
    }

    @Bean(name = "localLettuceConnectionFactory")
    public LettuceConnectionFactory localLettuceConnectionFactory(@Qualifier("localSentinelConfiguration") RedisSentinelConfiguration localSentinelConfiguration,@Qualifier("localLettucePoolingClientConfiguration")LettucePoolingClientConfiguration localLettucePoolingClientConfiguration){

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(localSentinelConfiguration,localLettucePoolingClientConfiguration);
        lettuceConnectionFactory.setDatabase(7);
        return lettuceConnectionFactory;
    }

    @Bean("defaultRedisTemplate")
    @Primary
    public StringRedisTemplate defaultRedisTemplate(@Qualifier("defaultLettuceConnectionFactory") LettuceConnectionFactory defaultLettuceConnectionFactory){

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);

        return stringRedisTemplate;
    }

    @Bean("localRedisTemplate")
    public StringRedisTemplate localRedisTemplate(@Qualifier("localLettuceConnectionFactory") LettuceConnectionFactory localLettuceConnectionFactory){

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(localLettuceConnectionFactory);

        return stringRedisTemplate;
    }
}
