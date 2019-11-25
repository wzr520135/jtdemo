package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @auther wzr
 * @create 2019-11-08 15:46
 * @Description 哨兵入门案列
  * @return
 */

public class TestSentinle {

    /**  masterName ;mymaster
     *   sentinels:哨兵的set集合
     *   端口说明:
     *    默认端口:6379
     *    通信端口: 16379 ping-pong
     *    哨兵端口: 26379
     * */
    @Test
      public void test01(){
          Set<String> sentinels=new HashSet<>();
            sentinels.add("192.168.198.130:26379");
          JedisSentinelPool pool =
                  new JedisSentinelPool("mymaster",sentinels);
          Jedis jedis = pool.getResource();
                 jedis.set("1907","redis哨兵测试成功");
          System.out.println(jedis.get("1907"));

      }



}
