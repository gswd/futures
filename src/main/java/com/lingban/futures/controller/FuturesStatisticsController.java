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
import com.lingban.futures.service.MarketService;
import com.lingban.futures.service.PredictAccuracyService;
import com.lingban.futures.service.SocialEmotionService;
import com.lingban.futures.vo.MarketInfoVO;
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
	@Autowired
	private MarketService marketService;
	
	
	//---------------------盈亏统计---------------------------------------
	/**
	 * 所有盈亏数据
	 * @param dateParam
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value="/gainAndLoss" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo(DateQueryParam dateParam, @RequestParam(required = false) String orderType) {
		logger.info("GET /gainAndLoss   futuresFundsInfo() ...");
		
		List<FundsHistory> fundsHistorys;
		try {
			fundsHistorys = fundsHistoryService.getFundsHistoryByPeriod(dateParam, orderType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		
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
		logger.info("GET /gainAndLoss/{futuresCode}   futuresFundsInfoByCode() ...");
		
		List<FundsHistory> fundsHistorys;
		try {
			fundsHistorys = fundsHistoryService.getFundsHistoryByPeriodAndCode(dateParam, futuresCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		
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
		logger.info("GET /socialEmotion/history/{futuresCode}/day   socialEmotionHistory() ...");
		
		Map<String, SocialEmotionHistoryDays> socialEmotionHistoryDays;
		try {
			socialEmotionHistoryDays = socialEmotionService.getSocialEmotionHistoryDays(dateParam, futuresCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		
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
		logger.info("GET /socialEmotion/history/{futuresCode}/min/{granularity}   socialEmotionHistoryWithMin() ...");
		
		Map<String, SocialEmotionHistory> socialEmotionHistoryDays;
		try {
			socialEmotionHistoryDays = socialEmotionService.getSocialEmotionHistoryOfOneDay(localDate, futuresCode, granularity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		
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
	public String marketDataByCode(DateQueryParam dateParam, @PathVariable String futuresCode ) {
		logger.info("GET /market/history/{futuresCode}/day   marketDataByCode() ...");
		
		Map<String, MarketInfoVO> MarketInfoVO;
		try {
			MarketInfoVO = marketService.getMarketInfoDays(dateParam, futuresCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		
		return buildResultInfo(StatusCode.SUCCESS, MarketInfoVO);
		
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
	public String marketDataByCodeWithMin(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @PathVariable String futuresCode, @PathVariable String granularity ) {
		logger.info("GET /market/history/{futuresCode}/min/{granularity}   marketDataByCodeWithMin() ...");
		
		Map<String, MarketInfoVO> MarketInfoVO;
		try {
			MarketInfoVO = marketService.getMarketInfoOfOneDay(localDate, futuresCode, granularity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		return buildResultInfo(StatusCode.SUCCESS, MarketInfoVO);
		
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
		logger.info("GET /predictAccuracy/history/{futuresCode}/min/{granularity}   predictAccuracyHistoryWithMin() ...");
		
		Map<String, PredictAccuracyVO> predictAccuracyVO;
		try {
			predictAccuracyVO = predictAccuracyService.getPredictAccuracyOfOneDay(localDate, futuresCode, granularity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
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
		logger.info("GET /predictAccuracy/history/{futuresCode}/day   predictAccuracyHistory() ...");
		
		Map<String, PredictAccuracyVO> predictAccuracyHistoryDays;
		try {
			predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracyHistoryDays(dateParam, futuresCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
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
		logger.info("GET /predictAccuracy/history/{futuresCode}   predictAccuracyHistorySummary() ...");
		
		List<PredictAccuracyVO> predictAccuracyHistoryDays;
		try {
			predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracySummary(dateParam, futuresCode, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
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
		logger.info("GET /predictAccuracy/history/{futuresCode}   predictAccuracyHistorySummary() ...");
		
		List<PredictAccuracyVO> predictAccuracyHistoryDays;
		try {
			predictAccuracyHistoryDays = predictAccuracyService.getPredictAccuracySummary(dateParam, null, orderType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FAIL;
		}
		return buildResultInfo(StatusCode.SUCCESS, predictAccuracyHistoryDays);
		
	}
	
}
