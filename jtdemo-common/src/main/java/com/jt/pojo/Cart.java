package com.jt.pojo;

/**
 * @auther wzr
 * @create 2019-11-18 15:23
 * @Description
 * @return
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@TableName("tb_cart")
@Accessors(chain = true)
public class Cart extends BasePojo implements Serializable {
    @TableId(type = IdType.AUTO)
    private  Long id;
    private  Long userId;
    private  Long itemId;
    private  String itemTitle;
    private  String itemImage;
    private  String itemPrice;
    private  Integer num;
}
