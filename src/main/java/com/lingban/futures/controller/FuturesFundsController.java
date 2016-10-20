package com.lingban.futures.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lingban.futures.common.PageParam;
import com.lingban.futures.common.Paging;
import com.lingban.futures.model.FuturesFunds;
import com.lingban.futures.service.FuturesFundsService;
import com.lingban.futures.vo.StatusCode;

@RestController
@RequestMapping(value = "/futuresFunds")
public class FuturesFundsController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FuturesFundsService futuresFundsService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo(PageParam pageParam) {

		Paging<FuturesFunds> page = futuresFundsService.selectFuturesFundsPage(pageParam);
		System.out.println("1: ===>"+futuresFundsService.testCache("abc1"));
		System.out.println("1: ===>"+futuresFundsService.testCache("abc1"));
		logger.info("GET /t/user/login   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS, page);

	}

	
}
