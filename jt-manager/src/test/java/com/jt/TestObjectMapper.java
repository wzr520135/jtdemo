package com.jt;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.apache.catalina.mapper.Mapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-11-06 16:31
 * @Description
 * @return
 */

public class TestObjectMapper {

     private  static  final ObjectMapper MAPPER=new ObjectMapper();


    /*实现对象和json数据的转发*/
    @Test
     public  void object2Json() throws JsonProcessingException {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L)
                .setItemDesc("<html>")
                .setCreated(new Date())
                .setUpdated(itemDesc.getCreated());
        String s = MAPPER.writeValueAsString(itemDesc);
        System.out.println(s);

        //将Json还原成对象
        ItemDesc itemDesc1 = MAPPER.readValue(s, ItemDesc.class);
        System.out.println(itemDesc1);
    }

        /*实现list转换成js*/
     @Test
    public void list2Json() throws JsonProcessingException {
          List<ItemDesc> list= new ArrayList<>();
         ItemDesc itemDesc3 = new ItemDesc();
         itemDesc3.setItemId(1800L)
                 .setItemDesc("<html3>")
                 .setCreated(new Date())
                 .setUpdated(itemDesc3.getCreated());
         ItemDesc itemDesc4 = new ItemDesc();
         itemDesc4.setItemId(1900L)
                 .setItemDesc("<html4>")
                 .setCreated(new Date())
                 .setUpdated(itemDesc4.getCreated());

         list.add(itemDesc3);
         list.add(itemDesc4);


         String json = MAPPER.writeValueAsString(list);
         System.out.println(json);
         List itemList = MAPPER.readValue(json, list.getClass());
         System.out.println(itemList);

     }





}
