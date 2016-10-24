package com.lingban.futures.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.FundsHistory;
import com.lingban.futures.service.FundsHistoryService;
import com.lingban.futures.vo.StatusCode;

@RestController
@RequestMapping(value = "/statistics")
public class FuturesStatisticsController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(FuturesStatisticsController.class);
	@Autowired
	private FundsHistoryService fundsHistoryService;

	@RequestMapping(value="/gainAndLoss" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo(DateQueryParam dateParam, @RequestParam(required = false) String orderType) {
		
		List<FundsHistory> fundsHistoryList = fundsHistoryService.getFundsHistoryByPeriod(dateParam, orderType);
		
		logger.info("GET /gainAndLoss   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, fundsHistoryList);

	}

}
