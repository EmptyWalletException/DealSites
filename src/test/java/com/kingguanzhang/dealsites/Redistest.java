package com.kingguanzhang.dealsites;

import com.kingguanzhang.dealsites.dao.ProductMapper;
import com.kingguanzhang.dealsites.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Redistest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object,Product> productRedisTemplate;

    @Test
    public void redisTest(){
        stringRedisTemplate.opsForValue().append("msg2","hello2");
    }

    @Test
    public void redisTest2(){
        Product product = new Product();
        product.setProductId(1);
        product.setPriority(2);
        product.setImgAddr("333");
        productRedisTemplate.opsForValue().set("product1",product);
    }
}
