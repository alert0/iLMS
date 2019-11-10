package com.hotent.org.api.model;

import java.util.Map;

import com.hotent.base.api.BaseModel;
 

/**
 * 描述：抽象用户组类型 
 * 构建组：x5-bpmx-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-14-下午6:11:56
 * 版权：广州宏天软件有限公司版权所有
 */
public interface IGroup    extends IdentityType,BaseModel{
 
	
	
	/**
	 * 组织ID
	 * @return
	 */
	String getGroupId();
	
	/**
	 * 组织名称
	 * @return
	 */
	String getName();
	
	/**
	 * 组织编码
	 * @return
	 */
	String getGroupCode();
	
	/**
	 * 组排序
	 * @return
	 */
	Long getOrderNo();
	
	/**
	 * 组织类型。
	 * 比如：org,role,pos
	 * @return
	 */
	String getGroupType();
	
	/**
	 * 组织结构。
	 * @return
	 */
	GroupStructEnum getStruct();
	
	/**
	 * 上级ID
	 * @return
	 */
	String getParentId();
	
	//路径 例如xxx.xxxx
	String getPath();
	/**
	 * 获取参数。
	 * @return
	 */
	Map<String, Object> getParams();
}
