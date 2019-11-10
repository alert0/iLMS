package com.hanthink.pub.model;

import com.hanthink.jiso.model.JisoInsModel;

/**
 * 
 * @ClassName: PubPrintInsModel
 * @Description: 指示票打印实体类
 * @author dtp
 * @date 2018年10月22日
 */
public class PubPrintInsModel extends JisoInsModel{
	
	/**
	 * 底色标识
	 */
	protected String remarkFlag;

	/**
	 * 底色标识2
	 */
	protected String remarkFlag2;
	
	/**
	 * 零件号
	 */
	protected String partNo;
	
	/**
	 * 零件号2
	 */
	protected String partNo2;
	
	/**
	 * 序号
	 */
	protected String serial;
	
	/**
	 * 车身序号
	 */
	protected String wcSeqno;
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 记号
	 */
	protected String partMark;
	
	/**
	 * 序号
	 */
	protected String serial2;
	
	/**
	 * 车身序号
	 */
	protected String wcSeqno2;
	
	/**
	 * 简号
	 */
	protected String partShortNo2;
	
	/**
	 * 记号
	 */
	protected String partMark2;
	
	//******************************厂外同步指示票*********************************
	
	/**
	 * 序号
	 */
	protected String no;
	
	/**
	 * 序号2
	 */
	protected String no2;
	
	/**
	 * 拣货地址
	 */
	protected String storage;
	
	/**
	 * 拣货地址2
	 */
	protected String storage2;
	
	/**
	 * 配送地址2
	 */
	protected String location2;
	
	/**
	 * 数量2
	 */
	protected String requireNum2;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getWcSeqno() {
		return wcSeqno;
	}

	public void setWcSeqno(String wcSeqno) {
		this.wcSeqno = wcSeqno;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getSerial2() {
		return serial2;
	}

	public void setSerial2(String serial2) {
		this.serial2 = serial2;
	}

	public String getWcSeqno2() {
		return wcSeqno2;
	}

	public void setWcSeqno2(String wcSeqno2) {
		this.wcSeqno2 = wcSeqno2;
	}

	public String getPartShortNo2() {
		return partShortNo2;
	}

	public void setPartShortNo2(String partShortNo2) {
		this.partShortNo2 = partShortNo2;
	}

	public String getPartMark2() {
		return partMark2;
	}

	public void setPartMark2(String partMark2) {
		this.partMark2 = partMark2;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getStorage2() {
		return storage2;
	}

	public void setStorage2(String storage2) {
		this.storage2 = storage2;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getRequireNum2() {
		return requireNum2;
	}

	public void setRequireNum2(String requireNum2) {
		this.requireNum2 = requireNum2;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartNo2() {
		return partNo2;
	}

	public void setPartNo2(String partNo2) {
		this.partNo2 = partNo2;
	}

	public String getRemarkFlag() {
		return remarkFlag;
	}

	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
	}

	public String getRemarkFlag2() {
		return remarkFlag2;
	}

	public void setRemarkFlag2(String remarkFlag2) {
		this.remarkFlag2 = remarkFlag2;
	}
	
	
}
