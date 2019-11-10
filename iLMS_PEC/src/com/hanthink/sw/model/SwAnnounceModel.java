package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

/**
* <p>Title: SwAnnounceModel.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/
public class SwAnnounceModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -591769839289749311L;
	/**id**/
	private String id;
	/**公告id**/
	private String noticeId;
	/**工厂**/
    private String factoryCode;
    /**公告标题**/
    private String noticeTitle;
    /**公告内容**/
    private String noticeContent;
    /**附件名称**/
    private String fileName;
    /**文件信息表ID**/
    private String fileId;
    /**发布人员**/
    private String publishUser;
    /**发布时间**/
    private String publishTime;
    /**公告开始日期**/
    private String noticeStartTime;
    /**公告结束日期**/
    private String noticeEndTime;
    /**查看下载日期**/
    private String downloadTime;
    /**是否需要反馈**/
    private String isFeedback;
    /**反馈状态**/
    private String feedbackStatus;
    /**反馈天数**/
    private String feedbackDay;
    /**公告状态**/
    private String noticeStatus;
    /**查看状态**/
    private String viewStatus;
    /**下载状态**/
    private String downloadStatus;
    /**反馈内容**/
    private String returnMsg;
    /**分配供应商(供应商所属分组id)**/
    private String groupIds;
    /**供应商代码**/
    private String supplierNo;
    /**分组id**/
    private String groupId;
    /**创建人**/
    private String creationUser;
    /**创建时间**/
    private String creationTime;
    /**最后修改用户**/
    private String lastModifiedUser;
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getNoticeStartTime() {
		return noticeStartTime;
	}
	public void setNoticeStartTime(String noticeStartTime) {
		this.noticeStartTime = noticeStartTime;
	}
	public String getNoticeEndTime() {
		return noticeEndTime;
	}
	public void setNoticeEndTime(String noticeEndTime) {
		this.noticeEndTime = noticeEndTime;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public String getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getFeedbackDay() {
		return feedbackDay;
	}
	public void setFeedbackDay(String feedbackDay) {
		this.feedbackDay = feedbackDay;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
    
}
