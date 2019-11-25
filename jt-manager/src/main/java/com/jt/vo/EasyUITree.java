package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @auther wzr
 * @create 2019-10-31 10:45
 * @Description
 * @return
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {

    private  Long id; //节点id
    private  String text; //文本信息
    //private List<?> children;
    private  String state;//状态信息



}
