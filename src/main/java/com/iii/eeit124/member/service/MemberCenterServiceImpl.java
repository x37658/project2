package com.iii.eeit124.member.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iii.eeit124.entity.Members;
import com.iii.eeit124.member.dao.MemberCenterDao;

@Service
public class MemberCenterServiceImpl implements MemberCenterService{
	@Autowired
	MemberCenterDao dao;
	public Members getMemberById(Integer memberId) {
		return dao.getMemberById(memberId);
	}
	@Override
	public Map<String, BigDecimal> getCostHistory(Integer memberId) {
		
		return dao.getCostHistory(memberId);
	}
	@Override
	public Map<String, Object> getSellingHistory(Integer memberId) {
		return dao.getSellingHistory(memberId);
	}
	@Override
	public Map<String, List<Object>> getSellingCountByDate(Integer memberId) {
		return dao.getSellingCountByDate(memberId);
	}
	@Override
	public Map<String, Object> getSellingHistory(Integer memberId, Date start, Date last) {
		return dao.getSellingHistory(memberId, start, last);
	}
	@Override
	public Map<String, List<Object>> getSellingCountByDate(Integer memberId, Date start, Date last) {
		return dao.getSellingCountByDate(memberId, start, last);
	}
	
	@Transactional
	public Members update(Members members) {
		return dao.update(members);
	}
	
	
}
