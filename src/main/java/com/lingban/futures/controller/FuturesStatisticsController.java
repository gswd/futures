package com.lingban.futures.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.FundsHistory;
import com.lingban.futures.model.SocialEmotionHistory;
import com.lingban.futures.model.SocialEmotionHistoryDays;
import com.lingban.futures.service.FundsHistoryService;
import com.lingban.futures.service.PredictAccuracyService;
import com.lingban.futures.service.SocialEmotionService;
import com.lingban.futures.vo.PredictAccuracyVO;
import com.lingban.futures.vo.StatusCode;

@RestController
@RequestMapping(value = "/statistics")
public class FuturesStatisticsController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(FuturesStatisticsController.class);
	@Autowired
	private FundsHistoryService fundsHistoryService;
	@Autowired
	private SocialEmotionService socialEmotionService;
	@Autowired
	private PredictAccuracyService predictAccuracyService;
	
	
	//---------------------盈亏统计---------------------------------------
	/**
	 * 所有盈亏数据
	 * @param dateParam
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value="/gainAndLoss" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo(DateQueryParam dateParam, @RequestParam(required = false) String orderType) {
		
		List<FundsHistory> fundsHistorys = fundsHistoryService.getFundsHistoryByPeriod(dateParam, orderType);
		
		logger.info("GET /gainAndLoss   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, fundsHistorys);

	}
	
	/**
	 * 指定品种盈亏数据
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/gainAndLoss/{futuresCode}" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfoByCode(DateQueryParam dateParam, @PathVariable String futuresCode) {
		
		List<FundsHistory> fundsHistorys = fundsHistoryService.getFundsHistoryByPeriodAndCode(dateParam, futuresCode);
		
		logger.info("GET /gainAndLoss   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, fundsHistorys);

	}
	
	//---------------------社会情绪统计---------------------------------------
	/**
	 * 社会情绪历史数据,某品种多天情绪集合.时间粒度为天.
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/socialEmotion/history/{futuresCode}/day" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String socialEmotionHistory(DateQueryParam dateParam, @PathVariable String futuresCode) {
		
		List<SocialEmotionHistoryDays> socialEmotionHistoryDays = socialEmotionService.getSocialEmotionHistoryDays(dateParam, futuresCode);
		
		logger.info("GET /socialEmotion/history/{futuresCode}/day   SocialEmotionHistory() ...");
		return buildResultInfo(StatusCode.SUCCESS, socialEmotionHistoryDays);
		
	}
	
	/**
	 * 某品种某天情绪数据按不同粒度的集合.支持5min 30min 60min
	 * 某天情绪详情
	 * @param localDate
	 * @param granularity 时间粒度支持5min 30min 60min
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/socialEmotion/history/{futuresCode}/min/{granularity}" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String socialEmotionHistoryWithMin(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @PathVariable String futuresCode, @PathVariable String granularity) {
		
		Map<String, SocialEmotionHistory> socialEmotionHistoryDays = socialEmotionService.getSocialEmotionHistoryOfOneDay(localDate, futuresCode, granularity);
		
		logger.info("GET /socialEmotion/history/{futuresCode}/min/{granularity}   socialEmotionHistoryWithMin() ...");
		return buildResultInfo(StatusCode.SUCCESS, socialEmotionHistoryDays);
		
	}
	
	//---------------------行情数据---------------------------------------
	
	
	
	/**
	 * 某品种多天行情数据集合
	 * 
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/market/history/{futuresCode}/day" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String marketDataByCode(@RequestParam DateQueryParam dateParam, @PathVariable String futuresCode ) {
		//TODO
		logger.info("GET /market/history/{futuresCode}/day   marketDataByCode() ...");
		return buildResultInfo(StatusCode.SUCCESS, null);
		
	}
	
	
	/**
	 * 某品种某天行情数据，按不同粒度的集合.支持5min 30min 60min
	 * 某天天行详情
	 * @param localDate
	 * @param granularity 时间粒度支持5min 30min 60min
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/market/history/{futuresCode}/min/{granularity}" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String marketDataByCodeWithMin(@RequestParam LocalDate localDate, @PathVariable String futuresCode, @PathVariable String granularity ) {
		
		//TODO
		
		logger.info("GET /market/history/{futuresCode}/min/{granularity}   marketDataByCodeWithMin() ...");
		return buildResultInfo(StatusCode.SUCCESS, null);
		
	}
	
	//---------------------正确率统计---------------------------------------
	/**
	 * 某品种某天预测成功率按不同粒度的集合.支持5min 30min 60min
	 * 某天预测成功率详情
	 * @param localDate
	 * @param granularity 时间粒度支持5min 30min 60min
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/predictAccuracy/history/{futuresCode}/min/{granularity}" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String predictAccuracyHistoryWithMin(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @PathVariable String futuresCode, @PathVariable String granularity ) {
		
		//TODO
		
		Map<String, PredictAccuracyVO> predictAccuracyVO = predictAccuracyService.getPredictAccuracyOfOneDay(localDate, futuresCode, granularity);
		logger.info("GET /predictAccuracy/history/{futuresCode}/min/{granularity}   predictAccuracyHistoryWithMin() ...");
		return buildResultInfo(StatusCode.SUCCESS, predictAccuracyVO);
	}
	
	
	/**
	 * 历史预测成功率，某品种多天预测成功率集合，时间粒度为天.
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/predictAccuracy/history/{futuresCode}/day" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String predictAccuracyHistory(DateQueryParam dateParam, @PathVariable String futuresCode) {
		
		List<PredictAccuracyVO> predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracyHistoryDays(dateParam, futuresCode);
		logger.info("GET /predictAccuracy/history/{futuresCode}/day   predictAccuracyHistory() ...");
		return buildResultInfo(StatusCode.SUCCESS, predictAccuracyHistoryDays);
		
	}

	/**
	 * 某品种历史预测成功率统计.
	 * 列表使用
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/predictAccuracy/history/{futuresCode}" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String predictAccuracyHistorySummaryByCode(DateQueryParam dateParam, @PathVariable String futuresCode) {
		
		List<PredictAccuracyVO> predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracySummary(dateParam, futuresCode, null);
		logger.info("GET /predictAccuracy/history/{futuresCode}   predictAccuracyHistorySummary() ...");
		return buildResultInfo(StatusCode.SUCCESS, predictAccuracyHistoryDays);
		
	}
	
	/**
	 * 所有品种历史预测成功率统计.
	 * 列表使用
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	@RequestMapping(value="/predictAccuracy/history" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String predictAccuracyHistorySummary(DateQueryParam dateParam, @RequestParam(required = false) String orderType) {
		
		List<PredictAccuracyVO> predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracySummary(dateParam, null, orderType);
		logger.info("GET /predictAccuracy/history/{futuresCode}   predictAccuracyHistorySummary() ...");
		return buildResultInfo(StatusCode.SUCCESS, predictAccuracyHistoryDays);
		
	}
	
}
