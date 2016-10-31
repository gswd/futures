package com.lingban.futures.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.common.cfg.BissinesConfig;
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
	public Map<String, SocialEmotionHistoryDays> getSocialEmotionHistoryDays(DateQueryParam dateParam, String futuresCode) throws Exception {

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

		LinkedHashMap<String, SocialEmotionHistoryDays> SocialEmotionHistoryDaysMap = socialEmotionHistoryDays.stream()
				.collect(Collectors.toMap(seh -> DateUtils.Date2LocalDateStr(seh.getCreateTime()), Function.identity(),
						(existingVal, newVal) -> newVal, LinkedHashMap::new));

		return SocialEmotionHistoryDaysMap;
	}

	@Override
	public Map<String, SocialEmotionHistory> getSocialEmotionHistoryOfOneDay(LocalDate localDate, String futuresCode, String granularity) throws Exception {
		
		Example example = new Example(SocialEmotionHistoryDays.class);
		Criteria criteria = example.createCriteria();
		
		//把结束日期延后一天，以便查询结束时间的当天数据
		criteria.andEqualTo("code", futuresCode).andGreaterThanOrEqualTo("createTime", localDate).andLessThan("createTime", localDate.plusDays(1));
		example.orderBy("createTime").asc();
		List<SocialEmotionHistory> socialEmotionHistory = socialEmotionHistoryMapper.selectByExample(example);
		
//		Map<String, SocialEmotionHistory> socialEmotionHistory5Min = socialEmotionHistory.stream().collect(Collectors.toMap(d -> DateUtils.Date2StrHourMinute(d.getCreateTime()), Function.identity()));		
		
		Map<String, List<SocialEmotionHistory>> socialEmotionHistoryWithMin = null;
		
		switch (granularity) {
		case "5":
			socialEmotionHistoryWithMin = socialEmotionHistory.stream()
					.collect(Collectors.groupingBy(e -> findBelongToTimes(e, BissinesConfig.TIMES_5MIN), LinkedHashMap :: new, Collectors.toList()));
			break;

		case "30":
			socialEmotionHistoryWithMin = socialEmotionHistory.stream()
					.collect(Collectors.groupingBy(e -> findBelongToTimes(e, BissinesConfig.TIMES_30MIN), LinkedHashMap :: new, Collectors.toList()));
			break;
		case "60":
			socialEmotionHistoryWithMin = socialEmotionHistory.stream()
					.collect(Collectors.groupingBy(e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap :: new, Collectors.toList()));
			break;
		default:
			socialEmotionHistoryWithMin = socialEmotionHistory.stream()
					.collect(Collectors.groupingBy(e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap :: new, Collectors.toList()));
			break;
		}

		Map<String, SocialEmotionHistory> socialEmotionHistoryMap = new LinkedHashMap<>();
		
		socialEmotionHistoryWithMin.forEach((k, v)-> {
			
			DoubleSummaryStatistics negativeSummary = v.stream().collect(Collectors.summarizingDouble(v1 -> v1.getNegative().doubleValue()));
			negativeSummary.getAverage();
			

			DoubleSummaryStatistics positiveSummary = v.stream().collect(Collectors.summarizingDouble(v1 -> v1.getPositive().doubleValue()));
			positiveSummary.getAverage();
			
			
			SocialEmotionHistory se = new SocialEmotionHistory();
			
			se.setCode(futuresCode);
			se.setPositive(BigDecimal.valueOf(v.stream().collect(Collectors.averagingDouble(v1 -> v1.getPositive().doubleValue()))).setScale(4, BigDecimal.ROUND_HALF_UP));
			se.setNegative(BigDecimal.valueOf(v.stream().collect(Collectors.averagingDouble(v1 -> v1.getNegative().doubleValue()))).setScale(4, BigDecimal.ROUND_HALF_UP));
			
			socialEmotionHistoryMap.put(k, se);
		});
		
		return socialEmotionHistoryMap;
	}

	
	private String findBelongToTimes(SocialEmotionHistory seh, List<LocalTime> TimesList) {

		Optional<LocalTime> opt = TimesList.stream().filter(x -> !LocalDateTime
				.ofInstant(seh.getCreateTime().toInstant(), ZoneId.systemDefault()).toLocalTime().withSecond(0).isAfter(x))
				.findFirst();
		return opt.orElse(TimesList.get(TimesList.size() - 1)).toString();
	}
}
