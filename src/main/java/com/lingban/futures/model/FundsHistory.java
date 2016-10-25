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

@Table(name = "futures_funds_history")
@NameStyle(Style.normal)
public class FundsHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize=false)
	private Integer id;
	private String code;
	private BigDecimal baseFunds;
	private BigDecimal nowFunds;
	private Date createTime;
	private Double changeRate;

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

	public BigDecimal getBaseFunds() {
		return baseFunds;
	}

	public void setBaseFunds(BigDecimal baseFunds) {
		this.baseFunds = baseFunds;
	}

	public BigDecimal getNowFunds() {
		return nowFunds;
	}

	public void setNowFunds(BigDecimal nowFunds) {
		this.nowFunds = nowFunds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Double changeRate) {
		this.changeRate = changeRate;
	}

}
