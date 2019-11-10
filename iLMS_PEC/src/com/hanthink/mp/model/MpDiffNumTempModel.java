package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：W+1,W+2生产计划  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpDiffNumTempModel extends AbstractModel<String>{
	
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年1月2日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -2376103555719758288L;
	/**
	 * 主表 W+1,W+2生产计划表
	 * @return
	 */
	/**
	* 订单号
	*/
	private String orderNo; 
	
	/**
	* 数量
	*/
	private Integer num; 
	
	/**
	* 类型
	*/
	private String type; 
	
	/**
	* 车型
	*/
	private String modelCode;
	
	/**
	* 工厂
	*/
	private String factoryCode;
	
	/**
	* 标识
	*/
	private String flag;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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