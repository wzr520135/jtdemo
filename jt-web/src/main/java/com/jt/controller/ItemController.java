package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther wzr
 * @create 2019-11-12 9:18
 * @Description /WEB服务器
 * @return
 */
@Controller//需要跳转页面.
@RequestMapping("/items/")
public class ItemController {

    @Autowired
    private ItemService itemService;
    //${item.title }
    @RequestMapping("{itemId}")
    public  String toItems(@PathVariable Long itemId , Model model){

        //根据商品id号 查询后台的商品数据
        Item item=itemService.findItemById(itemId);
         model.addAttribute("item",item);
        ItemDesc itemDesc=itemService.findItemDescById(itemId);
        model.addAttribute("itemDesc",itemDesc);
       //System.out.println("当前商品id值");
        return   "item"; //动态展示页面
    }



}
