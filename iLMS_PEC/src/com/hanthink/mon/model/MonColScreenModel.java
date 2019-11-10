package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class MonColScreenModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 3332431738490641966L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private String factoryCode;
	private String workCenter;
	private String runOee;
	private String stopOee;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getRunOee() {
		return runOee;
	}
	public void setRunOee(String runOee) {
		this.runOee = runOee;
	}
	public String getStopOee() {
		return stopOee;
	}
	public void setStopOee(String stopOee) {
		this.stopOee = stopOee;
	}
}
