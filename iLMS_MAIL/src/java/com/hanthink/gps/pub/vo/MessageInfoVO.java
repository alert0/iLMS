package com.hanthink.gps.pub.vo;

import java.io.Serializable;

/**
 * 公告信息VO
 * @author Administrator
 *
 */
public class MessageInfoVO implements Serializable {

	private static final long serialVersionUID = 3844234944729267469L;
	
	/** 公告ID */
	private String infoId;
	
	/** 工厂 */
	private String factory;
	
	/** 公告标题 */
	private String infoTitle;
	
	/** 公告内容 */
	private String infoDetails;
	
	/** 公告附件名称 */
	private String fileName;
	
	/** 公告附件路径 */
	private String filePath;
	
	/** 公告发布人(用户名) */
	private String publishUser;
	
	/** 公告发布人(姓名) */
	private String publishUserCName;
	
	/** 发布日期 */
	private String publistDateStr;
	
	/** 生效开始日期 */
	private String startDateStr;
	
	/** 生效截止日期 */
	private String endDateStr;
	
	/** 公告生效期 */
	private String effectDateStr;
	
	/** 生效状态1：生效 0：失效 */
	private String effStatus;
	
	/** 供应商分组ID(以","分隔) */
	private String supGroupIdList;
	
	private java.lang.String createUser;//创建用户(登录名)
	private java.util.Date createTime;//创建时间
	private java.lang.String lastModifyUser;//最后修改用户(登录名)
	private java.util.Date lastModifyTime;//最后修改时间
	
	
	/** 登录供应商代码 */
	private java.lang.String loginSupplierNo; 
	
	/** 登录用户名 */
	private java.lang.String loginUserName; 
	
	/** 查询发布日期-起始 */
	private java.lang.String queryDateStartStr;
	
	/**  查询发布日期-截止 */
	private java.lang.String queryDateEndStr;
	
	/** 是否有附件 */
	private String isFile;
	
	/** 是否供应商 */
	private String isSupplier;
	
	/** 公告查看状态(0:未查看1:已查看) */
	private String viewStatus;
	
	/** 公告下载状态(0:未下载1:已下载) */
	private String downloadStatus;
	
	/** 公告通知状态(0:未通知1:已通知) */
	private String noticeStatus;
	
	
	
	/**
	 * 获取公告ID
	 * @return the infoId 公告ID
	 */
	public String getInfoId() {
		return infoId;
	}

	/**
	 * 设置公告ID
	 * @param infoId the infoId 公告ID
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**
	 * 获取工厂
	 * @return the factory 工厂
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * 设置工厂
	 * @param factory the factory 工厂
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	/**
	 * 获取公告标题
	 * @return the infoTitle 公告标题
	 */
	public String getInfoTitle() {
		return infoTitle;
	}

	/**
	 * 设置公告标题
	 * @param infoTitle the infoTitle 公告标题
	 */
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	/**
	 * 获取公告内容
	 * @return the infoDetails 公告内容
	 */
	public String getInfoDetails() {
		return infoDetails;
	}

	/**
	 * 设置公告内容
	 * @param infoDetails the infoDetails 公告内容
	 */
	public void setInfoDetails(String infoDetails) {
		this.infoDetails = infoDetails;
	}

	/**
	 * 获取公告附件名称
	 * @return the fileName 公告附件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置公告附件名称
	 * @param fileName the fileName 公告附件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取公告附件路径
	 * @return the filePath 公告附件路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置公告附件路径
	 * @param filePath the filePath 公告附件路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取公告发布人(用户名)
	 * @return the publishUser 公告发布人(用户名)
	 */
	public String getPublishUser() {
		return publishUser;
	}

	/**
	 * 设置公告发布人(用户名)
	 * @param publishUser the publishUser 公告发布人(用户名)
	 */
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	/**
	 * 获取公告发布人(姓名)
	 * @return the publishUserCName 公告发布人(姓名)
	 */
	public String getPublishUserCName() {
		return publishUserCName;
	}

	/**
	 * 设置公告发布人(姓名)
	 * @param publishUserCName the publishUserCName 公告发布人(姓名)
	 */
	public void setPublishUserCName(String publishUserCName) {
		this.publishUserCName = publishUserCName;
	}

	/**
	 * 获取发布日期
	 * @return the publistDateStr 发布日期
	 */
	public String getPublistDateStr() {
		return publistDateStr;
	}

	/**
	 * 设置发布日期
	 * @param publistDateStr the publistDateStr 发布日期
	 */
	public void setPublistDateStr(String publistDateStr) {
		this.publistDateStr = publistDateStr;
	}

	/**
	 * 获取生效开始日期
	 * @return the startDateStr 生效开始日期
	 */
	public String getStartDateStr() {
		return startDateStr;
	}

	/**
	 * 设置生效开始日期
	 * @param startDateStr the startDateStr 生效开始日期
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	/**
	 * 获取生效截止日期
	 * @return the endDateStr 生效截止日期
	 */
	public String getEndDateStr() {
		return endDateStr;
	}

	/**
	 * 设置生效截止日期
	 * @param endDateStr the endDateStr 生效截止日期
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	/**
	 * 获取createUser
	 * @return the createUser createUser
	 */
	public java.lang.String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置createUser
	 * @param createUser the createUser createUser
	 */
	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取createTime
	 * @return the createTime createTime
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置createTime
	 * @param createTime the createTime createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取lastModifyUser
	 * @return the lastModifyUser lastModifyUser
	 */
	public java.lang.String getLastModifyUser() {
		return lastModifyUser;
	}

	/**
	 * 设置lastModifyUser
	 * @param lastModifyUser the lastModifyUser lastModifyUser
	 */
	public void setLastModifyUser(java.lang.String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	/**
	 * 获取lastModifyTime
	 * @return the lastModifyTime lastModifyTime
	 */
	public java.util.Date getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * 设置lastModifyTime
	 * @param lastModifyTime the lastModifyTime lastModifyTime
	 */
	public void setLastModifyTime(java.util.Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * 获取登录供应商代码
	 * @return the loginSupplierNo 登录供应商代码
	 */
	public java.lang.String getLoginSupplierNo() {
		return loginSupplierNo;
	}

	/**
	 * 设置登录供应商代码
	 * @param loginSupplierNo the loginSupplierNo 登录供应商代码
	 */
	public void setLoginSupplierNo(java.lang.String loginSupplierNo) {
		this.loginSupplierNo = loginSupplierNo;
	}

	/**
	 * 获取登录用户名
	 * @return the loginUserName 登录用户名
	 */
	public java.lang.String getLoginUserName() {
		return loginUserName;
	}

	/**
	 * 设置登录用户名
	 * @param loginUserName the loginUserName 登录用户名
	 */
	public void setLoginUserName(java.lang.String loginUserName) {
		this.loginUserName = loginUserName;
	}

	/**
	 * 获取查询发布日期-起始
	 * @return the queryDateStartStr 查询发布日期-起始
	 */
	public java.lang.String getQueryDateStartStr() {
		return queryDateStartStr;
	}

	/**
	 * 设置查询发布日期-起始
	 * @param queryDateStartStr the queryDateStartStr 查询发布日期-起始
	 */
	public void setQueryDateStartStr(java.lang.String queryDateStartStr) {
		this.queryDateStartStr = queryDateStartStr;
	}

	/**
	 * 获取查询发布日期-截止
	 * @return the queryDateEndStr 查询发布日期-截止
	 */
	public java.lang.String getQueryDateEndStr() {
		return queryDateEndStr;
	}

	/**
	 * 设置查询发布日期-截止
	 * @param queryDateEndStr the queryDateEndStr 查询发布日期-截止
	 */
	public void setQueryDateEndStr(java.lang.String queryDateEndStr) {
		this.queryDateEndStr = queryDateEndStr;
	}

	/**
	 * 获取公告生效期
	 * @return the effectDateStr 公告生效期
	 */
	public String getEffectDateStr() {
		return effectDateStr;
	}

	/**
	 * 设置公告生效期
	 * @param effectDateStr the effectDateStr 公告生效期
	 */
	public void setEffectDateStr(String effectDateStr) {
		this.effectDateStr = effectDateStr;
	}

	/**
	 * 获取生效状态1：生效0：失效
	 * @return the effStatus 生效状态1：生效0：失效
	 */
	public String getEffStatus() {
		return effStatus;
	}

	/**
	 * 设置生效状态1：生效0：失效
	 * @param effStatus the effStatus 生效状态1：生效0：失效
	 */
	public void setEffStatus(String effStatus) {
		this.effStatus = effStatus;
	}

	/**
	 * 获取供应商分组ID(以","分隔)
	 * @return the supGroupIdList 供应商分组ID(以","分隔)
	 */
	public String getSupGroupIdList() {
		return supGroupIdList;
	}

	/**
	 * 设置供应商分组ID(以","分隔)
	 * @param supGroupIdList the supGroupIdList 供应商分组ID(以","分隔)
	 */
	public void setSupGroupIdList(String supGroupIdList) {
		this.supGroupIdList = supGroupIdList;
	}

	/**
	 * 获取是否有附件
	 * @return the isFile 是否有附件
	 */
	public String getIsFile() {
		return isFile;
	}

	/**
	 * 设置是否有附件
	 * @param isFile the isFile 是否有附件
	 */
	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

	/**
	 * 获取是否供应商
	 * @return the isSupplier 是否供应商
	 */
	public String getIsSupplier() {
		return isSupplier;
	}

	/**
	 * 设置是否供应商
	 * @param isSupplier the isSupplier 是否供应商
	 */
	public void setIsSupplier(String isSupplier) {
		this.isSupplier = isSupplier;
	}

	/**
	 * 获取公告查看状态(0:未查看1:已查看)
	 * @return the viewStatus 公告查看状态(0:未查看1:已查看)
	 */
	public String getViewStatus() {
		return viewStatus;
	}

	/**
	 * 设置公告查看状态(0:未查看1:已查看)
	 * @param viewStatus the viewStatus 公告查看状态(0:未查看1:已查看)
	 */
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	/**
	 * 获取公告下载状态(0:未下载1:已下载)
	 * @return the downloadStatus 公告下载状态(0:未下载1:已下载)
	 */
	public String getDownloadStatus() {
		return downloadStatus;
	}

	/**
	 * 设置公告下载状态(0:未下载1:已下载)
	 * @param downloadStatus the downloadStatus 公告下载状态(0:未下载1:已下载)
	 */
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	/**
	 * 获取公告通知状态(0:未通知1:已通知)
	 * @return the noticeStatus 公告通知状态(0:未通知1:已通知)
	 */
	public String getNoticeStatus() {
		return noticeStatus;
	}

	/**
	 * 设置公告通知状态(0:未通知1:已通知)
	 * @param noticeStatus the noticeStatus 公告通知状态(0:未通知1:已通知)
	 */
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	
	
	

}
