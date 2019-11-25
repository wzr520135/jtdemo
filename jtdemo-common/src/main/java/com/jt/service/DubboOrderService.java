package com.jt.service;

import com.jt.pojo.Order;

/**
*@auther wzr
*@create 2019-11-19 14:42
*@Description
*@return
*/

public interface DubboOrderService {
    String insertOrder(Order order);

    Order findOrderById(String id);
}
