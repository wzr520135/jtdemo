package com.jt.exe;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther wzr
 * @create 2019-10-31 15:37
 * @Description
 * @return
 */
 //@ResponseBody
//@ControllerAdvice
@RestControllerAdvice//异常通知 对Controller层生效  加上返回JSon数据
@Slf4j
public class SysExecution {
    //当系统中出现运行时异常时生效
    //区分 系统正常异常和跨域异常
    //说明:跨域访问时用户一定会添加callback参数
    @ExceptionHandler({RuntimeException.class,})//表示异常处理器
    public Object error(Exception exception, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return SysResult.fail();
        } else {
            //用户是跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
             return  new JSONPObject(callback, SysResult.fail());
        }
    }

}



