package com.hanthink.gps.pub.vo;

public class ChkItemVO {
	
	private String id;
	private String name;
	private String checked;
	
	public ChkItemVO(String id,String name,String checked){
		this.id = id;
		this.name = name;
		this.checked = checked;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
