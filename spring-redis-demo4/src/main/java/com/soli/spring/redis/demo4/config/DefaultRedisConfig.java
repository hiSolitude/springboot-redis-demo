package com.soli.spring.redis.demo4.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DefaultRedisConfig
 *
 * @author yufei.zhao()
 * @date 2019/4/2
 */
@Configuration
@ConfigurationProperties
public class DefaultRedisConfig {
    private static Logger logger = LoggerFactory.getLogger(DefaultRedisConfig.class);

    @Value("${spring.redis.sentinel.nodes}")
    private List<String> nodes;
    @Value("${spring.redis.sentinel.master}")
    private String masterName;

    @Value("${spring.redis.lettuce.pool.max-active:8}")
    private Integer maxActive;
    @Value("${spring.redis.lettuce.pool.max-idle:8}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait:-1}")
    private Long maxWait;
    @Value("${spring.redis.lettuce.pool.min-idle:0}")
    private Integer minIdle;

    /**
     * 配置连接池
     * @return
     */
    @Bean(name = "defaultLettucePoolingClientConfiguration")
    public LettucePoolingClientConfiguration defaultLettucePoolingClientConfiguration(){
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(10);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        genericObjectPoolConfig.setTestOnBorrow(true);
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(60));
        builder.shutdownTimeout(Duration.ofMillis(100));

        return builder.build();
    }


    @Bean(name = "defaultSentinelConfiguration")
    @Primary
    public RedisSentinelConfiguration defaultSentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置master的名称
        redisSentinelConfiguration.master(masterName);
        //配置redis 的哨兵 snetinel
        Set<RedisNode> redisNodeSet = new HashSet<>();
        nodes.forEach(x -> {
            redisNodeSet.add(new RedisNode(x.split(":")[0],Integer.parseInt(x.split(":")[1])));
        });

        logger.info("redisNodeSet --> "+redisNodeSet);
        redisSentinelConfiguration.setSentinels(redisNodeSet);
        //redisSentinelConfiguration.setDatabase(3);
        return redisSentinelConfiguration;
    }

}
