package com.jt.service;

import com.jt.pojo.Item;

import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {
     EasyUITable findByPage(Integer page,
                                  Integer rows);

      void  saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item,ItemDesc itemDesc);

    void updateStatus(Long[] ids, int status);

    void deleteByids(Long[] ids);

    ItemDesc findItemDescById(Long itemId);

    Item findItemById(Long itemId);


}
