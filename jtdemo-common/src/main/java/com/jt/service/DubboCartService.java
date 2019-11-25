package com.jt.service;


import com.jt.pojo.Cart;

import java.util.List;

/**
*@auther wzr
*@create 2019-11-18 15:31
*@Description
*@return
*/
public interface DubboCartService {

    List<Cart> findCartListByUserId(Long userId);

    void updateCartNum(Cart cart);


    void deleteCart(Cart cart);

    void insertCart(Cart cart);
}
