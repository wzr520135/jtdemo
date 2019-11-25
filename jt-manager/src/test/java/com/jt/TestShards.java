package com.jt;

import org.junit.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-11-07 15:47
 * @Description
 * @return
 */

public class TestShards {
    /* 测试redis分片
    *  面试了解redis的分片原理?了解hash一致性规则?
    * */
    @Test
    public  void test01(){
        String host="192.168.198.130";
        List<JedisShardInfo>  shards=new ArrayList<>();
         shards.add(new JedisShardInfo(host,6379));
         shards.add(new JedisShardInfo(host,6380));
         shards.add(new JedisShardInfo(host,6381));

        ShardedJedis jedis= new ShardedJedis(shards);

                 jedis.set("1907", "学习redis分片机制");
        System.out.println(jedis.get("1907"));



    }
}
