package com.lingban.futures.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.SocialEmotionHistoryDays;
import com.lingban.futures.service.SocialEmotionService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("socialEmotionService")
public class SocialEmotionServiceImpl implements SocialEmotionService {

	@Autowired
	protected Mapper<SocialEmotionHistoryDays> socialEmotionHistoryDaysMapper;

	@Override
	public List<SocialEmotionHistoryDays> getSocialEmotionHistoryDays(DateQueryParam dateParam, String futuresCode) {
		
		Example example = new Example(SocialEmotionHistoryDays.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("code", futuresCode);
		if(dateParam.getStartDate() != null){
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if(dateParam.getEndDate() != null){
			criteria.andLessThanOrEqualTo("createTime", dateParam.getEndDate());
		}

		example.orderBy("createTime").asc();
		List<SocialEmotionHistoryDays> socialEmotionHistoryDays = socialEmotionHistoryDaysMapper.selectByExample(example);
		
		return socialEmotionHistoryDays;
	}


}
