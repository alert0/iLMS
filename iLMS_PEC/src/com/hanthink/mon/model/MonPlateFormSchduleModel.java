package com.hanthink.mon.model;

import java.util.List;

import com.hotent.base.core.model.AbstractModel;

/**
 * 站台Model
 * @author WY
 *
 */
public class MonPlateFormSchduleModel extends AbstractModel<String>{

	private static final long serialVersionUID = 4843548741968260031L;
	
	private String id;
	
	/**
	 * 订单类型
	 */
	private String logisticsModel;
	
	/**
	 *  车间
	 */
	private String workcenter;
	
	private String time1;
	private String time2;
	private String time3;
	private String time4;
	private String time5;
	private String time6;
	private String time7;
	private String time8;
	private String time9;
	private String time10;
	
	/**
	 * 不用于显示,最后的一个时间截止点
	 */
	private String time11;
	/**
	 * 当前时间
	 */
	private String currentTime;
	
	/**
	 * 转移时间
	 */
	private String inTransTime;
	
	/**
	 * 卸货时间
	 */
	private String inUnloadTime;
	
	/**
	 * 返空装箱时间
	 */
	private String inEmptyTime;
	
	/**
	 * 大物卸货时间
	 */
	private String inUnloadBigTime;
	
	/**
	 * 小物卸货时间
	 */
	private String inUnloadSmallTime;
	
	/**
	 * 卸货站台
	 */
	private List<MonPlateFormModel> downPlateForms;
	
	/**
	 * 返空站台
	 */
	private List<MonPlateFormModel> upPlateForms;
		
	public String getInUnloadBigTime() {
		return inUnloadBigTime;
	}
	public void setInUnloadBigTime(String inUnloadBigTime) {
		this.inUnloadBigTime = inUnloadBigTime;
	}
	public String getInUnloadSmallTime() {
		return inUnloadSmallTime;
	}
	public void setInUnloadSmallTime(String inUnloadSmallTime) {
		this.inUnloadSmallTime = inUnloadSmallTime;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getTime3() {
		return time3;
	}
	public void setTime3(String time3) {
		this.time3 = time3;
	}
	public String getTime4() {
		return time4;
	}
	public void setTime4(String time4) {
		this.time4 = time4;
	}
	public String getTime5() {
		return time5;
	}
	public void setTime5(String time5) {
		this.time5 = time5;
	}
	public String getTime6() {
		return time6;
	}
	public void setTime6(String time6) {
		this.time6 = time6;
	}
	public String getTime7() {
		return time7;
	}
	public void setTime7(String time7) {
		this.time7 = time7;
	}
	public String getTime8() {
		return time8;
	}
	public void setTime8(String time8) {
		this.time8 = time8;
	}
	public String getTime9() {
		return time9;
	}
	public void setTime9(String time9) {
		this.time9 = time9;
	}
	public String getTime10() {
		return time10;
	}
	public void setTime10(String time10) {
		this.time10 = time10;
	}
	public String getLogisticsModel() {
		return logisticsModel;
	}
	public void setLogisticsModel(String logisticsModel) {
		this.logisticsModel = logisticsModel;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getWorkcenter() {
		return workcenter;
	}
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	public List<MonPlateFormModel> getDownPlateForms() {
		return downPlateForms;
	}
	public void setDownPlateForms(List<MonPlateFormModel> downPlateForms) {
		this.downPlateForms = downPlateForms;
	}
	public List<MonPlateFormModel> getUpPlateForms() {
		return upPlateForms;
	}
	public void setUpPlateForms(List<MonPlateFormModel> upPlateForms) {
		this.upPlateForms = upPlateForms;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInTransTime() {
		return inTransTime;
	}
	public void setInTransTime(String inTransTime) {
		this.inTransTime = inTransTime;
	}
	public String getInUnloadTime() {
		return inUnloadTime;
	}
	public void setInUnloadTime(String inUnloadTime) {
		this.inUnloadTime = inUnloadTime;
	}
	public String getInEmptyTime() {
		return inEmptyTime;
	}
	public void setInEmptyTime(String inEmptyTime) {
		this.inEmptyTime = inEmptyTime;
	}
	public String getTime11() {
		return time11;
	}
	public void setTime11(String time11) {
		this.time11 = time11;
	}
	
}
