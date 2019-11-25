package com.jt.util;

import com.jt.pojo.User;

/**
 * @auther wzr
 * @create 2019-11-19 11:34
 * @Description  如果存储多个数据 用map存储
 * @return
 */

public class UserThreadLocal {

     private  static  ThreadLocal<User> thread=new ThreadLocal<>();

     public   static  void setUser(User user){
         thread.set(user);
     }
     public static  User getUser(){

         return thread.get();
     }
     //防止内存溢出,必须添加移除方法
    public  static  void remove(){
         thread.remove();
    }

}
