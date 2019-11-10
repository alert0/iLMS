package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class MonAbnormalTrackModel extends AbstractModel<String>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年4月10日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -6815046880835824057L;
	/** 车间 */
	private String workCenter;
	/** 业务类型 */
	private String optType;
	/** 工程 */
	private String projName;
	/** 异常总数量 */
	private String excepNum;
	/** 异常总箱数 */
	private String excepBox;
	/** 工厂 */
	private String factoryCode;
	
	/** 验收总数 */
	private String checkCount;
	/** 备件总数 */
	private String prePareCount;
	/** 配送总数 */
	private String distCount;
	
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getExcepNum() {
		return excepNum;
	}
	public void setExcepNum(String excepNum) {
		this.excepNum = excepNum;
	}
	public String getExcepBox() {
		return excepBox;
	}
	public void setExcepBox(String excepBox) {
		this.excepBox = excepBox;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(String checkCount) {
		this.checkCount = checkCount;
	}
	public String getPrePareCount() {
		return prePareCount;
	}
	public void setPrePareCount(String prePareCount) {
		this.prePareCount = prePareCount;
	}
	public String getDistCount() {
		return distCount;
	}
	public void setDistCount(String distCount) {
		this.distCount = distCount;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
