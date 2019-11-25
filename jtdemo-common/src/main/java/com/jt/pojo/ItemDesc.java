package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther wzr
 * @create 2019-11-01 9:39
 * @Description
 * @return
 */
@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
public class ItemDesc  extends  BasePojo{
    @TableId()//只标识主键 不设定关系
    private  Long itemId;
    private  String itemDesc;
}
