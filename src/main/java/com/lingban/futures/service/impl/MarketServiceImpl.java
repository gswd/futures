package com.lingban.futures.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.common.cfg.BissinesConfig;
import com.lingban.futures.dao.FuturesRecordsHistoryDaysMapper;
import com.lingban.futures.dao.FuturesRecordsMapper;
import com.lingban.futures.model.FuturesRecords;
import com.lingban.futures.model.FuturesRecordsHistoryDays;
import com.lingban.futures.service.MarketService;
import com.lingban.futures.utils.DateUtils;
import com.lingban.futures.vo.MarketInfoVO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("marketService")
public class MarketServiceImpl implements MarketService {

	@Autowired
	protected FuturesRecordsHistoryDaysMapper futuresRecordsHistoryDaysMapper;
	@Autowired
	protected FuturesRecordsMapper futuresRecordsMapper;

	@Override
	public Map<String, MarketInfoVO> getMarketInfoDays(DateQueryParam dateParam, String futuresCode) {
		
		Example example = new Example(FuturesRecordsHistoryDays.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("code", futuresCode);
		if(dateParam.getStartDate() != null){
			criteria.andGreaterThanOrEqualTo("createTime", dateParam.getStartDate());
		}
		if(dateParam.getEndDate() != null){
			criteria.andLessThanOrEqualTo("createTime", dateParam.getEndDate());
		}

		example.orderBy("createTime").asc();
		List<FuturesRecordsHistoryDays> futuresRecordsHistoryDays = futuresRecordsHistoryDaysMapper.selectByExample(example);
		
		Map<String, MarketInfoVO> PredictAccuracyVOMap = futuresRecordsHistoryDays.stream()
				.collect(Collectors.toMap(key -> DateUtils.Date2LocalDateStr(key.getCreateTime()), val -> {
					MarketInfoVO vo = new MarketInfoVO();
					vo.setCode(val.getCode());
					vo.setSettlementPrice(val.getSettlement());
					vo.setClosingPrice(val.getClosing());
					vo.setTimePoint(val.getCreateTime());
					return vo;
				}, (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, LinkedHashMap::new));
		
		return PredictAccuracyVOMap;
	}

	@Override
	public Map<String, MarketInfoVO> getMarketInfoOfOneDay(LocalDate localDate, String futuresCode,
			String granularity) {
		Example example = new Example(FuturesRecords.class);
		Criteria criteria = example.createCriteria();
		
		//把结束日期延后一天，以便查询结束时间的当天数据
		criteria.andEqualTo("code", futuresCode).andGreaterThanOrEqualTo("createTime", localDate).andLessThan("createTime", localDate.plusDays(1));
		example.orderBy("createTime").asc();
		
		List<FuturesRecords> futuresRecords = futuresRecordsMapper.selectByExample(example);
		
		Map<String, Optional<FuturesRecords>> predictRecordsWithMin = null;
		
		
		switch (granularity) {
		case "5":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_5MIN), LinkedHashMap::new,
					Collectors.maxBy(Comparator.comparing(info -> info.getCreateTime()))));
			break;

		case "30":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_30MIN), LinkedHashMap::new,
					Collectors.maxBy(Comparator.comparing(info -> info.getCreateTime()))));
			break;
		case "60":
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap::new,
					Collectors.maxBy(Comparator.comparing(info -> info.getCreateTime()))));
			break;
		default:
			predictRecordsWithMin = futuresRecords.stream().collect(Collectors.groupingBy(
					e -> findBelongToTimes(e, BissinesConfig.TIMES_60MIN), LinkedHashMap::new,
					Collectors.maxBy(Comparator.comparing(info -> info.getCreateTime()))));
			break;
		}

		Map<String, MarketInfoVO> marketInfoVOMap = new LinkedHashMap<>();
		
		predictRecordsWithMin.forEach((k, v)-> {
			
			v.ifPresent(records -> {
				MarketInfoVO vo = new MarketInfoVO();
				
				vo.setCode(futuresCode);
				vo.setTimePoint(Date.from(LocalDateTime.of(localDate,LocalTime.parse(k)).atZone(ZoneId.systemDefault()).toInstant()));
				vo.setClosingPrice(records.getNewPrice());
				vo.setSettlementPrice(records.getSettlement());
				
				marketInfoVOMap.put(k, vo);
			});
			
		});
		
		return marketInfoVOMap;
	}
	
	private String findBelongToTimes(FuturesRecords records, List<LocalTime> TimesList) {

		Optional<LocalTime> opt = TimesList.stream().filter(x -> !LocalDateTime
				.ofInstant(records.getCreateTime().toInstant(), ZoneId.systemDefault()).toLocalTime().isAfter(x))
				.findFirst();
		return opt.orElse(TimesList.get(TimesList.size() - 1)).toString();
	}
}
