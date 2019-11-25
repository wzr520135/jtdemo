package com.jt.service;

import com.jt.vo.EasyUIFile;
import org.springframework.web.multipart.MultipartFile;

/**
*@auther wzr
*@create 2019-11-01 14:53
*@Description
*@return
*/

public interface FileService {
    EasyUIFile fileUpload(MultipartFile uploadFile);
}
