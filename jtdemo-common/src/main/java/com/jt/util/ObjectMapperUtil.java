package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @auther wzr
 * @create 2019-11-06 17:03
 * @Description 需要将我的Json串与对象实现互转
 *              json与lisr实现互转
 * @return
 */

public class ObjectMapperUtil {

    private  static  final ObjectMapper MAPPER =new ObjectMapper();

    //将对象转化成json串
     public  static  String toJSON(Object obj){
               String json=null;
        try {
             json=MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
          return  json;

    }
    //将json串转换成对象

    public static <T> T toObject(String json,Class<T> taegetClass){
             T t=null;
        try {
            t = MAPPER.readValue(json, taegetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
          return  t;
    }

}
