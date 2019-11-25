package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @auther wzr
 * @create 2019-11-07 9:13
 * @Description 实现spring容器管理redis配置类
 * @return
 */
@Configuration //表示配置类
@PropertySource("classpath:/properties/redis.properties")
public class Redisonfig {
 /*   //单台
 @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;

    @Bean
    @Scope("prototype")//设置为多例  当用户使用时创建
    public Jedis jedis() {
        return new Jedis(host, port);
    }
*/
/************************************************************/

   /*  //分片
   @Value("${redis.nodes}")
    private String nodes;
    @Bean
    @Scope("prototype")
    public  ShardedJedis shardedJedis(){
        List<JedisShardInfo> shards=new ArrayList<>();
        String[] redisNodes = nodes.split(",");
        for (String redisNode:redisNodes){
            //redisNode=IP:PORT
            String[] hostAndPort = redisNode.split(":");
              String host= hostAndPort[0];
              int port =Integer.valueOf(hostAndPort[1]);//转换成 整型
            //int port =  Integer.parseInt(hostAndPort[1]);
               JedisShardInfo info = new JedisShardInfo(host,port);

                shards.add(info);
        }

        return  new ShardedJedis(shards);

    }*/
   /*public  void test01(){
        String host="192.168.198.130";
        List<JedisShardInfo> shards=new ArrayList<>();
        shards.add(new JedisShardInfo(host,6379));
        shards.add(new JedisShardInfo(host,6380));
        shards.add(new JedisShardInfo(host,6381));
        ShardedJedis jedis= new ShardedJedis(shards);
        jedis.set("1907", "学习redis分片机制");
        System.out.println(jedis.get("1907"));
    }*/

    /****************************************************************/
    /*哨兵*/
 /*  @Value("${redis.sentinels}")
    private  String sentinels;

  *//* @Autowired
    private  JedisSentinelPool sentinelPool;*//*

   //规则:可以为bean的方法自动的注入参数对象
   @Bean
   @Scope("prototype")//多列
   //public  Jedis jedis02(@Qualifier("JedisSentinelPool") JedisSentinelPool sentinelPool)
   public  Jedis jedis02( JedisSentinelPool sentinelPool){
       return sentinelPool.getResource();

   }
  // @Bean("JedisSentinelPool")
   @Bean//定义哨兵的池对象  单例模式
    public JedisSentinelPool jedisSentinelPool() {
        Set<String> set=new HashSet<>();
        set.add(sentinels);

       return new JedisSentinelPool("mymaster", set);


    }

    */
//*******************************************************
    /*redis 集群 */
    @Value("${redis.nodes}")
    private String redisNodes;

    @Bean
    @Scope("prototype")
    public JedisCluster jedisCluster(@Qualifier("redisSet") Set<HostAndPort> redisSet) {
        /*Set<HostAndPort> set = new HashSet<>();
        String[] redisNodes2 = redisNodes.split(",");
        for (String redisNode : redisNodes2) {
            String[] nodes = redisNode.split(":");
            String host = nodes[0];
            int port = Integer.parseInt(nodes[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
        }
        return new JedisCluster(set);*/
       return  new JedisCluster( redisSet);

    }
    // 将set集合交给容器管理
    @Bean("redisSet")
    public Set<HostAndPort> redisSet() {

        Set<HostAndPort> redisSet = new HashSet<>();
        String[] redisNodes2 = redisNodes.split(",");
        for (String redisNode : redisNodes2) {
            String[] nodes = redisNode.split(":");
            String host = nodes[0];
            int port = Integer.parseInt(nodes[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            redisSet.add(hostAndPort);
        }
        return redisSet;

    }
}