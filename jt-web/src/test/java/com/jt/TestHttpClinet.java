package com.jt;

import com.jt.util.HttpClientService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther wzr
 * @create 2019-11-12 10:27
 * @Description
 * @return
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClinet {



    /**1实例化httpCliient对象
     * 2准备url请求地址 http://www.baidu.com
     * 3封装请求方式对象  get/post/put
     * 4发起http请求获取服务器响应
     * 5 判断返回值状态吗信息200
     * 6 从响应对象中获取服务器返回数据
     * */
    @Test
    public  void testGet() throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        String url="http://www.baidu.com";
        HttpGet get= new HttpGet(url);
        CloseableHttpResponse response=client.execute(get);
        if(response.getStatusLine().getStatusCode()==200){
            //表示请求服务请求正确
            HttpEntity entity = response.getEntity();//返回值实体对象
               String result=
                       EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        }


    }
    @Autowired
    private HttpClientService httpClient;
   @Test
     public  void  doGet(){
       String url="http://manager.jt.com/web/item/findItemById?itemId=1473166932";
       Map<String,String> params=new HashMap<>();
       params.put("itemId","1473166932");
       String json = httpClient.doGet(url, params);
       System.out.println(json);


   }



}
