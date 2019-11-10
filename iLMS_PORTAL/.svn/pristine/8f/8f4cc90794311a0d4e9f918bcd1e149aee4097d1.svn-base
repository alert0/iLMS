package com.hotent.form.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormRightDao;
import com.hotent.form.persistence.model.BpmFormRight;

/**
 * 
 * <pre> 
 * 描述：BPM_FORM_RIGHT DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-19 14:22:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmFormRightDaoImpl extends MyBatisDaoImpl<String, BpmFormRight> implements BpmFormRightDao{

    @Override
    public String getNamespace() {
        return BpmFormRight.class.getName();
    }
	
    
    /**
	 * 根据流程定义Id获取权限配置数据。
	 * @param actDefId			流程定义KEY
	 * @param parentFlowKey		父级流程定义KEY
	 * @return
	 */
	public BpmFormRight getByFlowKey(String flowKey,String parentFlowKey,int permissionType) {
		
		MapBuilder mb= buildMapBuilder();
		mb.addParam("flowKey", flowKey)
		.addParam("permissionType", permissionType);
		if(StringUtil.isNotEmpty(parentFlowKey)){
			mb.addParam("parentFlowKey", parentFlowKey);
		}
		
		return this.getUnique("getByFlowKey", mb.getParams());
		
	}
	
	
	/**
	 * 根据流程定义ID节点ID 和父流程定义ID获取权限配置数据。
	 * @param flowKey
	 * @param nodeId
	 * @param parentFlowKey
	 * @param permissionType
	 * @return
	 */
	public BpmFormRight getByFlowNodeId(String flowKey,String nodeId, String parentFlowKey, int permissionType){
		MapBuilder mb= buildMapBuilder();
		mb.addParam("flowKey", flowKey)
		.addParam("nodeId", nodeId)
		.addParam("permissionType", permissionType);
		if(StringUtil.isNotEmpty(parentFlowKey)){
			mb.addParam("parentFlowKey", parentFlowKey);
		}
		return this.getUnique("getByFlowNodeId", mb.getParams());
	}


	@Override
	public void removeByFlowNode(String flowKey, String nodeId, String parentFlowKey) {
		MapBuilder mb= buildMapBuilder();
		mb.addParam("flowKey", flowKey)
		.addParam("nodeId", nodeId);
		if(StringUtil.isNotEmpty(parentFlowKey)){
			mb.addParam("parentFlowKey", parentFlowKey);
		}
		this.deleteByKey("removeByFlowNode", mb.getParams());
		
	}


	@Override
	public void removeByFlowKey(String flowKey, String parentFlowKey, int permissionType) {
		MapBuilder mb= buildMapBuilder();
		mb.addParam("flowKey", flowKey)
		.addParam("permissionType", permissionType);
		if(StringUtil.isNotEmpty(parentFlowKey)){
			mb.addParam("parentFlowKey", parentFlowKey);
		}
		this.deleteByKey("removeByFlowKey", mb.getParams());
	}


	@Override
	public BpmFormRight getByFormKey(String formKey,boolean readOnly) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formKey", formKey);
		map.put("type", readOnly ? "2":"1");
		
		return this.getUnique("getByFormKey",map);
	}


	@Override
	public void removeByFormKey(String formKey) {
		this.deleteByKey("removeByFormKey", formKey);
	}


	@Override
	public List<BpmFormRight> getByFlowKey(String flowKey) {
		return this.getByKey("getAllByFlowKey", flowKey);
	}

	
}

