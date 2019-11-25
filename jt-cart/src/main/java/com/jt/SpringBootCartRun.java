package com.jt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;



/**
 * @auther wzr
 * @create 2019-11-18 17:30
 * @Description
 * @return
 */

@SpringBootConfiguration
@MapperScan("com.jt.mapper")
@EnableAutoConfiguration
public class SpringBootCartRun {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCartRun.class,args);

    }
}
