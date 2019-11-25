package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

/**
*@auther wzr
*@create 2019-11-14 11:36
*@Description
*@return
*/

public interface ItemService {

    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);
}
