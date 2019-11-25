package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.Cachefind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITable;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-10-30 17:10
 * @Description
 *  @Resource (name="itemCatMapperB")
 *  跟@Autowired 用法差不多 (等同于@Qualifier和@Autowired)
 * @return
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    //@Qualifier(value = "itemCatMapperB")
    //指定一个id为itemCatMapperB 的对象自动注入 (配合Autowired使用)
     private ItemCatMapper itemCatMapper;
//    @Autowired
//    private Jedis jedis;

   // @Autowired
    private ShardedJedis jedis;

    @Override
    public ItemCat findItemCatById(Long itemCatId) {

           return  itemCatMapper.selectById(itemCatId);
    }

    /*1根据parentId查询数据库纪录
    * 2循环遍历数据之后封装EasyUITree的list集合
    * */
    @Override
    @Cachefind()
    public List<EasyUITree> findItemCatByParentId(Long parentId) {
        //1查询数据
        List<ItemCat> itemCatList=findItemCatListByParentId(parentId);
         //实现数据封装
        List<EasyUITree> treeList =
                new ArrayList<EasyUITree>(itemCatList.size());

        //循环遍历ItemCat 加入到 List<EasyUITree> 中
        for(ItemCat itemCat:itemCatList ){

            Long id=itemCat.getId();
            String text=itemCat.getName();
            //判断如果是父级closed 否则 open 三目运算符
            String state=itemCat.getIsParent()?"closed":"open";
            EasyUITree tree=new EasyUITree(id,text,state);
            treeList.add(tree);
        }
         return treeList;
    }

    private List<ItemCat> findItemCatListByParentId(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<ItemCat> itemCatList=
                itemCatMapper.selectList(queryWrapper);
        return itemCatList;
    }

    /**
     *  1先查询缓存 确定key的写法
     *  2 如果缓存中没有数据 则说明用户第一此查询
     *    先查询数据库 需要将数据源转换成json保存到redis中
     *   3 如果缓存中有数据 则说明用户不的第一 查询  就直接从
     *   redis中获取数据 需要将json转化为对象
     * */

    @Override
    public List<EasyUITree> findItemCatCache(Long parentId) {
        List<EasyUITree> treeList=new ArrayList<>();
              String key="ITEMCAT::"+parentId;
              String json=jedis.get(key);
               //判断
               if(StringUtils.isEmpty(json)){
                   //从数据库查寻数据
                   treeList = findItemCatByParentId(parentId);
                    //数据转换成json
                   String itemCatJson= ObjectMapperUtil.toJSON(treeList);
                      //将json存到redis中
                      jedis.set(key, itemCatJson);
                   System.out.println("用户第一此查询数据");
               }else {
                   //说明用户不的第一此查询
                   treeList=
                           ObjectMapperUtil.toObject(json, treeList.getClass());
                   System.out.println("用户查询缓存");

               }

        return treeList;
    }

}
