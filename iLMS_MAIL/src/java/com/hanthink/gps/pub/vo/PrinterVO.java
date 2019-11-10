package com.hanthink.gps.pub.vo;


/**
 * 打印机配置基本信息
 */
public class PrinterVO extends BaseVO {
	
	private String printerGroupDesc;
	private String billTypeDesc;
	public String getPrinterGroupDesc() {
		return printerGroupDesc;
	}
	public void setPrinterGroupDesc(String printerGroupDesc) {
		this.printerGroupDesc = printerGroupDesc;
	}
	public String getBillTypeDesc() {
		return billTypeDesc;
	}
	public void setBillTypeDesc(String billTypeDesc) {
		this.billTypeDesc = billTypeDesc;
	}

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private String factory;
	private String printerName;
	private String printerDesc;
	private String billType;
	private String printerGroup;
	private String note;
	private String creationTime;
	private String creationUser;
	private String lastModifiedUser;
	private String lastModifiedTime;
	private String lastModifiedIp;
	
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public String getPrinterDesc() {
		return printerDesc;
	}
	public void setPrinterDesc(String printerDesc) {
		this.printerDesc = printerDesc;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getPrinterGroup() {
		return printerGroup;
	}
	public void setPrinterGroup(String printerGroup) {
		this.printerGroup = printerGroup;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getLastModifiedIp() {
		return lastModifiedIp;
	}
	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}
	
}