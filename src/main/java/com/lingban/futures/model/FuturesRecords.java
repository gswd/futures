package com.lingban.futures.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 期货行情记录以及预测信息
 *
 */
@Table(name = "futures_records")
@NameStyle(Style.normal)
public class FuturesRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize = false)
	private Integer id;
	private String code;
	// 实际变化趋势
	private Integer priceTrend;
	// 趋势预测
	private Integer predict;
	// 正确率
	@Transient
	private BigDecimal accuracy;

	// 时间
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;

	// 最新价
	private BigDecimal newPrice;
	// 结算价
	private BigDecimal settlement;

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

	public Integer getPriceTrend() {
		return priceTrend;
	}

	public void setPriceTrend(Integer priceTrend) {
		this.priceTrend = priceTrend;
	}

	public Integer getPredict() {
		return predict;
	}

	public void setPredict(Integer predict) {
		this.predict = predict;
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

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public BigDecimal getSettlement() {
		return settlement;
	}

	public void setSettlement(BigDecimal settlement) {
		this.settlement = settlement;
	}

	@Override
	public String toString() {
		return "FuturesRecords [id=" + id + ", code=" + code + ", priceTrend=" + priceTrend + ", predict=" + predict
				+ ", accuracy=" + accuracy + ", createTime=" + createTime + ", newPrice=" + newPrice + ", settlement="
				+ settlement + "]";
	}

}
