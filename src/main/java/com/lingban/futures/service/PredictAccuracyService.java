package com.lingban.futures.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.lingban.futures.common.DateQueryParam;
import com.lingban.futures.vo.PredictAccuracyVO;
/**
 * 预测成功率相关业务
 *
 */
public interface PredictAccuracyService{
	
	/**
	 * 获取一段时间预测成功率，时间粒度为天.
	 * @return 每天的预测数据集合
	 */
	public Map<String, PredictAccuracyVO> getPredictAccuracyHistoryDays(DateQueryParam dateParam, String futuresCode) throws Exception;
	
	/**
	 * 某段时间预测成功率统计
	 * @param dateParam
	 * @param futuresCode null则返回所有品种的统计数据
	 * @param orderType null则不排序
	 * @return 指定品种这端时间的预测正确率
	 */
	public List<PredictAccuracyVO> getPredictAccuracySummary(DateQueryParam dateParam, String futuresCode, String orderType) throws Exception;
	
	/**
	 * 返回某天预测正确率详情
	 * @param localDate 查询日期
	 * @param futuresCode 期货代码
	 * @param granularity 时间粒度 5 、 30、 60 
	 * @return
	 */
	public Map<String, PredictAccuracyVO> getPredictAccuracyOfOneDay(LocalDate localDate, String futuresCode, String granularity) throws Exception;
	
}
