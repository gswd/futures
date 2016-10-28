package com.lingban.futures.service;

import java.time.LocalDate;
import java.util.Map;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.SocialEmotionHistory;
import com.lingban.futures.model.SocialEmotionHistoryDays;

public interface SocialEmotionService{
	
	/**
	 * 获取社会情绪历史信息，时间粒度为天.
	 * @return
	 */
	public Map<String, SocialEmotionHistoryDays> getSocialEmotionHistoryDays(DateQueryParam dateParam, String futuresCode) throws Exception;

	/**
	 * 返回时间粒度对应的情绪对象
	 * @param localDate 查询日期
	 * @param futuresCode 期货代码
	 * @param granularity 时间粒度
	 * @return
	 */
	public Map<String, SocialEmotionHistory> getSocialEmotionHistoryOfOneDay(LocalDate localDate, String futuresCode,
			String granularity) throws Exception;
	
}
