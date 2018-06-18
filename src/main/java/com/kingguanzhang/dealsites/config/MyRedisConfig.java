package com.kingguanzhang.dealsites.config;

import com.kingguanzhang.dealsites.pojo.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object,Product> productRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Product> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Product> ser = new Jackson2JsonRedisSerializer<Product>(Product.class);
        template.setDefaultSerializer(ser);
        return template;
    }


}
