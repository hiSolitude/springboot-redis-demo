# springboot集成redis

## spring-redis-demo1

> parent版本为：
>
> ```xml
> <parent>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-parent</artifactId>
>     <version>1.5.15.RELEASE</version>
>     <relativePath/> <!-- lookup parent from repository -->
> </parent>
> <dependencies>
>     <!-- redis 配置 -->
> 	<dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-data-redis</artifactId>
>      </dependency>
> </dependencies>
> ```

单节点部署redis，直连 6379 端口，使用 redis.pool

## spring-redis-demo2

> parent版本为：
>
> ```xml
> <parent>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-parent</artifactId>
>     <version>2.1.0.RELEASE</version>
>     <relativePath/> <!-- lookup parent from repository -->
> </parent>
> <dependencies>
>     <!-- redis 配置 -->
> 	<dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-data-redis</artifactId>
>      </dependency>
> </dependencies>
> ```

单节点部署redis，直连 6379 端口，使用 spring.redis.lettuce.pool；

springboot 1.0版本和 2.0版本 redis-starter 所使用的连接池不同。

#  spring-redis-demo3

引入 parent版本与  spring-redis-demo2 相同

连接两台redis，使用redis不同的database，使用端口 6379 ，使用 spring.redis.lettuce.pool；需要手动来配置 redis 属性

# spring-redis-demo4

引入 parent版本与 spring-redis-demo2 相同

使用redis哨兵模式，连接同一台机器的哨兵，不同的数据库，使用端口 26379，使用 spring.redis.lettuce.pool；需要手动配置 redis 属性

> ```java
> lettuceConnectionFactory.setDatabase(3);
> ```