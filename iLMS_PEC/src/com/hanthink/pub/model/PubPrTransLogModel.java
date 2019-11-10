package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;

 /**
 * 
 * <pre> 
 * 描述：打印日常日志表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPrTransLogModel extends AbstractModel<Integer>{

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月21日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -2433891830870635705L;

	/**
	 * 主表  打印日常日志表
	 */
	/**
	* ID
	*/
	private Integer id ; 
	
	/**
	* 日志级别
	*/
	private String trxLevel; 
	
	/**
	* 日志类型
	*/
	private String trxType; 
	
	/**
	* 日志类型
	*/
	private String category; 
	
	/**
	* 后台来源类
	*/
	private String source; 
	
	/**
	* 事务
	*/
	private String trxName; 
	
	/**
	* 日志时间
	*/
	private java.util.Date entryTime;  
	
	/**
	* 日志时间字符
	*/
	private String entryTimeStr;  
	
	/**
	* 日志时间（开始）
	*/
	private String entryTimeStrStart;  
	
	/**
	* 日志时间（结束）
	*/
	private String entryTimeStrEnd;  
	
	/**
	* 打印内容
	*/
	private String content;  
	
	/**
	* 打印机
	*/
	private String printerName;
	
	/**
	 * 工厂
	 */
	private String factoryCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrxLevel() {
		return trxLevel;
	}

	public void setTrxLevel(String trxLevel) {
		this.trxLevel = trxLevel;
	}
	
	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTrxName() {
		return trxName;
	}

	public void setTrxName(String trxName) {
		this.trxName = trxName;
	}

	public java.util.Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(java.util.Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getEntryTimeStr() {
		return entryTimeStr;
	}

	public void setEntryTimeStr(String entryTimeStr) {
		this.entryTimeStr = entryTimeStr;
	}

	public String getEntryTimeStrStart() {
		return entryTimeStrStart;
	}

	public void setEntryTimeStrStart(String entryTimeStrStart) {
		this.entryTimeStrStart = entryTimeStrStart;
	}

	public String getEntryTimeStrEnd() {
		return entryTimeStrEnd;
	}

	public void setEntryTimeStrEnd(String entryTimeStrEnd) {
		this.entryTimeStrEnd = entryTimeStrEnd;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}  
	
	

}