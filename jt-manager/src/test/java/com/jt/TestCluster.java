package com.jt;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @auther wzr
 * @create 2019-11-11 14:08
 * @Description
 * @return
 */

public class TestCluster {

@Test
      public  void  test01(){
          Set<HostAndPort> nodes= new HashSet<>();
            nodes.add(new HostAndPort("192.168.198.130",7000));
            nodes.add(new HostAndPort("192.168.198.130",7001));
            nodes.add(new HostAndPort("192.168.198.130",7002));
            nodes.add(new HostAndPort("192.168.198.130",7003));
            nodes.add(new HostAndPort("192.168.198.130",7004));
            nodes.add(new HostAndPort("192.168.198.130",7005));


          JedisCluster jedisCluster=new JedisCluster(nodes);
            jedisCluster.set("cluster", "redis集群搭建完成");
          System.out.println(jedisCluster.get("cluster"));

      }

}
