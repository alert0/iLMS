package com.hotent.sys.api.msg.model;

/**
 * 系统用户对象。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-10-30-下午5:26:50
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SysExecutor {
	

	public static String TYPE_USER="user";
	public static String TYPE_GROUP="group";
	
	public SysExecutor(){
		
	}
	
	public SysExecutor(String id_,String name,String type){
		this.id=id_;
		this.name=name;
		this.type=type;
	}
	
	
	/**
	 * 对象ID
	 */
	private String id="";
	/**
	 * 对象名称
	 */
	private String name="";
	/**
	 * 对象类型
	 */
	private String type="";
	/**
	 * 组类型，比如 org,role等。
	 */
	private String groupType="";
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}
