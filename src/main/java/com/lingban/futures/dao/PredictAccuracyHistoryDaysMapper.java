package com.lingban.futures.dao;

import java.util.List;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.model.PredictAccuracyHistoryDays;

import tk.mybatis.mapper.common.Mapper;

public interface PredictAccuracyHistoryDaysMapper extends Mapper<PredictAccuracyHistoryDays>{
	/**
	 * 某段时间预测成功率统计
	 * @param dateParam
	 * @param futuresCode
	 * @return
	 */
	public List<PredictAccuracyHistoryDays> getPredictAccuracySummary(DateQueryParam dateParam, String futuresCode);
}
