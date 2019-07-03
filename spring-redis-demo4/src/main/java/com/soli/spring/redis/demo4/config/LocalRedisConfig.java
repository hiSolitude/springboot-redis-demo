package com.soli.spring.redis.demo4.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LocalRedisConfig
 *
 * @author yufei.zhao()
 * @date 2019/4/2
 */
@Configuration
@ConditionalOnProperty(name = "nodes" ,prefix = "spring.local-redis.sentinel")
public class LocalRedisConfig {

    private static Logger logger = LoggerFactory.getLogger(LocalRedisConfig.class);


    @Value("${spring.local-redis.sentinel.nodes}")
    private List<String> nodes;
    @Value("${spring.local-redis.sentinel.master}")
    private String masterName;

    @Value("${spring.local-redis.lettuce.pool.max-active:8}")
    private Integer maxActive;
    @Value("${spring.local-redis.lettuce.pool.max-idle:8}")
    private Integer maxIdle;
    @Value("${spring.local-redis.lettuce.pool.max-wait:-1}")
    private Long maxWait;
    @Value("${spring.local-redis.lettuce.pool.min-idle:0}")
    private Integer minIdle;


    /**
     * 配置连接池
     * @return
     */
    @Bean(name = "localLettucePoolingClientConfiguration")
    public LettucePoolingClientConfiguration localLettucePoolingClientConfiguration(){
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


    @Bean(name = "localSentinelConfiguration")
    public RedisSentinelConfiguration localSentinelConfiguration(){
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
        //redisSentinelConfiguration.setDatabase(7);
        return redisSentinelConfiguration;
    }


}
