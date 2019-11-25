package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @auther wzr
 * @create 2019-10-31 14:49
 * @Description 该类是系统级别的vo对象
 * @return
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {

    private  Integer status;//状态码 200表示成功 201 表示失败
    private  String  msg;  //提示信息
    private  Object  data; //返回的数据

       public static SysResult  succcess (){
           return new SysResult(200,null,null);

       }

      public static SysResult  succcess (Object data){
        return new SysResult(200,null,data);

       }

    public static SysResult  succcess ( String msg,Object data){
        return new SysResult(200,msg,data);

    }

    public static  SysResult fail(){

           return new SysResult(201,"业务执行失败",null);
    }

}
