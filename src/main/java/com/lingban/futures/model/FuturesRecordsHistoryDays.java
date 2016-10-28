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
 * 期货行情记录以及预测信息
 *
 */
@Table(name = "futures_records_history_days")
@NameStyle(Style.normal)
public class FuturesRecordsHistoryDays {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize=false)
	private Integer id;
	private String code;
	//收盘价
	private BigDecimal closing;
	//结算价
	private BigDecimal settlement;
	// 时间
	@JSONField(format="yyyy-MM-dd")
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
	public BigDecimal getClosing() {
		return closing;
	}
	public void setClosing(BigDecimal closing) {
		this.closing = closing;
	}
	public BigDecimal getSettlement() {
		return settlement;
	}
	public void setSettlement(BigDecimal settlement) {
		this.settlement = settlement;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "FuturesRecordsHistoryDays [id=" + id + ", code=" + code + ", closing=" + closing + ", settlement="
				+ settlement + ", createTime=" + createTime + "]";
	}

}
