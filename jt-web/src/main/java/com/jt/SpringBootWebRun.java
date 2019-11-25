package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//告知springboot程序 启动是不要加载数据源配置exclude=DataSourceAutoConfiguration.class
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBootWebRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootWebRun.class,args);
	}
}
