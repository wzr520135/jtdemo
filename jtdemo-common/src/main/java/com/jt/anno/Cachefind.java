package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*@auther wzr
*@create 2019-11-07 10:44
*@Description 自定义一个切面注解
*@return
*/
@Target(ElementType.METHOD)//对方法生效
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface Cachefind {
     String value() default "cache";
     //1key可以动态获取.  类名.方法名::第一个参数的值
    //2key也可以自己指定.
    String key() default "";
    //设定超时时间
     int seconds() default 0;//用户数据不超时

}
