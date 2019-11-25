package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DoubboUserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther wzr
 * @create 2019-11-14 20:15
 * @Description
 * @return
 */
@Controller
@RequestMapping("/user/")

public class UserController {

    @Reference(check = false)
    private DoubboUserService userService;
    @Autowired
    private JedisCluster jedisCluster;

 @RequestMapping("{moduleName}")
 public  String  modelName(@PathVariable String moduleName){

   return moduleName;

 }

 //注册
 @RequestMapping("doRegister")
 @ResponseBody
  public SysResult doRegister(User user){

       userService.insertUser(user);
   return  SysResult.succcess();
  }
  //实现用户单点登录
    /** 1 动态获取IP地址
     * 2 将ticket信息发送到Cookie中
     * Cookie用法 ticketCookie.setMaxAge(大于0);存活时间单位秒
     * ticketCookie.setMaxAge(0);立即删除
     * ticketCookie.setMaxAge(-1);表示会话关闭时删除
     * 2.关于path说明
     * 	 url:www.jd.com/login.html
     * 	 ticketCookie.setPath("/");
     * 	 ticketCookie.setPath("/abc"); url无法访问该cookie
     * */
    @RequestMapping("doLogin")
    @ResponseBody
    public  SysResult doLogin(User user,
                              HttpServletRequest request,
                              HttpServletResponse response){
       //1 动态获取IP地址
        String ip = IPUtil.getIpAddr(request);
        //2获取校验的结果
           String ticket= userService.findUserByUP(user,ip);//?
             if(StringUtils.isEmpty(ticket)){
                 //为空 表示用户名和密码错误
                 return  SysResult.fail();
             }
           //3数据保存到cookie中
        Cookie  ticketCookie= new Cookie("JT_TICKET", ticket);
              ticketCookie.setMaxAge(7*24*3600);
              ticketCookie.setPath("/"); //表示根目录有效
        //由于单点登录,需要将cookie信息设置为共享数据
              ticketCookie.setDomain("jt.com");
              response.addCookie(ticketCookie);


        return  SysResult.succcess();
    }

/** 实现用户登录操作
 * 业务:
 * 1删除redis中的数据 ticket
 * 2删除cookie "JT_TICKET"
 *
 * 实现思路:
 * 1 先获取"JT_TICKET "中的值
 * 2 删除redis数据
 * 3删除cookie记录
 *bug:一般不会出现,但是在特定的业务环境下出现异常
 * */
    @RequestMapping("logout")
public String logOut(HttpServletRequest request ,HttpServletResponse response ){
        Cookie cookie =
                CookieUtil.getCookie(request, "JT_TICKET");
   if(cookie==null){
       //重定向到系统首页
       return "redirect:/";
   }
   //2删除redis中的数据
         String ticket=cookie.getValue();
         jedisCluster.del(ticket);
   //3删除cookie
       /* // 规则:定义一个与原来cookie名称配置一致的cookie之后再删除
        Cookie jtCookie=new Cookie("JT_TICKET", "");
         jtCookie.setMaxAge(0);//表示立即删除
         jtCookie.setDomain("jt.com");//设置cookie共享
        jtCookie.setPath("/");
         response.addCookie(jtCookie);*/
        //利用工具API删除cookie
        CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");
        //重定向到系统首页
    return "redirect:/";
}


}
