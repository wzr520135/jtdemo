package com.jt.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;

import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther wzr
 * @create 2019-10-30 17:11
 * @Description
 * @return
 */
@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {

    @Autowired
    private  ItemCatService itemCatService;

    @RequestMapping("queryItemName")
    public  String findItemCatNameById(Long itemCatId){
             //1先根据ID传对象
        ItemCat itemCat =itemCatService.findItemCatById(itemCatId);
             //2 将对象中的name名称获取
        String name = itemCat.getName();

        return name;
    }
/*获取商品分类列表信息
* URL:list
* 返回值 EasyUITree
@RequestParam 获取参数 实现数据转化
* 1  value/name 接受参数名称
* 2 defaultValue 默认是
* 3 required 是否必须传递 默认值是true
 */
@RequestMapping("list")
 public List<EasyUITree> findItemCatByParentId(
         @RequestParam(value = "id",defaultValue = "0") Long parentId){
     //1先查询一级商品分类信息
//    if(id==null) {
//        id=0L;
//    }
//        Long parentId=id;
   return   itemCatService.findItemCatByParentId(parentId);

         //先查询缓存,如果缓存中没有数据.则查询数据库
      //return  itemCatService.findItemCatCache(parentId);

 }



}
