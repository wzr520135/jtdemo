package com.jt.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
    @Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	public EasyUITable findByPage(Integer page,Integer rows) {
		int start=page -1;
		List<Item> item = itemMapper.findByPage(start,rows);
		int rowCount = itemMapper.getRowCount();
		EasyUITable  easyUITable=new EasyUITable();
		    easyUITable.setRows(item);
		    easyUITable.setTotal(rowCount);
		return easyUITable;
	}
/*实现两张表同事入库*/
	@Override
	public void saveItem(Item item , ItemDesc itemDesc) {
		item.setStatus(1)
				.setCreated(new Date())
				.setUpdated(item.getCreated());
		int rows= itemMapper.insert(item);
		//System.out.println(rows);
		   //利用Mybatis-plus入库之后 会自动将主键Id进行回显
		    itemDesc.setItemId(item.getId())
					.setCreated(item.getCreated())
					.setUpdated(item.getCreated());
		//System.out.println(itemDesc);
		int i = itemDescMapper.insert(itemDesc);
		//System.out.println(i);

	}

	@Override
	public void updateItem(Item item ,ItemDesc itemDesc) {
		item.setUpdated(new Date());

		int rows= itemMapper.updateById(item);
		  itemDesc.setItemId(item.getId())
				  .setUpdated(item.getUpdated());
		     itemDescMapper.updateById(itemDesc);
		System.out.println(rows);
	}

/*将ids中的所有的数据状态status改为2*/
	@Override
	public void updateStatus(Long[] ids, int status) {
       		//1
		/*for (Long id:ids) {
			Item item=new Item();
			item.setId(id)
					.setStatus(status)
					.setUpdated(new Date());
		itemMapper.updateById(item);
		}*/

		//2
		Item item=new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper=new UpdateWrapper<>();
		List idList= Arrays.asList(ids);
		  updateWrapper.in("id",idList);
		itemMapper.update(item,updateWrapper);

	}
	@Override
    @Transactional
	public void deleteByids(Long[] ids) {

            List idlist=Arrays.asList(ids);
		itemMapper.deleteBatchIds(idlist);

		int i = itemDescMapper.deleteBatchIds(idlist);
		System.out.println("成功"+i);

	}


	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return  itemDescMapper.selectById(itemId);

	}

	@Override
	public Item findItemById(Long itemId) {
		System.out.println("2222222");
		return  itemMapper.selectById(itemId);
	}

	}

