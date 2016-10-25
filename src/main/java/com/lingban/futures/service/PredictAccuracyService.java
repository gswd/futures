package com.lingban.futures.service;

import java.util.List;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.PredictAccuracyHistoryDays;
/**
 * 预测成功率相关业务
 *
 */
public interface PredictAccuracyService{
	
	/**
	 * 获取一段时间预测成功率，时间粒度为天.
	 * @return 每天的预测数据集合
	 */
	public List<PredictAccuracyHistoryDays> getPredictAccuracyHistoryDays(DateQueryParam dateParam, String futuresCode);
	
	/**
	 * 某段时间预测成功率统计
	 * @param dateParam
	 * @param futuresCode null则返回所有品种的统计数据
	 * @return 指定品种这端时间的预测正确率
	 */
	public List<PredictAccuracyHistoryDays> getPredictAccuracySummary(DateQueryParam dateParam, String futuresCode);
}
