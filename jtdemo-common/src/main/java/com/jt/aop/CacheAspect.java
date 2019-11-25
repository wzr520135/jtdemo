package com.jt.aop;

import com.jt.anno.Cachefind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

/**
 * @auther wzr
 * @create 2019-11-07 11:25
 * @Description
 * @return
 */
@Component//表示将我们这个类交给容器管理
@Aspect//表示切面
public class CacheAspect {
    //@Autowired 单台
    //private  Jedis jedis;
    //@Autowired //分片
    //private ShardedJedis jedis;

   /* @Autowired //哨兵
    private  Jedis jedis;*/
   @Autowired (required = false)//redis集群
   //说明:当tomcat服务器启动时,会启动spring容器 要求立即注入对象
   //如果项目中有可能不需要该对象时,可以配置required = false
   private JedisCluster jedis;

   // private ShardedJedis shardedJedis;
   // @Around("@annotation(com.jt.anno.Cachefind)")
    @Around("@annotation(cachefind)")
    public Object around(ProceedingJoinPoint joinPoint
    , Cachefind cachefind){
        System.out.println("成功进入AOP切面");
      String key =getKey(joinPoint,cachefind);
       Object obj=null;
      //1 先查询缓存数据g
           String result=jedis.get(key);

        try {
             if(StringUtils.isEmpty(result)){
                obj = joinPoint.proceed();
                 // 将数据保存到redis 中
                 String json = ObjectMapperUtil.toJSON(obj);
                   //判断用户是否传递超时时间
                 if(cachefind.seconds()==0) {
                          jedis.set(key, json);
                 } else {
                      int seconds=cachefind.seconds();
                      jedis.setex(key, seconds, json);
                 }

             } else {
                 Class reslutType =getType(joinPoint);
                 obj = ObjectMapperUtil.toObject(result, reslutType);
                 System.out.println("执行aop缓存");


             }
        } catch (Throwable e) {
               e.printStackTrace();
               throw  new RuntimeException(e);
        }


        return obj;
    }
    /**
     * 目的:获取方法的返回值类型
     * 提示:利用方法对象获取返回值类型
     * @param joinPoint
     * @return
     */
    private Class getType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature =
                (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
        /*
         * Object[] objs = joinPoint.getArgs(); Class[] argsClass = new
         * Class[objs.length]; for (int i=0; i<objs.length;i++) { argsClass[i] =
         * objs[i].getClass(); }
         *
         * joinPoint.getTarget().getClass().getMethod(name, parameterTypes);
         */

    }

    //判断用户是否传递参数  如果用户传参数使用自己的key
    //固若每有就指定参数 使用动态生成
    private String getKey(ProceedingJoinPoint joinPoint, Cachefind cachefind) {
          String key=cachefind.key();
          //获取当前方法的名称 类名 方法名
        //获取目标方法的工具api(参数 方法名 返回值类型)
        String className = joinPoint.getSignature().getDeclaringTypeName();

        String methondName = joinPoint.getSignature().getName();

        if(!StringUtils.isEmpty(key)){
              //以用户的数据为准
        return  className+"."+methondName+"::"+key;

          }else{
              //类名.方法名::第一个参数值
            Object arg0 = joinPoint.getArgs()[0];
            return  className+"."+methondName+"::"+arg0;

        }

    }


}
