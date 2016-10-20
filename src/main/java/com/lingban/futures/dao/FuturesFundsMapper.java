package com.lingban.futures.dao;

import java.util.List;

import com.lingban.futures.common.PageParam;
import com.lingban.futures.model.FuturesFunds;

import tk.mybatis.mapper.common.Mapper;

public interface FuturesFundsMapper extends Mapper<FuturesFunds>{
	
	List<FuturesFunds> getFuturesFunds(PageParam pageParam);
}
