package com.jt;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @auther wzr
 * @create 2019-11-06 14:10
 * @Description
 * @return
 */

public class TestRedis {

    @Test
    public void testString() throws InterruptedException {
        String host="192.168.198.130";
        int port=6379;
        Jedis jedis=new Jedis(host,port);
        jedis.set("1907","redis的入门案列~~~~~~~~~~~~");
        System.out.println(jedis.get("1907"));
        //2批量操作
        jedis.mset("a","a1","b","b1");
        System.out.println(jedis.mget("a", "b"));
     //3清空
        String s = jedis.flushDB();
        System.out.println(s);

      //4 自增
        jedis.set("num","1");
        Long result = jedis.incr("num");
        System.out.println(result);
        System.out.println("自减的结果是"+jedis.decr("num"));

        //5设定超时时间
        jedis.expire("num",30);
        //Thread.sleep(2000);
        System.out.println(jedis.ttl("mum"));


        //6撤销超时时间
        jedis.persist("num");
        System.out.println(jedis.ttl("mum"));


    }

      private Jedis jedis;
    @Before
     public void init(){
         String host="192.168.198.130";
         int port=6379;
          jedis=new Jedis(host,port);

     }
     /*需求redis中已经存在改该key 不允许修改*/
     @Test
  public  void testString2(){

            //   jedis.set("abc","aaa");
        // System.out.println(jedis.get("a"));
           if(!jedis.exists("abc")){
               jedis.set("abc","aaaaa");
               System.out.println(jedis.get("abc"));
           }  else{
               System.out.println("已存在key");
           }
        //利用redusApi实现上述功能
         Long result = jedis.setnx("abc", "bbbbb");
         System.out.println("获取返回值的结果"+result);


     }

    /** 1为数据添加超时时间 原子性操作
    *   2 set数据不能修改 同时设定超时时间
     */

    public  void  testString3(){
         jedis.setex("time",10,"asdf");
         //jedis.psetex("time",10000,"asdf");
         //NX不允许修改 XX可以修改 EX 秒 PX毫秒
        // jedis.set("1907","设定redis的","NX","EX",10);

    }

/*测试hash类型*/
    @Test
     public  void  testHash(){

         jedis.hset("user","id","1001");
         jedis.hset("user","name","刘老师");
         jedis.hset("user","age","20");
         jedis.hset("user","sex","男");
         System.out.println("值是:"+jedis.hvals("user"));
         System.out.println("key是:"+jedis.hkeys("user"));
        System.out.println(jedis.hgetAll("user"));

    }

    /*list*/
    @Test
    public  void testLipst(){
         jedis.lpush("list","1,2,3,4,5");
        System.out.println(jedis.rpop("list"));
        jedis.lpush("list1","1","2","3","4","5");
        System.out.println(jedis.rpop("list1"));


    }

}
