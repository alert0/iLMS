package com.hotent.bo.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * BO定义的和实体的关系。
 * <pre> 
 * 描述：BO定义的和实体的关系。
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-02 11:19:35
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BOEntRel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2325631939274387702L;

	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* BO定义ID
	*/
	protected String boDefid; 
	
	/**
	* 上级ID
	*/
	protected String parentId; 
	
	/**
	* 引用的BODEFID
	*/
	protected String refEntId; 
	
	/**
	 * 关联的bo 实体类。
	 */
	protected BOEnt refEnt;
	
	
	/**
	* 类型(main,onetoone,onetomany,manytomany)
	*/
	protected String type; 
	
	
	
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setBoDefid(String boDefid) {
		this.boDefid = boDefid;
	}
	
	/**
	 * 返回 BO定义ID
	 * @return
	 */
	public String getBoDefid() {
		return this.boDefid;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回 上级ID
	 * @return
	 */
	public String getParentId() {
		return this.parentId;
	}
	
	public void setRefEntId(String refEntId) {
		this.refEntId = refEntId;
	}
	
	/**
	 * 返回 引用的BODEFID
	 * @return
	 */
	public String getRefEntId() {
		return this.refEntId;
	}
	
	
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型(main,onetoone,onetomany,manytomany)
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * 关联实体。
	 * @return
	 */
	public BOEnt getRefEnt() {
		return refEnt;
	}

	public void setRefEnt(BOEnt refEnt) {
		this.refEnt = refEnt;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("boDefid", this.boDefid) 
		.append("parentId", this.parentId) 
		.append("refEntId", this.refEntId) 
		.append("type", this.type) 
		.toString();
	}
}