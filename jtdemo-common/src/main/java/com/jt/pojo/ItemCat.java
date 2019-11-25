package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther wzr
 * @create 2019-10-30 17:03
 * @Description
 * @return
 */
@Data
@TableName("tb_item_cat")
@Accessors(chain = true)
public class ItemCat extends  BasePojo {
   @TableId(type = IdType.AUTO)
    private  long id;
    private  long parentId;
    private String name;
    private Integer status;
    private  Integer sortOrder; //排序号
    private  Boolean isParent; //是否为父级

}
