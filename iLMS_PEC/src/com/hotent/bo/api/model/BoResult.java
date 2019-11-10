package com.hotent.bo.api.model;

/**
 * bo数据保存后的结果对象数据。
 * @author ray
 *
 */
public class BoResult {
	
	public final static class ACTION_TYPE {
		/**
		 * 添加
		 */
		public final static String ADD = "add";
		/**
		 * 更新
		 */
		public final static String UPDATE = "upd";
		
		/**
		 * 删除
		 */
		public final static String DELETE = "del";
	}
	
	/**
	 * 主键
	 */
	private String pk="";
	
	
	
	private String parentId="0";
	
	/**
	 * 增加，修改，删除
	 * add,upd,del
	 */
	private String action="";
	
	/**
	 * BO定义别名。
	 */
	private String boCode="";
	
	/**
	 * 对应的bo实体。
	 */
	private BaseBoEnt boEnt=null;
	
	
	public String getAction() {
		return action;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getEntName() {
		return  boEnt.getName();
	}
	
	public String getTableName() {
		return  boEnt.getTableName();
	}
	
	public BaseBoEnt getBoEnt() {
		return boEnt;
	}
	public void setBoEnt(BaseBoEnt boEnt) {
		this.boEnt = boEnt;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBoCode() {
		return boCode;
	}
	public void setBoCode(String boCode) {
		this.boCode = boCode;
	}
	
}
