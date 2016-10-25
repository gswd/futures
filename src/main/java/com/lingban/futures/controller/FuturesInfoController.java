package com.lingban.futures.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lingban.futures.common.cfg.BissinesConfig;
import com.lingban.futures.vo.StatusCode;

@RestController
@RequestMapping(value = "/futures")
public class FuturesInfoController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(FuturesInfoController.class);

	@RequestMapping(value="/infos" , method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String futuresFundsInfo() {
		
		logger.info("GET /infos   futuresFundsInfo() ...");
		return buildResultInfo(StatusCode.SUCCESS,  BissinesConfig.FUTURE);

	}
	

}
