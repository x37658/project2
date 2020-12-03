package com.iii.eeit124.shopping.service;

import java.util.List;

import com.iii.eeit124.entity.Orders;

public interface SellingOrderListService {

	List<Orders> findAllOrdersByMemberId(Integer pageNo, Integer recordsPerPage, Integer id, Integer paidStatus,
			String orderStatus);

	Long getRecordCounts(Integer id, Integer paidStatus, String orderStatus);

	List<Orders> findAllOrdersByMemberId(Integer pageNo, Integer recordsPerPage, Integer id);

	Long getRecordCounts(Integer id);

	Integer updateOrderStatus(Integer orderItemId);

}