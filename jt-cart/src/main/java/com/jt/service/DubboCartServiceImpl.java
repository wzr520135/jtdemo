package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-11-18 17:48
 * @Description
 * @return
 */
@Service
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired
    private CartMapper cartMapper;


    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
             queryWrapper.eq("user_id", userId);

        return  cartMapper.selectList(queryWrapper);
    }

    @Override
    public void updateCartNum(Cart cart) {
        Cart cartTemp =new Cart();
        cartTemp.setNum(cart.getNum())
                .setUpdated(new Date());
        UpdateWrapper<Cart> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id", cart.getUserId())
                .eq("item_id", cart.getItemId());

        System.out.println("这个返回值是什么:"+updateWrapper);
       cartMapper.update(cartTemp,updateWrapper);

    }

    @Override
    public void deleteCart(Cart cart) {
         cartMapper.delete(new QueryWrapper<Cart>(cart));
    }

    @Override
    /*新增 购物车
    *  1 根据userId和itemId查询数据库
    *  有数据;数量的更新
    * 无数据 新增入库
    * */
    public void insertCart(Cart cart) {
     QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("user_id",cart.getUserId())
               .eq("item_id",cart.getItemId());

        Cart cartDB = cartMapper.selectOne(queryWrapper);

         if(cartDB==null){
             cart.setCreated(new Date()).setUpdated(cart.getCreated());
                     cartMapper.insert(cart);
         } else {
              //数量更新 update tb_cart set num=#{num} ,update=#{updated}
              //where id=#{id}
             int  num =cart.getNum()+cartDB.getNum();
            /* cartDB.setNum(num)
                     .setUpdated(new Date());
             cartMapper.updateById(cartDB);*/
             Cart cartTemp=new Cart();
             cartTemp.setId(cartDB.getId())
                     .setNum(num)
                     .setUpdated(new Date());
             cartMapper.updateById(cartTemp);

         }

    }
}
