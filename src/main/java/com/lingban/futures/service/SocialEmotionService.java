package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.SocialEmotionHistoryDays;

public interface SocialEmotionService{
	
	/**
	 * 获取社会情绪历史信息，时间粒度为天.
	 * @return
	 */
	public List<SocialEmotionHistoryDays> getSocialEmotionHistoryDays(DateQueryParam dateParam, String futuresCode);
	
}
