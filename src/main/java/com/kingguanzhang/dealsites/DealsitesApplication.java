package com.kingguanzhang.dealsites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DealsitesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealsitesApplication.class, args);
    }
}
