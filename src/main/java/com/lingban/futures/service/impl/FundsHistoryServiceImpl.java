package com.lingban.futures.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.FundsHistory;
import com.lingban.futures.service.FundsHistoryService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("fundsHistoryService")
public class FundsHistoryServiceImpl extends AbstractBaseService<FundsHistory> implements FundsHistoryService {

	@Override
	public List<FundsHistory> getFundsHistoryByPeriod(DateQueryParam dateParam, String orderType) {
		Example example = new Example(FundsHistory.class);
		Criteria criteria = example.createCriteria();
		
		if(dateParam.getStartDate() != null){
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if(dateParam.getEndDate() != null){
			criteria.andLessThanOrEqualTo("createTime", dateParam.getEndDate());
		}

		example.orderBy("code").asc().orderBy("createTime").asc();
		List<FundsHistory> fundsHistoryList = mapper.selectByExample(example);
		
		Map<String, List<FundsHistory>> fundsHistoryMap = fundsHistoryList.stream().collect(Collectors.groupingBy(FundsHistory :: getCode));
		
		List<FundsHistory> fundsHistoryResult = new ArrayList<>();
		
		fundsHistoryMap.forEach((k, v) -> {
			BigDecimal baseFunds = v.get(0).getBaseFunds();
			BigDecimal nowFunds = v.get(v.size() - 1).getNowFunds();
			
			FundsHistory fundsHistory = new FundsHistory();
			fundsHistory.setBaseFunds(baseFunds);
			fundsHistory.setNowFunds(nowFunds);
			fundsHistory.setCode(k);
			
			//(baseFunds + nowFunds) * 100 / baseFunds
			//保留两位小数，向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
			fundsHistory.setChangeRate(baseFunds.subtract(nowFunds).multiply(new BigDecimal("100")).divide(baseFunds, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
			
			fundsHistoryResult.add(fundsHistory);
		});
		
		return fundsHistoryResult;
	}

}
