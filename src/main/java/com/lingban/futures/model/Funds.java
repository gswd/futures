package com.lingban.futures.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "future_funds")
@NameStyle(Style.normal)
public class Funds {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize=false)
	private Integer id;
	private String code;
	private Integer buyNum;
	private Integer sellNum;
	private BigDecimal funds;
	private BigDecimal baseFunds;
	private BigDecimal nowFunds;
	@Transient
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

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
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

	public Double getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Double changeRate) {
		this.changeRate = changeRate;
	}

	@Override
	public String toString() {
		return "FuturesFunds [id=" + id + ", code=" + code + ", buyNum=" + buyNum + ", sellNum=" + sellNum + ", funds="
				+ funds + ", baseFunds=" + baseFunds + ", nowFunds=" + nowFunds + ", changeRate=" + changeRate + "]";
	}

}
