package com.lingban.futures.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.common.cfg.BissinesConfig;
import com.lingban.futures.model.FundsHistory;
import com.lingban.futures.service.FundsHistoryService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("fundsHistoryService")
public class FundsHistoryServiceImpl extends AbstractBaseService<FundsHistory> implements FundsHistoryService {

	@Override
	public List<FundsHistory> getFundsHistoryByPeriod(DateQueryParam dateParam, String orderType) throws Exception{
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
			
			//(nowFunds - baseFunds) * 100 / baseFunds
			//保留两位小数，向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
			fundsHistory.setChangeRate(nowFunds.subtract(baseFunds).multiply(BissinesConfig.BIGDECIMAL_100).divide(baseFunds, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
			
			fundsHistoryResult.add(fundsHistory);
		});
		
		
		if("ASC".equals(orderType)){
			return fundsHistoryResult.stream().sorted(Comparator.comparing(FundsHistory :: getChangeRate)).collect(Collectors.toList());
		}
		if("DESC".equals(orderType)){
			return fundsHistoryResult.stream().sorted(Comparator.comparing(FundsHistory :: getChangeRate).reversed()).collect(Collectors.toList());
		}
		
		return fundsHistoryResult;
	}

	@Override
	public List<FundsHistory> getFundsHistoryByPeriodAndCode(DateQueryParam dateParam, String futuresCode) throws Exception{
		Example example = new Example(FundsHistory.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("code", futuresCode);
		if(dateParam.getStartDate() != null){
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if(dateParam.getEndDate() != null){
			criteria.andLessThanOrEqualTo("createTime", dateParam.getEndDate());
		}
		
		example.orderBy("createTime").asc();
		List<FundsHistory> fundsHistoryList = mapper.selectByExample(example);
		
		if(fundsHistoryList == null || fundsHistoryList.size() == 0){
			return fundsHistoryList;
		}
		
		List<FundsHistory> fundsHistoryResult = new ArrayList<>();
		
		BigDecimal baseFunds = fundsHistoryList.get(0).getBaseFunds();
		BigDecimal nowFunds = fundsHistoryList.get(fundsHistoryList.size() - 1).getNowFunds();
		
		FundsHistory fundsHistory = new FundsHistory();
		fundsHistory.setBaseFunds(baseFunds);
		fundsHistory.setNowFunds(nowFunds);
		fundsHistory.setCode(futuresCode);
		
		//(baseFunds - nowFunds) * 100 / baseFunds
		//保留两位小数，向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
		fundsHistory.setChangeRate(baseFunds.subtract(nowFunds).multiply(BissinesConfig.BIGDECIMAL_100).divide(baseFunds, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		fundsHistoryResult.add(fundsHistory);
		
		return fundsHistoryResult;
	}
	
}
