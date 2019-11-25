package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;


/**
 * @auther wzr
 * @create 2019-11-15 18:17
 * @Description dubbo接口的实现类
 * @return
 */
@Service
public class DoubboUserSerciceImpl implements DoubboUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 1.避免后台数据库报错,暂时使用手机代替
     * 2.使用MD5加密算法  32位16进制字符串=2^128次
     * 					(2^4)^32=2^128
     * 3.设定操作时间
     * 注意事项:
     * 	1.注册时使用加密算法
     *  2.用户登录时,加密算法必须相同
     */
    @Override
    public void insertUser(User user) {
           String md5Pass=
                   DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
          user.setEmail(user.getPhone())
                  .setPassword(md5Pass)
                  .setCreated(new Date())
                  .setUpdated(user.getCreated());
        userMapper.insert(user);
    }
     //实现登录
    /** 1明确返回值数据 返回加密后的秘钥
     * 2校验用户信息是否有效 如果无效则返回null
     * 3如果用户密码正确  则将用户信息保存到redis中
     *
     *
     * */
    @Override
    public String findUserByUP(User user, String ip) {

          // 1将密码进行加密
        String md5Pass=
                DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

           user.setPassword(md5Pass);
           //2根据用户信息查询数据库
        // 根据对象中不为空的属性当做where条件
        QueryWrapper<User> queryWrapper=new QueryWrapper<>(user);

        User userDB=userMapper.selectOne(queryWrapper);
         if(userDB==null){
             return  null;
         }
         //如果不为空 则用户名和密码正确 继续执行下面代码
        //3动态生成ticket
        String uuid= UUID.randomUUID().toString();
        String ticket=DigestUtils.md5DigestAsHex(uuid.getBytes());
         //4j将user对象转化成json
             //为了数据安全性 一般会将重要数据进行脱敏处理
         userDB.setPassword("123456你信不信");
        String userJSON = ObjectMapperUtil.toJSON(userDB);
        ///6 防止用户重复登录 需要将之前的登录信息删删除
        if(jedisCluster.exists("JT_USERNAME_"+userDB.getUsername())){
            String oldTicket=jedisCluster.get("JT_USERNAME_"+userDB.getUsername());
            System.out.println("旧的ticket"+oldTicket);
            jedisCluster.del(oldTicket);
        }

        //5将我们的数据保存到redis中
                jedisCluster.hset(ticket, "JT_USER", userJSON);
                jedisCluster.hset(ticket, "JT_USER_IP", ip);
                jedisCluster.expire(ticket, 7*24*3600);
         //实现用户名与ticker绑定
         jedisCluster.setex("JT_USERNAME_"+userDB.getUsername(), 7*24*3600,ticket);

        return ticket;
    }
}
