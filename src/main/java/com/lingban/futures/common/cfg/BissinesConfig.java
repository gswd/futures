package com.lingban.futures.common.cfg;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lingban.futures.model.Futures;
import com.lingban.futures.service.FuturesInfoService;

/**
 * 与业务相关的配置
 *
 */
@Component
public class BissinesConfig {
	private Logger logger = LoggerFactory.getLogger(BissinesConfig.class);
	/**
	 * 所有期货品种,品种代码到品种基本信息对象的映射
	 * <p> 例如：NI : 镍 </p>
	 * 
	 */
	public static Map<String, Futures> FUTURE;
	
	public static final List<LocalTime> TIMES_5MIN = generateTimes(5);
	public static final List<LocalTime> TIMES_30MIN = generateTimes(30);
	public static final List<LocalTime> TIMES_60MIN = generateTimes(60);
	
	public static final BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
	
	@Autowired
	private FuturesInfoService futuresInfoService;
	
	
	
	@PostConstruct
	public void init(){
		logger.info("加载期货品种信息...");
		//加载期货品种信息
		List<Futures> futures = null;
		try {
			futures = futuresInfoService.selectAll();
		} catch (Exception e) {
			logger.error("init futures Error...\n\r {}", e.getMessage());
			System.exit(1);
		}
		FUTURE = futures.stream().collect(Collectors.toMap(Futures :: getCode, Function.identity()));
		FUTURE.forEach((k, v) -> logger.info("{} : {}", k, v.getName()));
	}
	
	/**
	 * 生成指定时间间隔的本地时间集合
	 * @param timeUnit
	 * @return
	 */
	private static List<LocalTime> generateTimes(int timeInterval){
		
		return Stream.iterate(LocalTime.of(timeInterval/60, timeInterval%60), time -> time.plusMinutes(timeInterval)).limit(24*60/timeInterval).collect(Collectors.toList());
	}
}
