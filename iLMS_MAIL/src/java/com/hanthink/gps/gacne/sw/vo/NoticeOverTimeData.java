package com.hanthink.gps.gacne.sw.vo;

import java.io.Serializable;

/**
 * @Desc    : 公告未反馈VO
 * @FileName: NoticeOverTimeData.java 
 * @CreateOn: 2018-11-14 16:19
 * @author  : zhengwuchao  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2018-11-14	V1.0		zhengwuchao		新建
 * 
 *
 */
public class NoticeOverTimeData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 工厂
	 */
	private String factory;
	
	/**
	 * 公告ID
	 */
	private String noticeId;
	
	/**
	 * 公告名称
	 */
	private String noticeName;
	
	/**
	 * 公告发布人
	 */
	private String noticeUser;
	
	/**
	 * 公告发布时间
	 */
	private String noticeTime;
	
	/**
	 * 未查看人数
	 */
	private String viewNum;
	
	/**
	 * 未查看人数
	 */
	private String mail;
	
	/**重要联络人邮箱**/
	protected String importMail;
	/**PT订单联络人邮箱地址**/
	protected String PtMail;
	/**邮箱地址**/
	protected String massMail;
	/**邮箱地址A**/
	protected String excepMailA;
	/**邮箱地址B**/
	protected String excepMailB;
	/**邮箱地址**/
	protected String deviceMail;
	/**重要联络人邮箱地址1**/
	protected String importMailA;
	/**PT订单联络人邮箱地址1**/
	protected String PtMailA;
	/**邮箱地址1**/
	protected String massMailA;
	/**邮箱地址1**/
	protected String deviceMailA;
	/**邮箱地址1**/
	protected String packMailA;
	/**邮箱地址2**/
	protected String packMailB;
	/**邮箱地址**/
	protected String PtLogisticsMail;
	/**邮箱地址1**/
	protected String PtLogisticsMailA;
	/**邮箱地址**/
	protected String massLogisticeMail;
	/**邮箱地址1**/
	protected String massLogisticeMailA;
	/**发布人**/
	protected String publishUser;
	/**发布人邮箱**/
	protected String publishtEmail;
	
	/**供应商代码**/
	protected String supplierNo;
	/**供应商名称**/
	protected String supplierName;
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeUser() {
		return noticeUser;
	}

	public void setNoticeUser(String noticeUser) {
		this.noticeUser = noticeUser;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getViewNum() {
		return viewNum;
	}

	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getImportMail() {
		return importMail;
	}

	public void setImportMail(String importMail) {
		this.importMail = importMail;
	}

	public String getPtMail() {
		return PtMail;
	}

	public void setPtMail(String ptMail) {
		PtMail = ptMail;
	}

	public String getMassMail() {
		return massMail;
	}

	public void setMassMail(String massMail) {
		this.massMail = massMail;
	}

	public String getExcepMailA() {
		return excepMailA;
	}

	public void setExcepMailA(String excepMailA) {
		this.excepMailA = excepMailA;
	}

	public String getExcepMailB() {
		return excepMailB;
	}

	public void setExcepMailB(String excepMailB) {
		this.excepMailB = excepMailB;
	}

	public String getDeviceMail() {
		return deviceMail;
	}

	public void setDeviceMail(String deviceMail) {
		this.deviceMail = deviceMail;
	}

	public String getImportMailA() {
		return importMailA;
	}

	public void setImportMailA(String importMailA) {
		this.importMailA = importMailA;
	}

	public String getPtMailA() {
		return PtMailA;
	}

	public void setPtMailA(String ptMailA) {
		PtMailA = ptMailA;
	}

	public String getMassMailA() {
		return massMailA;
	}

	public void setMassMailA(String massMailA) {
		this.massMailA = massMailA;
	}

	public String getDeviceMailA() {
		return deviceMailA;
	}

	public void setDeviceMailA(String deviceMailA) {
		this.deviceMailA = deviceMailA;
	}

	public String getPackMailA() {
		return packMailA;
	}

	public void setPackMailA(String packMailA) {
		this.packMailA = packMailA;
	}

	public String getPackMailB() {
		return packMailB;
	}

	public void setPackMailB(String packMailB) {
		this.packMailB = packMailB;
	}

	public String getPtLogisticsMail() {
		return PtLogisticsMail;
	}

	public void setPtLogisticsMail(String ptLogisticsMail) {
		PtLogisticsMail = ptLogisticsMail;
	}

	public String getPtLogisticsMailA() {
		return PtLogisticsMailA;
	}

	public void setPtLogisticsMailA(String ptLogisticsMailA) {
		PtLogisticsMailA = ptLogisticsMailA;
	}

	public String getMassLogisticeMail() {
		return massLogisticeMail;
	}

	public void setMassLogisticeMail(String massLogisticeMail) {
		this.massLogisticeMail = massLogisticeMail;
	}

	public String getMassLogisticeMailA() {
		return massLogisticeMailA;
	}

	public void setMassLogisticeMailA(String massLogisticeMailA) {
		this.massLogisticeMailA = massLogisticeMailA;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public String getPublishtEmail() {
		return publishtEmail;
	}

	public void setPublishtEmail(String publishtEmail) {
		this.publishtEmail = publishtEmail;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
}
