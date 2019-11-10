package com.hotent.org.persistence.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.GroupStructEnum;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IdentityType;
 


 /**
 * 
 * <pre> 
 * 描述：组织架构 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class Org extends AbstractModel<String> implements IGroup{
	
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* name_
	*/
	protected String name; 
	
	/**
	* prent_id_
	*/
	protected String parentId; 
	
	/**
	* code_
	*/
	protected String code; 
	
	/**
	* 级别
	*/
	protected String grade; 
	
	/**
	 * 维度Id
	 */
	protected String demId;
	
	protected Long orderNo; 
	
	/**
	 * 上级组织名称
	 */
	protected  String parentOrgName;
	
	/**
	 * 是否主组织。
	 */
	private int isMaster=0;
	
	/**
	/**
	 * 路径
	 */
	protected String path;
	
	/**
	 * 组织路径名
	 */
	protected String pathName;
	/**
	 * 是否有子节点   否0  是1  
	 */
	protected int isIsParent = 0;

	protected  String wxOrgId;

	 public String getWxOrgId() {
		 return wxOrgId;
	 }

	 public void setWxOrgId(String wxOrgId) {
		 this.wxOrgId = wxOrgId;
	 }

	 /**
	 * 用户id列表
	 * @return
	 */
	protected List<String> userList = new ArrayList<String>();
	public String getPathName() {
		return pathName;
	}

	public boolean isIsParent() {
		return isIsParent==1;
	}

	public void setIsParent(int isIsParent) {
		this.isIsParent = isIsParent;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getParentOrgName() {
		return this.parentOrgName;
	}
	
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 name_
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回 prent_id_
	 * @return
	 */
	public String getParentId() {
		return this.parentId;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDemId() {
		return demId;
	}

	public void setDemId(String demId) {
		this.demId = demId;
	}

	/**
	 * 返回 code_
	 * @return
	 */
	public String getCode() {
		return this.code;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**
	 * 返回 级别
	 * @return
	 */
	public String getGrade() {
		return this.grade;
	}
	
	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("parentId", this.parentId) 
		.append("code", this.code) 
		.append("grade", this.grade)
		.append("demId",this.demId)
		
		.append("path",this.path)
		.append("pathName",this.pathName)
		.toString();
	}

	public String getIdentityType() {
		return IdentityType.GROUP;
	}

	public String getGroupId() {
		return this.id;
	}

	public String getGroupCode() {
		return this.code;
	}

	public Long getOrderNo() {
		 
		return this.orderNo;
	}

	public String getGroupType() {
		return GroupTypeConstant.ORG.key();
	}

	public GroupStructEnum getStruct() {
		return GroupStructEnum.TREE;
	}

	public String getPath() {
		return this.path;
	}

	public Map<String, Object> getParams() {
		return null;
	}

	public int getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}
	
	
}