package com.hotent.sys.persistence.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
 
public class TreeEntity{
	
	public static final String ICON_COMORG ="";
	protected Long  sn; /*序号*/
	protected String  icon; /*图标*/
	protected  String  name;
	protected  String  parentId;
	protected  String  id;
	protected boolean  nocheck = false; /***/
	protected boolean chkDisabled =false ;
	protected boolean click = true;
	protected String title = ""; //*title  默认为name 、如果name添加了 css 、则默认为 “” */  
	public TreeEntity() {
	}
	public TreeEntity(String name,String title,String id,String parentId,String icon){
		setName(name);
		this.parentId = parentId;
		this.id =id;
		this.icon = icon;
		this.title =title;
	}
 
	public void setName(String name) {
		this.name = name; 
	};
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id; 
	}
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId; 
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title; 
	}

	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	
}
