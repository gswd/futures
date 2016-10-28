package com.lingban.futures.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 预测信息
 */
public class PredictAccuracyVO {
	
	@JSONField(serialize=false)
	private Integer id;
	private String code;
	// 预测总次数
	private Long totalTimes;
	// 预测正确次数
	private Long correctTimes;
	// 正确率
	private BigDecimal accuracy;
	
	// 时间
	@JSONField(format="yyyy-MM-dd")
	private Date timePoint;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Long totalTimes) {
		this.totalTimes = totalTimes;
	}

	public Long getCorrectTimes() {
		return correctTimes;
	}

	public void setCorrectTimes(Long correctTimes) {
		this.correctTimes = correctTimes;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	@Override
	public String toString() {
		return "PredictAccuracyVO [id=" + id + ", code=" + code + ", totalTimes=" + totalTimes + ", correctTimes="
				+ correctTimes + ", accuracy=" + accuracy + ", timePoint=" + timePoint + "]";
	}

}
