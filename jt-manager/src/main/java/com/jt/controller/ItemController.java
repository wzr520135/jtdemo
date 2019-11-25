package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("query")
	public EasyUITable queryAll(@Param("page") Integer page,
								@Param("rows") Integer rows){
		    
	return   itemService.findByPage(page,rows);
	
	}

	/*实现商品的新增*/
	@RequestMapping("save")
	public SysResult saveItem(Item item, ItemDesc itemDesc){

		/*try {
			itemService.saveItem(item);
			return  SysResult.succcess();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}*/
		itemService.saveItem(item,itemDesc);

		return  SysResult.succcess();

	}
	@RequestMapping("update")
	public  SysResult updateItem(Item item,ItemDesc itemDesc){

		     itemService.updateItem(item,itemDesc);
		     return  SysResult.succcess();


	}

	/*商品的下架
	* URL:instock
	* type:post
	* params = {"ids":ids};
	* */
	@RequestMapping("instock")
	public  SysResult instockItem(Long[] ids) {

		int status = 2;//表示下架
		itemService.updateStatus(ids, status);
		return SysResult.succcess();
	}
	/*上架*/
	@RequestMapping("reshelf")
	public  SysResult reshelfItem(Long[] ids){

		int status=1;//表示下架
		itemService.updateStatus(ids,status);
		return SysResult.succcess();

	}

/*删除
* url:delete
* params = {"ids":ids};
 */
@RequestMapping("delete")
  public SysResult deleteItem(Long[] ids){
  	     itemService.deleteByids(ids);


  	return  SysResult.succcess();
  }

	/*根据id查询商品的详细信息
	*
	* */
  @RequestMapping("query/item/desc/{itemId}")
	public  SysResult  findeItemDescById(@PathVariable Long itemId){

       ItemDesc   desc=itemService.findItemDescById(itemId);
            return SysResult.succcess(desc);
	}
	
	
	
	
	
	
}
