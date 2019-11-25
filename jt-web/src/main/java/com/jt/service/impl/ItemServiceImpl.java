package com.jt.service.impl;

import com.jt.anno.Cachefind;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther wzr
 * @create 2019-11-14 11:37
 * @Description jt-web服务器
 * @return
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
     private HttpClientService httpClient;
    @Override
    //在jt-web service中发起http请求
    @Cachefind
    public Item findItemById(Long itemId) {
          //连接jt-manager中的服务
        String url="http://manage.jt.com/web/item/findItemById";
        Map<String,String> params=new HashMap<>();
        params.put("itemId", itemId+"");
        String itemJson = httpClient.doGet(url, params);

        return ObjectMapperUtil.toObject(itemJson, Item.class);
    }

    @Override
    @Cachefind
    public ItemDesc findItemDescById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemDescById";
        Map<String,String> params =
                new HashMap<String, String>();
        params.put("itemId", itemId+"");
        String itemDescJSON = httpClient.doGet(url,params);
        return ObjectMapperUtil.toObject(itemDescJSON,ItemDesc.class);


    }
}
