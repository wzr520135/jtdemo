package com.jt.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-11-19 14:05
 * @Description 订单
 * @return
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    /**
     *  订单确认页,需要获取购物车纪录
     *  通过userid
     *  页面获取数据${carts}
    * */
      @Reference(check = false)
       private DubboCartService cartService;

      @Reference(check = false)
      private DubboOrderService dubboOrderService;

    @RequestMapping("create")
    public  String Create(Model model){
        Long userId= UserThreadLocal.getUser().getId();
        List<Cart> cartList =cartService.findCartListByUserId(userId);
          model.addAttribute("carts",cartList);


        return "order-cart";
    }
/*实现订单的提交*/
      @RequestMapping("submit")
      @ResponseBody
    public SysResult insertOrder(Order order){
           Long userId=UserThreadLocal.getUser().getId();
            order.setUserId(userId);
      String orderId= dubboOrderService.insertOrder(order);
      if(StringUtils.isEmpty(orderId)){
          return SysResult.fail();
      }
      return  SysResult.succcess(orderId);


    }
    /** 根据orderid查询数据
     * */
  @RequestMapping("success")
    public  String findOrderById(String id,Model model){
      System.out.println("此 id 是:"+id);
      Order order=dubboOrderService.findOrderById(id);
        model.addAttribute("order",order);


     return    "success";
    }

}
