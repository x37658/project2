package com.iii.eeit124.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iii.eeit124.entity.Orders;
import com.iii.eeit124.shopping.dao.OrderListDao;

@Service
public class OrderListServiceImpl implements OrderListService {

	@Autowired
	OrderListDao dao;
	
	@Override
	public List<Orders> findAllOrdersByMemberId(Integer id) {

		return dao.indAllOrdersByMemberId(id);
	}

}
