package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther wzr
 * @create 2019-11-05 9:49
 * @Description
 * @return
 */
@RestController
public class GetPortController {
    @Value("${server.port}")
    private  String port;
    @RequestMapping("/getPort")
    public  String getPort(){

        return "端口号是:"+port;


    }



}
