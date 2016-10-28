package com.lingban.futures.service;

import java.time.LocalDate;
import java.util.Map;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.vo.MarketInfoVO;

/**
 * 市场交易行情业务
 *
 */
public interface MarketService {
	/**
	 * 获取一段时间某品种行情，时间粒度为天.
	 * @return 每天的收盘价、结算价等信息
	 */
	Map<String, MarketInfoVO> getMarketInfoDays(DateQueryParam dateParam, String futuresCode) throws Exception;

	/**
	 * 返回某天预测正确率详情
	 * 
	 * @param localDate
	 *            查询日期
	 * @param futuresCode
	 *            期货代码
	 * @param granularity
	 *            时间粒度 5 、 30、 60
	 * @return
	 */
	Map<String, MarketInfoVO> getMarketInfoOfOneDay(LocalDate localDate, String futuresCode, String granularity) throws Exception;
}
