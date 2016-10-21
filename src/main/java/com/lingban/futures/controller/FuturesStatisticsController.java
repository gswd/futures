package com.lingban.futures.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.common.PageParam;
import com.lingban.futures.common.Paging;
import com.lingban.futures.model.FuturesFunds;
import com.lingban.futures.service.FuturesFundsService;
import com.lingban.futures.vo.StatusCode;

@RestController
@RequestMapping(value = "/statistics")
public class FuturesStatisticsController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FuturesFundsService futuresFundsService;

	@RequestMapping(value="/gainAndLoss",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo(PageParam pageParam, DateQueryParam dateParam) {
		
		Paging<FuturesFunds> page = futuresFundsService.selectFuturesFundsPage(pageParam, dateParam);
		
		logger.info("GET /t/user/login   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, page);

	}
	@RequestMapping(value="/gainAndLoss/{futuresCode}",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfoByCode(PageParam pageParam, DateQueryParam dateParam, @PathVariable String futuresCode) {
		
		Paging<FuturesFunds> page = futuresFundsService.selectFuturesFundsWithPeriod(pageParam, dateParam, futuresCode);
		
		logger.info("GET /t/user/login   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, page);
		
	}

}
