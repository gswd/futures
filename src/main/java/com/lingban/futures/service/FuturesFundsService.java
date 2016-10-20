package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.common.PageParam;
import com.lingban.futures.common.Paging;
import com.lingban.futures.model.FuturesFunds;

public interface FuturesFundsService extends BaseService<FuturesFunds>{
	
	public Paging<FuturesFunds> selectFuturesFundsPage(PageParam pageParam);
	
	public List<String> testCache(String id);
	
	public String testCacheEvict(String id);
}
