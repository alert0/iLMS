package com.hotent.org.persistence.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
 
/**
 * @author miao
 */
public class OrgTree extends Org{
	
	public static final String ICON_COMORG ="/styles/theme/default/images/icons/u_darkblue/u_zzgl_darkblue.png";
	protected Long  sn; /*序号*/
	protected String  icon; /*图标*/
	protected boolean  nocheck = false; /***/
	protected boolean chkDisabled =false ;
	protected boolean click = true;
	protected String title = ""; //*title  默认为name 、如果name添加了 css 、则默认为 “” */  
	protected String demId = "0";
	protected Integer population; // 该部门人数包括下级部门人数
	public OrgTree() {
	}
	public OrgTree(String name,String id,String parentId,String icon){
		setName(name);
		this.parentId = parentId;
		this.id =id;
		this.icon = icon;
		
	}
	/**
	 * GroupList2TreeList
	 */
	public static List<OrgTree> GroupList2TreeList(List<Org> groupList,String icon){
		if(groupList == null || groupList.size() == 0) 
			return Collections.emptyList();
		
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		for(Org group : groupList){
			OrgTree grouptree = new OrgTree(group);
			grouptree.setIcon(icon);
			groupTreeList.add(grouptree);
		}
		return groupTreeList;
	}
	
	public OrgTree(Org group) {
		this.id =group.id;
		this.name = group.name;
		this.code = group.code;
		this.sn = group.orderNo;
		this.parentId = group.parentId;
		this.demId = group.demId;
		this.isIsParent = group.isIsParent;
		if(!this.name.contains("style=")){
			this.title = name;
		}
	}
	@Override
	public void setName(String name) {
		this.name = name; 
		// 将title 设置成name
		if("".equals(title) && !this.name.contains("style=")){
			this.title = name;
		} 
	};
	
	public String getDemId() {
		return demId;
	}
	public void setDemId(String demId) {
		this.demId = demId;
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
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	
}
