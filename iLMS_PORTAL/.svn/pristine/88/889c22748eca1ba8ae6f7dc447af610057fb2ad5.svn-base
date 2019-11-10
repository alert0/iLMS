package com.hotent.form.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmFormRight;

/**
 * 
 * <pre> 
 * 描述：BPM_FORM_RIGHT DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-19 14:22:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmFormRightDao extends Dao<String, BpmFormRight> {
	
	/**
	 * 根据流程定义Id获取权限配置数据。
	 * @param actDefId			流程定义KEY
	 * @param parentFlowKey		父级流程定义KEY
	 * @return
	 */
	BpmFormRight getByFlowKey(String flowKey,String parentFlowKey,int permissionType) ;
	
	
	/**
	 * 根据流程定义ID节点ID 和父流程定义ID获取权限配置数据。
	 * @param flowKey
	 * @param nodeId
	 * @param parentFlowKey
	 * @param permissionType
	 * @return
	 */
	BpmFormRight getByFlowNodeId(String flowKey,String nodeId, String parentFlowKey, int permissionType);
	
	/**
	 * 根据表单的Key获取表单配置的基础权限。
	 * @param formKey
	 * flowKey 为null 的数据
	 * @param isReadOnly 是否只读权限
	 * @return
	 */
	BpmFormRight getByFormKey(String formKey, boolean isReadOnly);
	
	/**
	 * 根据流程key和流程节点删除权限。
	 * @param flowKey
	 * @param nodeId
	 * @param parentFlowKey
	 */
	void removeByFlowNode(String flowKey,String nodeId, String parentFlowKey);
	
	/**
	 * 根据流程key进行删除。
	 * @param flowKey
	 * @param parentFlowKey
	 * @param permissionType
	 */
	void removeByFlowKey(String flowKey, String parentFlowKey,int permissionType);

	
	/**
	 * 根据formKey删除表单权限。
	 * @param formKey
	 */
	void removeByFormKey(String formKey);


	List<BpmFormRight> getByFlowKey(String flowKey);

}
