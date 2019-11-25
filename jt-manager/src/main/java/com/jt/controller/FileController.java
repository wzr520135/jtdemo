package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUIFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @auther wzr
 * @create 2019-11-01 14:13
 * @Description
 * @return
 */

@RestController
public class FileController {

       @Autowired
       private FileService fileService;
    /* 1 准备文件存储目录
    * 2 获取文件名称
    * 3 利用工具API实现文件上传
    * */



    @RequestMapping("/file")
    public  String file(MultipartFile fileImage) throws IOException {

        File fileDir=new File("F:/tedu4woekspace/jtdemo/images/");
          if(!fileDir.exists()){
              fileDir.mkdirs();//没有目录则先创建 且是带子级目录的文件

          }
          //获取图片名称
        String filename = fileImage.getOriginalFilename();
            String path="F:/tedu4woekspace/jtdemo/images/"+filename;
            //文件实现上传
            fileImage.transferTo(new File(path));
        return  "文件上传成功";
    }

    /*实现商品的文件上传*/
    @RequestMapping("/pic/upload")
    public EasyUIFile fileUpload(MultipartFile uploadFile){


        return      fileService.fileUpload(uploadFile);




    }



}
