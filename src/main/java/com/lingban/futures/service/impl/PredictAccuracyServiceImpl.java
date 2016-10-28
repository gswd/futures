package com.lingban.futures.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.common.cfg.BissinesConfig;
import com.lingban.futures.dao.FuturesRecordsMapper;
import com.lingban.futures.dao.PredictAccuracyHistoryDaysMapper;
import com.lingban.futures.model.FuturesRecords;
import com.lingban.futures.model.PredictAccuracyHistoryDays;
import com.lingban.futures.service.PredictAccuracyService;
import com.lingban.futures.utils.DateUtils;
import com.lingban.futures.vo.PredictAccuracyVO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("predictAccuracyService")
public class PredictAccuracyServiceImpl implements PredictAccuracyService {

	@Autowired
	protected PredictAccuracyHistoryDaysMapper predictAccuracyHistoryDaysMapper;
	@Autowired
	protected FuturesRecordsMapper futuresRecordsMapper;

	@Override
	public Map<String, PredictAccuracyVO> getPredictAccuracyHistoryDays(DateQueryParam dateParam,
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
		List<PredictAccuracyHistoryDays> predictAccuracyHistoryDays = predictAccuracyHistoryDaysMapper.selectByExample(example);
		
		Map<String, PredictAccuracyVO> PredictAccuracyVOMap = predictAccuracyHistoryDays.stream()
				.collect(Collectors.toMap(pa -> DateUtils.Date2LocalDateStr(pa.getCreateTime()), pah -> {
					PredictAccuracyVO vo = new PredictAccuracyVO();
					vo.setCode(pah.getCode());
					vo.setCorrectTimes(pah.getCorrect());
					vo.setId(pah.getId());
					vo.setAccuracy(pah.getAccuracy());
					vo.setTotalTimes(pah.getNum());
					vo.setTimePoint(pah.getCreateTime());
					return vo;
				}, (existingVal, newVal) -> newVal, LinkedHashMap::new));
		
		return PredictAccuracyVOMap;
	}

	@Override
	public List<PredictAccuracyVO> getPredictAccuracySummary(DateQueryParam dateParam, String futuresCode, String orderType) {
		
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("startDate", dateParam.getStartDate());
		criteria.put("endDate", dateParam.getEndDate());
		criteria.put("futuresCode", futuresCode);
		criteria.put("orderType", orderType);
		
		List<PredictAccuracyHistoryDays> predictAccuracyHistoryDays = predictAccuracyHistoryDaysMapper.getPredictAccuracySummary(criteria);
		
		List<PredictAccuracyVO> PredictAccuracyVOList = predictAccuracyHistoryDays.stream().map(pah -> {
			PredictAccuracyVO vo = new PredictAccuracyVO();
			vo.setCode(pah.getCode());
			vo.setCorrectTimes(pah.getCorrect());
			vo.setId(pah.getId());
			vo.setAccuracy(pah.getAccuracy());
			vo.setTotalTimes(pah.getNum());
			vo.setTimePoint(pah.getCreateTime());
			return vo;
		}).collect(Collectors.toList());
		
		return PredictAccuracyVOList;
	}
	
	@Override
	public Map<String, PredictAccuracyVO> getPredictAccuracyOfOneDay(LocalDate localDate, String futuresCode, String granularity) {
		Example example = new Example(FuturesRecords.class);
		Criteria criteria = example.createCriteria();
		
		//把结束日期延后一天，以便查询结束时间的当天数据
		criteria.andEqualTo("code", futuresCode).andGreaterThanOrEqualTo("createTime", localDate).andLessThan("createTime", localDate.plusDays(1));
		example.orderBy("createTime").asc();
		
		List<FuturesRecords> futuresRecords = futuresRecordsMapper.selectByExample(example);
		
		Map<String, Map<Boolean, Long>> predictRecordsWithMin = null;
		
		
		switch (granularity) {
		case "5":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_5MIN), LinkedHashMap::new,
					Collectors.partitioningBy(e1 -> e1.getPriceTrend().equals(e1.getPredict()), Collectors.counting())));
			break;

		case "30":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_30MIN), LinkedHashMap::new,
					Collectors.partitioningBy(e1 -> e1.getPriceTrend().equals(e1.getPredict()), Collectors.counting())));
			break;
		case "60":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap::new,
					Collectors.partitioningBy(e1 -> e1.getPriceTrend().equals(e1.getPredict()), Collectors.counting())));
			break;
		default:
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap::new,
					Collectors.partitioningBy(e1 -> e1.getPriceTrend().equals(e1.getPredict()), Collectors.counting())));
			break;
		}

		Map<String, PredictAccuracyVO> predictAccuracyVOMap = new LinkedHashMap<>();
		
		predictRecordsWithMin.forEach((k, v)-> {
			
			PredictAccuracyVO vo = new PredictAccuracyVO();
			
			vo.setCode(futuresCode);
			vo.setTotalTimes(v.get(Boolean.TRUE) + v.get(Boolean.FALSE));
			vo.setCorrectTimes(v.get(Boolean.TRUE));
			vo.setTimePoint(Date.from(LocalDateTime.of(localDate,LocalTime.parse(k)).atZone(ZoneId.systemDefault()).toInstant()));
			vo.setAccuracy(BigDecimal.valueOf(vo.getCorrectTimes()).divide(BigDecimal.valueOf(vo.getTotalTimes()), 4, BigDecimal.ROUND_HALF_UP));
			
			predictAccuracyVOMap.put(k, vo);
		});
		
		return predictAccuracyVOMap;
	}

	
	private String findBelongToTimes(FuturesRecords records, List<LocalTime> TimesList) {

		Optional<LocalTime> opt = TimesList.stream().filter(x -> !LocalDateTime
				.ofInstant(records.getCreateTime().toInstant(), ZoneId.systemDefault()).toLocalTime().isAfter(x))
				.findFirst();
		return opt.orElse(TimesList.get(TimesList.size() - 1)).toString();
	}
	
}
