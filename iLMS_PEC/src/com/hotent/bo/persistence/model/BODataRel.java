package com.hotent.bo.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：BO数据关联表，用于多对多的情况。 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-02 11:27:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BODataRel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4633816793042530729L;

	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 主表键数据
	*/
	protected String pk; 
	
	/**
	* 外键数据
	*/
	protected String fk; 
	
	/**
	* 数字主键
	*/
	protected Long pkNum; 
	
	/**
	* 外键数字数据
	*/
	protected Long fkNum; 
	
	/**
	* 子实体名称
	*/
	protected String subBoName; 
	
	
	public  BODataRel(){}
	
	public  BODataRel(String id,String pk,String fk,String subBoName){
		this.id=id;
		this.pk=pk;
		this.fk=fk;
		this.subBoName=subBoName;
	}
	
	public  BODataRel(String id,Long pk,Long fk,String subBoName){
		this.id=id;
		this.pkNum=pk;
		this.fkNum=fk;
		this.subBoName=subBoName;
	}
	
	
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
	
	public void setPk(String pk) {
		this.pk = pk;
	}
	
	/**
	 * 返回 主表键数据
	 * @return
	 */
	public String getPk() {
		return this.pk;
	}
	
	public void setFk(String fk) {
		this.fk = fk;
	}
	
	/**
	 * 返回 外键数据
	 * @return
	 */
	public String getFk() {
		return this.fk;
	}
	
	public void setPkNum(Long pkNum) {
		this.pkNum = pkNum;
	}
	
	/**
	 * 返回 数字主键
	 * @return
	 */
	public Long getPkNum() {
		return this.pkNum;
	}
	
	public void setFkNum(Long fkNum) {
		this.fkNum = fkNum;
	}
	
	/**
	 * 返回 外键数字数据
	 * @return
	 */
	public Long getFkNum() {
		return this.fkNum;
	}
	
	public void setSubBoName(String subBoName) {
		this.subBoName = subBoName;
	}
	
	/**
	 * 返回 子实体名称
	 * @return
	 */
	public String getSubBoName() {
		return this.subBoName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("pk", this.pk) 
		.append("fk", this.fk) 
		.append("pkNum", this.pkNum) 
		.append("fkNum", this.fkNum) 
		.append("subBoName", this.subBoName) 
		.toString();
	}
}