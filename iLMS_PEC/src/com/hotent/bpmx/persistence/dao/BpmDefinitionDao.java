package com.hotent.bpmx.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;

/**
 * 
 * <pre> 
 * 描述：流程定义
 * 构建组：x5-bpmx-core
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-12-23-上午8:41:25
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface BpmDefinitionDao extends Dao<String, DefaultBpmDefinition> {
	
	/**
	 * 根据流程主键将流程更新为主版本。
	 * @param mainDefId
	 * @param defKey 
	 * void
	 */
	void updateToMain(String defId);
	/**
	 * 根据流程业务主键查询和该主键一致的流程定义（该定义记录为主版本）
	 * @param defKey
	 * @return 
	 * DefaultBpmDefinition
	 */
	DefaultBpmDefinition getMainByDefKey(String defKey);
	
	/**
	 * 获得流程业务主键相同的最大版本号记录
	 * @param defKey
	 * @return 
	 * DefaultBpmDefinition
	 */
	Integer getMaxVersion(String defKey);
	
	/**
	 * 根据流程业务主键查询流程定义记录
	 * @param defKey
	 * @return 
	 * List<DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryByDefKey(String defKey);
	
	/**
	 * 根据流程创建人查询所有自己创建的流程定义
	 * @param userId
	 * @return 
	 * List<DefaultBpmDefinition>
	 */
	public List<DefaultBpmDefinition> queryByCreateBy(String userId);
	
	/**
	 * 根据流程业务主键查询流程定义历史记录
	 * @param defKey
	 * @return 
	 * List<DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryHistorys(String defKey);
	
	/**
	 * 按BpmnDefID获取流程定义
	 * @param bpmnDefId
	 * @return 
	 * DefaultBpmDefinition
	 */
	String getDefIdByBpmnDefId(String bpmnDefId);
	/**
	 * 通过BPMN的流程定义ID获取流程定义实体
	 * @param bpmnDefId
	 * @return
	 */
	DefaultBpmDefinition getByBpmnDefId(String bpmnDefId);
	/**
	 * 通过BPMN的流程定义发布获取流程定义实体
	 * @param bpmnDeployId
	 * @return
	 */
	DefaultBpmDefinition getByBpmnDeployId(String bpmnDeployId);
	
	/**
	 * 更新状态
	 * @param defId
	 * @param status 
	 * void
	 */
	void updateStatus(String defId,String status);
	
	/**
	 * 根据KEY获取流程定义列表。 
	 * @param defKey
	 * @return 
	 * List&lt;DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> getByDefKey(String defKey);
	
	/**
	 * 根据流程key删除流程定义。
	 */
	void delByKey(String defKey);
	
	/**
	 * 根据流程定义key删除。
	 * act_re_deployment
	 * @param defKey 
	 * void
	 */
	void delActDeploy(String defKey);
	
	/**
	 * 根据流程定义删除。
	 * act_ge_bytearray
	 * @param defKey 
	 * void
	 */
	void delActByteArray(String defKey);
	
	/**
	 * 根据流程定义删除。
	 * act_re_procdef。
	 * @param defKey 
	 * void
	 */
	void delActDef(String defKey);
	
	
	/**
	 * 根据map获取流程定义。
	 * @param map
	 * @return  List&lt;DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryListByMap(Map<String,Object> map);
	
	
	/**
	 * 更新流程定义分类。
	 * @param typeId	分类ID
	 * @param defList 	流程定义列表
	 * void
	 */
	void updDefType(String typeId,List<String> defList);
	
	
	/**
	 * 根据流程定义id删除 act_re_deployment表记录。
	 * @param defId 
	 * void
	 */
	void delActDeployByDefId(String defId);
	
	
	/**
	 * 根据流程定义id删除 act_re_procdef表记录。
	 * @param defId 
	 * void
	 */
	void delActDefByDefId(String defId);
	
	/**
	 * 根据流程定义id删除 act_ge_bytearray表记录。
	 * @param defId 
	 * void
	 */
	void delActByteArrayByDefId(String defId);
	
	
	/**
	 * 根据流程bpmnDefId删除。
	 * act_ru_task。
	 * @param defId 
	 * void
	 */
	void delActTask(String defId);
	
	
}
