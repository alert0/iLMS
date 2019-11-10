/**
 * 
 */
package com.hanthink.gps.system.vo;

import java.io.Serializable;

/**
 * 描述：统计表空间信息VO
 * @author chenyong
 * @date  2016-10-10
 *
 */
public class CheckTableSpaceVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String factory;          //工厂
	private String tableSpaceName;   //表空间名称
	private String totalSpace;       //表空间总量
	private String useSpace;         //已使用空间大小
	private String restSpace;        //剩余空间大小
	private String percent;          //使用百分比
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getTableSpaceName() {
		return tableSpaceName;
	}
	public void setTableSpaceName(String tableSpaceName) {
		this.tableSpaceName = tableSpaceName;
	}
	public String getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}
	public String getUseSpace() {
		return useSpace;
	}
	public void setUseSpace(String useSpace) {
		this.useSpace = useSpace;
	}
	public String getRestSpace() {
		return restSpace;
	}
	public void setRestSpace(String restSpace) {
		this.restSpace = restSpace;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
    
}
