package com.lingban.futures.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 预测信息历史，时间粒度为天
 */
@Table(name = "futures_predict_accuracy_history_days")
@NameStyle(Style.normal)
public class PredictAccuracyHistoryDays {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize=false)
	private Integer id;
	private String code;
	// 预测总次数
	private Integer num;
	// 预测正确次数
	private Integer correct;
	// 成功率
	private BigDecimal accuracy;
	// 时间
	private Date createTime;

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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PredictAccuracyHistoryDays [id=" + id + ", code=" + code + ", num=" + num + ", correct=" + correct
				+ ", accuracy=" + accuracy + ", createTime=" + createTime + "]";
	}
}
