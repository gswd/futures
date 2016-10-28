package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.model.Futures;

public interface FuturesInfoService{

	List<Futures> selectAll() throws Exception;
	
}
