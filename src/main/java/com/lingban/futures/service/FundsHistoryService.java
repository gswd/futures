package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.FundsHistory;

public interface FundsHistoryService{
	
	/**
	 * 查询某一段时间的资金盈亏情况
	 * @param orderType 排序方式  ASC 小->大  DESC 大->小
	 * @return
	 */
	public List<FundsHistory> getFundsHistoryByPeriod(DateQueryParam dateParam, String orderType) throws Exception;

	public List<FundsHistory> getFundsHistoryByPeriodAndCode(DateQueryParam dateParam, String futuresCode) throws Exception;
	
}
