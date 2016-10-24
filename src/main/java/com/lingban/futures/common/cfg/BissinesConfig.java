package com.lingban.futures.common.cfg;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	
	@Autowired
	private FuturesInfoService futuresInfoService;
	
	
	
	@PostConstruct
	public void init(){
		logger.info("加载期货品种信息...");
		//加载期货品种信息
		List<Futures> futures = futuresInfoService.selectAll();
		FUTURE = futures.stream().collect(Collectors.toMap(Futures :: getCode, Function.identity()));
		FUTURE.forEach((k, v) -> logger.info("{} : {}", k, v.getName()));
	}
	
}
