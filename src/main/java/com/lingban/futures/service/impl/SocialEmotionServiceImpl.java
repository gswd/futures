package com.lingban.futures.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.SocialEmotionHistory;
import com.lingban.futures.model.SocialEmotionHistoryDays;
import com.lingban.futures.service.SocialEmotionService;
import com.lingban.futures.utils.DateUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("socialEmotionService")
public class SocialEmotionServiceImpl implements SocialEmotionService {

	@Autowired
	protected Mapper<SocialEmotionHistoryDays> socialEmotionHistoryDaysMapper;

	@Autowired
	protected Mapper<SocialEmotionHistory> socialEmotionHistoryMapper;

	@Override
	public List<SocialEmotionHistoryDays> getSocialEmotionHistoryDays(DateQueryParam dateParam, String futuresCode) {

		Example example = new Example(SocialEmotionHistoryDays.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("code", futuresCode);
		if (dateParam.getStartDate() != null) {
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if (dateParam.getEndDate() != null) {
			//把结束日期延后一天，以便查询结束时间的当天数据
			criteria.andLessThan("createTime", dateParam.getEndDate().plusDays(1));
		}

		example.orderBy("createTime").asc();
		List<SocialEmotionHistoryDays> socialEmotionHistoryDays = socialEmotionHistoryDaysMapper
				.selectByExample(example);

		return socialEmotionHistoryDays;
	}

	@Override
	public Map<String, SocialEmotionHistory> getSocialEmotionHistory(LocalDate localDate, String futuresCode, String granularity) {
		
		Example example = new Example(SocialEmotionHistoryDays.class);
		Criteria criteria = example.createCriteria();
		
		//把结束日期延后一天，以便查询结束时间的当天数据
		criteria.andEqualTo("code", futuresCode).andGreaterThanOrEqualTo("createTime", localDate).andLessThan("createTime", localDate.plusDays(1));
		example.orderBy("createTime").asc();
		List<SocialEmotionHistory> socialEmotionHistory = socialEmotionHistoryMapper.selectByExample(example);
		
		Map<String, SocialEmotionHistory> socialEmotionHistory5Min = socialEmotionHistory.stream().collect(Collectors.toMap(d -> DateUtils.Date2StrHourMinute(d.getCreateTime()), Function.identity()));		
		return null;
	}

	
}
