package com.lingban.futures.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lingban.futures.common.PageParam;
import com.lingban.futures.common.Paging;
import com.lingban.futures.dao.FuturesFundsMapper;
import com.lingban.futures.model.FuturesFunds;


@Transactional
@Service("futuresFundsService")
public class FuturesFundsServiceImpl extends AbstractBaseService<FuturesFunds> implements FuturesFundsService{

	@Autowired
	private FuturesFundsMapper futuresFundsMapper;
	
	@Override
	public Paging<FuturesFunds> selectFuturesFundsPage(PageParam pageParam) {
		
		PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
		List<FuturesFunds> futuresFundsList = futuresFundsMapper.getFuturesFunds(pageParam);
		
		return new Paging<>(futuresFundsList);
	}
	
	@Override
	@Cacheable(value="redisCache1")
	public List<String> testCache(String id) {
		System.out.println("testCache");
		List<String> l = new ArrayList<>();
		l.add("a");
		l.add("b");
		l.add("h");
		l.add("e");
		return l;
	}

	@Override
	@CacheEvict()
	public String testCacheEvict(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
