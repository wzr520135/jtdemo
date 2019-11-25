package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

/**
*@auther wzr
*@create 2019-10-30 17:10
*@Description
*@return
*/

public interface ItemCatService {
    ItemCat findItemCatById(Long itemCatId);

    List<EasyUITree> findItemCatByParentId(Long parentId);

    List<EasyUITree> findItemCatCache(Long parentId);
}
