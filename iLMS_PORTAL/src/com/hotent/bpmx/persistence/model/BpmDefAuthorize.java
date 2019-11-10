package com.hotent.bpmx.persistence.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.hotent.base.core.model.AbstractModel;


/**
 * 对象功能:流程授权主表明细 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 09:00:53
 */

public class BpmDefAuthorize extends  AbstractModel<String>{
	
	// id
	protected String id="";
	
	//流程授权类型  : start,management,task,instance
	protected String authorizeTypes="";
	
	//流程授权说明
	protected String authorizeDesc="";
	
	//授权类型列表
	protected List<BpmDefAuthorizeType> bpmDefAuthorizeTypeList = new ArrayList<BpmDefAuthorizeType>();

	//授权对象列表
	protected List<BpmDefUser> bpmDefUserList = new ArrayList<BpmDefUser>();
	
	//授权流程列表
	protected List<BpmDefAct> bpmDefActList = new ArrayList<BpmDefAct>();
	
	//授权对象名称(仅用于查询)
	protected String ownerName;
	
	//授权流程名称(仅用于查询)
	protected String defName;
	
	
	/*
	 * [{type:"everyone"},{"type":"user",id:"",name:""}]
	 * 
  	*/
	protected String ownerNameJson="[]";
	
	//授权流程名称(仅用于存放授权流程的JSON数据)
	
  /* 
   * 	  [{ "defId":"10000018130014",
   *         "defKey":"zchz",
   *         "defName":"周程汇总",
   *         "right":{"edit":"N","del":"N","start":"N","set":"N"}
   *       },
   *       {"defId":"10000017980009",
   *        "defKey":"csjdsz",
   *        "defName":"测试节点设置",
   *        "right":{"edit":"N","del":"N","start":"N","set":"N"}
   *       },
   *       {"defId":"10000017860008",
   *        "defKey":"gxzlc",
   *        "defName":"共享子流程",
   *        "right":{"edit":"N","del":"N","start":"N","set":"N"}}]
  */	
	protected String defNameJson="[]";

//	@JSONField(serialize=true)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

//	@JSONField(serialize=true)
	public String getAuthorizeTypes()
	{
		return authorizeTypes;
	}

	public void setAuthorizeTypes(String authorizeTypes)
	{
		this.authorizeTypes = authorizeTypes;
	}

//	@JSONField(serialize=true)
	public String getAuthorizeDesc()
	{
		return authorizeDesc;
	}

	public void setAuthorizeDesc(String authorizeDesc)
	{
		this.authorizeDesc = authorizeDesc;
	}

	@JSONField(serialize=false)
	public List<BpmDefAuthorizeType> getBpmDefAuthorizeTypeList()
	{
		return bpmDefAuthorizeTypeList;
	}

	public void setBpmDefAuthorizeTypeList(List<BpmDefAuthorizeType> bpmDefAuthorizeTypeList){
		this.bpmDefAuthorizeTypeList = bpmDefAuthorizeTypeList;
		this.authorizeTypes = "";
		if(this.bpmDefAuthorizeTypeList.size()>0){
			JSONObject json=new JSONObject();
			for (BpmDefAuthorizeType bpmDefAuthorizeType : bpmDefAuthorizeTypeList){
				json.put(bpmDefAuthorizeType.getAuthorizeType(),true);
			}
			this.authorizeTypes =json.toJSONString();
		}
	}

	@JSONField(serialize=false)
	public List<BpmDefUser> getBpmDefUserList()
	{
		return bpmDefUserList;
	}

	public void setBpmDefUserList(List<BpmDefUser> bpmDefUserList)
	{
		this.bpmDefUserList = bpmDefUserList;
	}

	@JSONField(serialize=false)
	public List<BpmDefAct> getBpmDefActList()
	{
		return bpmDefActList;
	}

	public void setBpmDefActList(List<BpmDefAct> bpmDefActList)
	{
		this.bpmDefActList = bpmDefActList;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	public String getDefName()
	{
		return defName;
	}

	public void setDefName(String defName)
	{
		this.defName = defName;
	}

	public String getOwnerNameJson()
	{
		return ownerNameJson;
	}

	public void setOwnerNameJson(String ownerNameJson)
	{
		this.ownerNameJson = ownerNameJson;
	}

	public String getDefNameJson()
	{
		return defNameJson;
	}

	public void setDefNameJson(String defNameJson)
	{
		this.defNameJson = defNameJson;
	}
	
}