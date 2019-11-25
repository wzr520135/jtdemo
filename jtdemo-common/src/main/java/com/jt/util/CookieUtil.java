package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther wzr
 * @create 2019-11-18 10:31
 * @Description 获取cookie
 * @return
 */

public class CookieUtil {
    //1 获取Cookie对象
    public  static Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
   if(cookies!=null&&cookies.length>0) {//校验cookie是否有效
       for (Cookie cookie : cookies) {
           if (cookieName.equals(cookie.getName())) {
               return cookie;
           }
       }
   }
         return  null;
    }

 /*删除cookie*/
    public static void  deleteCookie(HttpServletResponse response,
            String cookieName, int maxAge,
                                     String domain,
                                     String path){
        // 规则:定义一个与原来cookie名称配置一致的cookie之后再删除
        Cookie jtCookie=new Cookie(cookieName, "");
        jtCookie.setMaxAge(maxAge);//表示立即删除
        jtCookie.setDomain(domain);//设置cookie共享
        jtCookie.setPath(path);
        response.addCookie(jtCookie);


    }

}
