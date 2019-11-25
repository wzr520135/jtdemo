package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther wzr
 * @create 2019-11-14 20:45
 * @Description
 * @return
 */

@RestController
@RequestMapping("/user/")
public class UserContoller {

   @Autowired
    private UserService userService;
   @Autowired
   private JedisCluster jedisCluster;
    @RequestMapping("check/{param}/{type}")
    public JSONPObject checkUser(
           @PathVariable String param,
            @PathVariable Integer type,
            String callback
    ){

        //校验用户信息
        boolean result = userService.checkUser(param,type);
        SysResult sysResult=SysResult.succcess(result);
        return   new JSONPObject(callback, sysResult);
    }
//登录后回显
/**
 * 利用ticket检索数据
 * 实现思路
 * 1 动态获取用户信息ticket
 * 2查询redis缓存
 *     2.1 如果每有 数据 则返回201信息
 *     2.2 校验用户的IP是否正确
 *     2.3 ip校验通过则表示用户正藏登录
 * @param ticket
 * @param callback 动态获取用户信息
 * @return
 */

@RequestMapping("query/{ticket}")
@ResponseBody
public JSONPObject findUserByTicket(@PathVariable String ticket,
                                    HttpServletRequest request,
                                     HttpServletResponse response,
                                     String callback

){
     JSONPObject jsonpObject;
     //1判断ticket是否为NULL
    if(StringUtils.isEmpty(ticket)){
           jsonpObject= new JSONPObject(callback, SysResult.fail());
     return  jsonpObject;
    }
    //2判断当前key是否存在
    if(!jedisCluster.exists(ticket)){
        //r如果ticket不为null 但是redis中没有修改数据
        //cookie中的数据有误,应该删除该cookie
        CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");


        jsonpObject= new JSONPObject(callback, SysResult.fail());
        return  jsonpObject;
    }
    //3 表示ticker数据存在
    //校验ip地址是否为同一台服务登录
     String nowip= IPUtil.getIpAddr(request);
     String realIP=
             jedisCluster.hget(ticket, "JT_USER_IP");

         if(!nowip.equals(realIP)){

         return  new JSONPObject(callback, SysResult.fail());
              }


             //4获取用户信息
             String userJSON =jedisCluster.hget(ticket,"JT_USER");
                   jsonpObject=  new JSONPObject(callback, SysResult.succcess(userJSON));

             return   jsonpObject;

}





}
