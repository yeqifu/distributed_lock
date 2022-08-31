package com.yeqifu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 落亦-
 * @Date: 2022/8/30 19:28
 */
@SpringBootApplication
@MapperScan("com.yeqifu.mapper")
public class DistributedLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }
}
