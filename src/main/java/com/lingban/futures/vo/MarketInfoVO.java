package com.lingban.futures.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 市场行情信息
 */
public class MarketInfoVO {
	
	private String code;
	//收盘价
	private BigDecimal closingPrice;
	//结算价
	private BigDecimal settlementPrice;
	// 时间
	@JSONField(format="yyyy-MM-dd")
	private Date timePoint;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(BigDecimal closingPrice) {
		this.closingPrice = closingPrice;
	}
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Date getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

}
