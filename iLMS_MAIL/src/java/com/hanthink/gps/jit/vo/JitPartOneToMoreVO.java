/**
 * 
 */
package com.hanthink.gps.jit.vo;

import java.io.Serializable;

/**
 * @author chenyong
 * date    2016-09-20
 * 厂外拉动一点零件多个供应商VO
 *
 */
public class JitPartOneToMoreVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String factory;  //工厂
	private String partNo;   //零件号
	private String supplierList; //供应商
	private String workcenter; //工作中心
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(String supplierList) {
		this.supplierList = supplierList;
	}
	public String getWorkcenter() {
		return workcenter;
	}
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	
	

}
