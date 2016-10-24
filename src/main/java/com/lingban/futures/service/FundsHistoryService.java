package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.FundsHistory;

public interface FundsHistoryService{
	
	/**
	 * 通过一段时间查询这段时间所有品种的资金情况
	 * @return
	 */
	public List<FundsHistory> getFundsHistoryByPeriod(DateQueryParam dateParam, String orderType);
	
}
