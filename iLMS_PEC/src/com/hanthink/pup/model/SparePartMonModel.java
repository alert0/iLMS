package com.hanthink.pup.model;

import java.util.Map;

import com.hotent.base.core.model.AbstractModel;

public class SparePartMonModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 6357833801638118488L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*备件出库*/	
	//工厂
	private String factoryCode;
	//当前批次
	private String currBatch;
	//简号
	private String partShortNo;
	//配送地址
	private String delPlace;
	//需求数量
	private String requierNum;
	//已配送数量
	private String delNum;
	//配送状态
	private String delStatus;
	
	/*零件验收*/
	//站台
	private String station;
	//车牌
	private String carCard;
	//卸货开始
	private String unloadStart;
	//卸货结束
	private String unloadEnd;
	//验收开始
	private String checkStart;
	//验收结束
	private String checkEnd;
	//卸货延迟状态
	private String unloadStatus;
	//验收延迟状态
	private String checkStatus;
	//延迟数量总数
	private String delayCount;
	private String planSheetNo;
	
	//延迟站台号列表
	private Map<String, Object> delayStationMap;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getCurrBatch() {
		return currBatch;
	}
	public void setCurrBatch(String currBatch) {
		this.currBatch = currBatch;
	}
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	public String getDelPlace() {
		return delPlace;
	}
	public void setDelPlace(String delPlace) {
		this.delPlace = delPlace;
	}
	public String getRequierNum() {
		return requierNum;
	}
	public void setRequierNum(String requierNum) {
		this.requierNum = requierNum;
	}
	public String getDelNum() {
		return delNum;
	}
	public void setDelNum(String delNum) {
		this.delNum = delNum;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getCarCard() {
		return carCard;
	}
	public void setCarCard(String carCard) {
		this.carCard = carCard;
	}
	public String getUnloadStart() {
		return unloadStart;
	}
	public void setUnloadStart(String unloadStart) {
		this.unloadStart = unloadStart;
	}
	public String getUnloadEnd() {
		return unloadEnd;
	}
	public void setUnloadEnd(String unloadEnd) {
		this.unloadEnd = unloadEnd;
	}
	public String getCheckStart() {
		return checkStart;
	}
	public void setCheckStart(String checkStart) {
		this.checkStart = checkStart;
	}
	public String getCheckEnd() {
		return checkEnd;
	}
	public void setCheckEnd(String checkEnd) {
		this.checkEnd = checkEnd;
	}
	public String getUnloadStatus() {
		return unloadStatus;
	}
	public void setUnloadStatus(String unloadStatus) {
		this.unloadStatus = unloadStatus;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getDelayCount() {
		return delayCount;
	}
	public void setDelayCount(String delayCount) {
		this.delayCount = delayCount;
	}
	public String getPlanSheetNo() {
		return planSheetNo;
	}
	public void setPlanSheetNo(String planSheetNo) {
		this.planSheetNo = planSheetNo;
	}
	public Map<String, Object> getDelayStationMap() {
		return delayStationMap;
	}
	public void setDelayStationMap(Map<String, Object> delayStationMap) {
		this.delayStationMap = delayStationMap;
	}
}
