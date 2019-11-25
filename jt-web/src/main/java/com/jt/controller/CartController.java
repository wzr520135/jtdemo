package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-11-18 15:51
 * @Description
 * @return
 */

@Controller
@RequestMapping("/cart/")
public class CartController {
    @Reference(check = false)
    private DubboCartService cartService;
    @RequestMapping("show")
    public  String show(Model model ){
        //获取当前用户信息 userID 动态获取用户信息
        /*  User user =(User) request.getAttribute("JT_USER");
             Long userId=user.getId();*/
          //Long userId=7L;
        User user = UserThreadLocal.getUser();
        Long userId = user.getId();
        List<Cart> cartList=
                cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);

        return  "cart";
        }

@RequestMapping("update/num/{itemId}/{num}")
@ResponseBody
      public SysResult updateNum(Cart cart){
    User user = UserThreadLocal.getUser();
    Long userId = user.getId();
       // Long userId=7L;
        cart.setUserId(userId);
        cartService.updateCartNum(cart);
        return  SysResult.succcess();

      }

      @RequestMapping("delete/{itemId}")
      public String deleteCart(Cart cart){
          User user = UserThreadLocal.getUser();
          Long userId = user.getId();
       // Long userId=7L;
         cart.setUserId(userId);
          cartService.deleteCart(cart);

        return  "redirect:/cart/show.html";
      }

@RequestMapping("add/{itemId}")
   public  String insertCart(Cart cart){
    User user = UserThreadLocal.getUser();
    Long userId = user.getId();
    System.out.println("用户id:"+userId);
      //  Long userId=7L;
        cart.setUserId(userId);
         cartService.insertCart(cart);

         return "redirect:/cart/show.html";

   }

    }

