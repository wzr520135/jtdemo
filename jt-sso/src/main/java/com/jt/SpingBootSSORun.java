package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther wzr
 * @create 2019-11-14 20:36
 * @Description
 * @return
 */
@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpingBootSSORun {
    public static void main(String[] args) {
        SpringApplication.run(SpingBootSSORun.class);
    }

}
