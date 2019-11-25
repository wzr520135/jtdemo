package com.jt.controller.web;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther wzr
 * @create 2019-11-14 19:57
 * @Description
 * @return
 */
@RestController
public class JSONPController {
    //约定回调函数的名称callback
/*    @RequestMapping("/web/testJSONP")
    public  String testJSonp(String callback){
        ItemDesc itemDesc = new ItemDesc();
         itemDesc.setItemId(10001L)
                 .setItemDesc("商品详情信息~~~");
          String json= ObjectMapperUtil.toJSON(itemDesc);

        return  callback+"("+json +")";
    }*/

 //利用API实现jsonp跨域访问


    @RequestMapping("/web/testJSONP")
    public JSONPObject testJSonp(String callback){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(10001L)
                .setItemDesc("商品详情信息~~~");
        return  new JSONPObject(callback, itemDesc);
    }

}
