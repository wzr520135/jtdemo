package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{
       int getRowCount();
       List<Item> findByPage( @Param("start") Integer start,
                              @Param("rows") Integer rows);
	
}
