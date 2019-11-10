package com.hanthink.jit.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：报废信息 实体对象
  * 作者:lz
  * 日期:2018-10-09 13:58:27
  * </pre>
  */
public class JitVehScrapModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -4361943355152352016L;

	private String id;
	/**
	 * 工厂
     */
	private String factoryCode; 
	
	/**
	 * 产品订单号
     */
	private String orderNo; 
	
	/**
	 * VIN     */
	private String vin; 
	
	/**
	 * 报废车间    */
	private String scrapWorkcenter; 
	
	/**
	 * 最后经过工位
     */
	private String scrapStationCode; 
	
	/**
	 * 工位描述
     */
	private String scrapStationDesc; 
	
	/**
	 * 报废原因
     */
	private String scrapReason; 
	
	/**
	 * 报废时间
     */
	private String scrapTime; 
	
	/**
	 * 报废时间（开始）
	 */
	private String scrapTimeStrStart;
	
	/**
	 * 报废时间（结束）
	 */
	private String scrapTimeStrEnd;
	
	/**
	 * 数据字典类型“JIT_ADJUST_KB”     */
	private String adjustKbState; 
	
	/**
	 * 补看板用户
     */
	private String adjustUser; 
	
	/**
	 * 补看板时间
     */
	private String adjustTime; 
	
	/**
	 * 补看板时间（字符）
     */
	private String adjustTimeStr; 
	
	/**
	 * 补看板IP
     */
	private String adjustIp; 
	
	/**
	 * 创建时间     */
	private String creationTime; 
	
	/**
	 * 最后修改人     */
	private String lastModifiedTime; 
	
	

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getScrapWorkcenter() {
		return scrapWorkcenter;
	}

	public void setScrapWorkcenter(String scrapWorkcenter) {
		this.scrapWorkcenter = scrapWorkcenter;
	}

	public String getScrapStationCode() {
		return scrapStationCode;
	}

	public void setScrapStationCode(String scrapStationCode) {
		this.scrapStationCode = scrapStationCode;
	}

	public String getScrapStationDesc() {
		return scrapStationDesc;
	}

	public void setScrapStationDesc(String scrapStationDesc) {
		this.scrapStationDesc = scrapStationDesc;
	}

	public String getScrapReason() {
		return scrapReason;
	}

	public void setScrapReason(String scrapReason) {
		this.scrapReason = scrapReason;
	}

	public String getScrapTime() {
		return scrapTime;
	}

	public void setScrapTime(String scrapTime) {
		this.scrapTime = scrapTime;
	}

	public String getScrapTimeStrStart() {
		return scrapTimeStrStart;
	}

	public void setScrapTimeStrStart(String scrapTimeStrStart) {
		this.scrapTimeStrStart = scrapTimeStrStart;
	}

	public String getScrapTimeStrEnd() {
		return scrapTimeStrEnd;
	}

	public void setScrapTimeStrEnd(String scrapTimeStrEnd) {
		this.scrapTimeStrEnd = scrapTimeStrEnd;
	}

	public String getAdjustKbState() {
		return adjustKbState;
	}

	public void setAdjustKbState(String adjustKbState) {
		this.adjustKbState = adjustKbState;
	}

	public String getAdjustUser() {
		return adjustUser;
	}

	public void setAdjustUser(String adjustUser) {
		this.adjustUser = adjustUser;
	}

	public String getAdjustTime() {
		return adjustTime;
	}

	public void setAdjustTime(String adjustTime) {
		this.adjustTime = adjustTime;
	}

	public String getAdjustTimeStr() {
		return adjustTimeStr;
	}

	public void setAdjustTimeStr(String adjustTimeStr) {
		this.adjustTimeStr = adjustTimeStr;
	}

	public String getAdjustIp() {
		return adjustIp;
	}

	public void setAdjustIp(String adjustIp) {
		this.adjustIp = adjustIp;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
	
}