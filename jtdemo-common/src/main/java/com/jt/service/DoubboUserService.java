package com.jt.service;

import com.jt.pojo.User;

/**
*@auther wzr
*@create 2019-11-15 18:12
*@Description 定义dubbo的UserService接口
*@return
*/

public interface DoubboUserService {

    void insertUser(User user);

    String findUserByUP(User user, String ip);
}
