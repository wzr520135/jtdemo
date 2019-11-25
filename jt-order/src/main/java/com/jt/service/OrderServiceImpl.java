package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jt.vo.SysResult;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;



@Service
public class OrderServiceImpl implements DubboOrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;

	@Override
	@Transactional //事务控制
	public String insertOrder(Order order) {

		//订单号:登录用户id+当前时间戳
		 String orderId=""+order.getUserId()+System.currentTimeMillis();
		Date  date= new Date();

		//1入库order对象
		order.setOrderId(orderId)
				.setStatus(1)
				.setCreated(date)
				.setUpdated(date);
		orderMapper.insert(order);
		//2入库订单物流
		OrderShipping shipping = order.getOrderShipping();
		     shipping.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
		     orderShippingMapper.insert(shipping);
		System.out.println("订单物流入库成功~!");

		//3实现订单商品入库
		List<OrderItem> orderItems=order.getOrderItems();
		for (OrderItem orderItem: orderItems
			 ) {orderItem.setOrderId(orderId)
				.setCreated(date)
				.setUpdated(date);
			 orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功");



		return orderId;
	}

	/** 查询3张表数据 实现数据封装
	 *
	 * */
	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
		  queryWrapper.eq("order_id",id);
		List<OrderItem> orderItems =
				orderItemMapper.selectList(queryWrapper);


		return order.setOrderShipping(orderShipping)
				.setOrderItems(orderItems);
	}
}
