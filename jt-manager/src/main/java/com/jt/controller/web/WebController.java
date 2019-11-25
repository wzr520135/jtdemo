package com.jt.controller.web;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @auther wzr
 * @create 2019-11-12 10:55
 * @Description 表示给前台传数据
 * @return
 */
@RestController
@RequestMapping("/web/item/")
public class WebController {
@Autowired
 private ItemService  itemService;

    @RequestMapping("findItemById")

    public Item findItemById(Long itemId){
        System.out.println(11111);
        Item item = itemService.findItemById(itemId);

        return   item;
    }

    //查询商品详情信息
    @RequestMapping("/findItemDescById")
    public ItemDesc findItemDescById(Long itemId) {

        return itemService.findItemDescById(itemId);
    }


}
