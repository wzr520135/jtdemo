package com.jt.service;

import com.jt.vo.EasyUIFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @auther wzr
 * @create 2019-11-01 14:53
 * @Description
 * @return
 */
@Service
@PropertySource("classpath:/properties/image.properties")
//@PropertySources({@PropertySource("classpath:/properties/image.properties\")}, +
//       "{@PropertySource("classpath:/properties/image.properties\")})
public class FileServiceImpl implements  FileService {

    /**
     *  1判断文件是否为图片jpg|png|gif...
     *  2防止恶意的程序   判断 高度/宽度
     *  3.图片分文件保存  yyyy/MM/dd
     *  4.修改文件名称 防止重名 (客户命名图片不受我们控制) UUID
     *  5实现文件上传
     *
     * */

    //动态的获取属性值 将数据信息写入properties文件中
            @Value("${image.localDir}")
  private  String localDir;//="F:/tedu4woekspace/jtdemo/images/";
   //定义一个根url地址
    @Value("${image.localDirUrl}")
  private   String localDirUrl;//="http://image.jt.com/";

    @Override
    public EasyUIFile fileUpload(MultipartFile uploadFile) {
         EasyUIFile easyUIFile=new EasyUIFile();

        //1判断文件是否为图片类型
        String fileName = uploadFile.getOriginalFilename();//获取图片名字
        /* if(filename.endsWith("jpg")){
              }else if(filename.endsWith("png")){
             }*/
            //正则表达式abc.jpg
        // .+ 任意字符至少要有一个字符 \\. 转义. ()分组
       fileName=fileName.toLowerCase();//字符串转小小写 防止系统版本不同大小写不同
        if(!fileName.matches("^.+\\.(jpg|png|gif)$")){
            //表示不满足规则
            return EasyUIFile.fail();
        }
        //2判断是否是恶意程序
        try {
            BufferedImage bufferedImage=
                    ImageIO.read(uploadFile.getInputStream());//获取输入流
             int width= bufferedImage.getWidth();
             int height=bufferedImage.getHeight();
             if(width==0|height==0){
                 return EasyUIFile.fail();
             }
          //3实现分文件存贮 按照yyyy/MM/dd/
               //准备文件路径
              String dateDir=new SimpleDateFormat("yyy/MM/dd/").format(new Date());
              //  文件存贮的路径
          String fileDirPath=localDir+dateDir;
            File dirFile = new File(fileDirPath);
              //如果没有目录则创建目录
            if(!dirFile.exists()){
                  dirFile.mkdirs();
              }
            //4生成文件的名称防止重名 name.type
             String fileType=
                     fileName.substring(fileName.lastIndexOf("."));
            String uuid= UUID.randomUUID().toString();
            //拼接文件名称
            String realFileName=uuid+fileType;
               //5实现文件上传
              uploadFile.transferTo(
                      new File(fileDirPath+realFileName));

             //暂时时候用网络地址代替真是的地址
            //定义url的虚拟地址
                 String url=localDirUrl+dateDir+realFileName;

              easyUIFile.setWidth(width)
                      .setHeight(height)
                      .setUrl(url);

        } catch (Exception e) {
            e.printStackTrace();
             return EasyUIFile.fail();
        }


        return easyUIFile;
    }
}
