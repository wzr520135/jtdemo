package com.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther wzr
 * @create 2019-11-14 20:47
 * @Description
 * @return
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    //校验用户数据的有效性,是否可用
    public boolean checkUser(String param, Integer type) {
        //String column = (type==1)?"username":(type==2?"phone":"email");
         Map<Integer,String> columnMap= new HashMap<>();
         columnMap.put(1, "username");
         columnMap.put(2, "phone");
         columnMap.put(3, "email");
         String colum=columnMap.get(type);
        System.out.println("culum:这是个啥"+colum);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq(colum, param);
          User userDB=userMapper.selectOne(queryWrapper);
           return  userDB==null?false:true;


    }
}
