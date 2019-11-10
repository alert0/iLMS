package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
* 
* <pre>
* 描述：固定取货时间维护数据实体对象
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17 10:49:09
* 版权：汉思信息技术有限公司
* </pre>
*/
public class PupPickTimeModel extends AbstractModel<String> {

	private static final long serialVersionUID = -7083047693444705361L;
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/** 工厂代码 */
	private String factoryCode;
	/** 集货线路 */
	private String routeCode;
	/** 当日车次 */
	private String todayNo;
	/** 取货时间 */
	private String pickTime;
	/** 收货时间 */
	private String arriveTime;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改人 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
	
	/** 导入UUID */
	private String impUUID;
	/** 检查结果 */
	private String checkResult;
	/** 校验信息 */
	private String checkInfo;
	/** 导入状态  0-未导入 1-已导入*/
	private String importStatus;
	/** 操作类型 I-新增 U-更新 */
	private String opeType;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getTodayNo() {
		return todayNo;
	}
	public void setTodayNo(String todayNo) {
		this.todayNo = todayNo;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
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
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getImpUUID() {
		return impUUID;
	}
	public void setImpUUID(String impUUID) {
		this.impUUID = impUUID;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	
	/**
	 * 
	 *@Description 校验导入信息
	 *@author zmj
	 *@param model
	 *@date 2018年9月15日 下午12:16:36
	 */
	public static void checkImportData(PupPickTimeModel model) {
		StringBuffer checkInfo = new StringBuffer();
		
		if(StringUtil.isEmpty(model.getRouteCode())) {
			checkInfo.append("路线代码为空;");
		}
		if(StringUtil.isEmpty(model.getTodayNo())) {
			checkInfo.append("当日班次为空;");
		}
		if(StringUtil.isEmpty(model.getPickTime())) {
			checkInfo.append("取货时间为空;");
		}
		if(StringUtil.isEmpty(model.getArriveTime())) {
			checkInfo.append("收货时间为空;");
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            model.setCheckResult("1");
            model.setOpeType("I");
            model.setCheckInfo("");
        }else{
            model.setCheckResult("0");
            model.setOpeType("U");
            model.setCheckInfo(checkInfo.toString());
        }
	}
}
