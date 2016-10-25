package com.lingban.futures.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.dao.PredictAccuracyHistoryDaysMapper;
import com.lingban.futures.model.PredictAccuracyHistoryDays;
import com.lingban.futures.service.PredictAccuracyService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("predictAccuracyService")
public class PredictAccuracyServiceImpl implements PredictAccuracyService {

	@Autowired
	protected PredictAccuracyHistoryDaysMapper predictAccuracyHistoryDaysMapper;

	@Override
	public List<PredictAccuracyHistoryDays> getPredictAccuracyHistoryDays(DateQueryParam dateParam,
			String futuresCode) {
		Example example = new Example(PredictAccuracyHistoryDays.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("code", futuresCode);
		if(dateParam.getStartDate() != null){
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if(dateParam.getEndDate() != null){
			criteria.andLessThanOrEqualTo("createTime", dateParam.getEndDate());
		}

		example.orderBy("createTime").asc();
		List<PredictAccuracyHistoryDays> socialEmotionHistoryDays = predictAccuracyHistoryDaysMapper.selectByExample(example);
		
		return socialEmotionHistoryDays;
	}

	@Override
	public List<PredictAccuracyHistoryDays> getPredictAccuracySummary(DateQueryParam dateParam, String futuresCode) {
		
		List<PredictAccuracyHistoryDays> predictAccuracyHistoryDays = predictAccuracyHistoryDaysMapper.getPredictAccuracySummary(dateParam, futuresCode);
		
		return predictAccuracyHistoryDays;
	}

}
