package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther wzr
 * @create 2019-11-11 18:31
 * @Description
 * @return
 */
@Configuration//表示配置类
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;
    //开启匹配后缀型配置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

         configurer.setUseSuffixPatternMatch(true);


    }

    /*添加用户的拦截器
     *
    *   url:localhost:8090/adduser/a/b (/*拦截不到)
    *  /**表是拦截所有的请求(多级)
    *  /* 表示拦截一级目录请求
    *
    */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/cart/**","/order/**");

    }
}

