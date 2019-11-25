package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther wzr
 * @create 2019-11-19 10:11
 * @Description 拦截器 用户登录以后才可以访问购物车
 * @return
 */
@Component//表示交给Spring容器管理
public class UserInterceptor  implements HandlerInterceptor {
    /*boolen true 放行
    *        false 拦截  必须配合重定向使用
    * 业务思路:
    *    如何判断用户是否登录?
    * 步骤:
    *   1 .动态获取cookie中的 JT_TICKET中的值
    *   2.获取用户ip地址,校验数据
    *   3.查询redis服务器是否有数据.获取userjson的数据
    *
    *
    *
    * */
    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
                  //1获取cookie数据
        Cookie cookie= CookieUtil.getCookie(request, "JT_TICKET");
           if(cookie!=null){
               String ticket =cookie.getValue();
               if(!StringUtils.isEmpty(ticket)){
                    if(jedisCluster.exists(ticket)) {
                        //2校验ip
                        String nowIP = IPUtil.getIpAddr(request);
                        String realIP = jedisCluster.hget(ticket, "JT_USER_IP");
                        if (nowIP.equals(realIP)) {
                            String userJSON=jedisCluster.hget(ticket, "JT_USER");
                            User user = ObjectMapperUtil.toObject(userJSON, User.class);
                            request.setAttribute("JT_USER", user);
                            //利用treadlocal动态获取用户数据
                            UserThreadLocal.setUser(user);
                            System.out.println(user.getId());


                            return true;//表示放行
                        }
                    }
               }
           }

       response.sendRedirect("/user/login.html");
        return  false;///表示拦截
    }

/** 在拦截器的最后一步 ,实现数据清空
 * */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
          UserThreadLocal.remove();
    }
}