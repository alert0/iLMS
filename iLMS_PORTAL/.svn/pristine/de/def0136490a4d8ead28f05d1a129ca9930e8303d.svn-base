package com.hotent.bpmx.persistence.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.transaction.TransactionException;

import com.hotent.base.api.query.FieldRelation;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.persistence.model.BpmDefData;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.query.BpmDefFieldSorts;
import com.hotent.bpmx.persistence.model.query.BpmDefQueryFields;
import com.hotent.org.api.model.IUser;

public interface BpmDefinitionManager extends Manager<String, DefaultBpmDefinition> {
	/**
	 * 
	 * 查询流程定义实体，并查询它的一对一子表的流程定义数据
	 * 
	 * @param entityId
	 * @return DefaultBpmDefinition
	 */
	DefaultBpmDefinition getById(String entityId);

	/**
	 * 
	 * 根据流程业务主键查询和该主键一致的流程定义（该定义记录为主版本）（默认不需要设置的数据）
	 * 
	 * @param defKey
	 * @return DefaultBpmDefinition
	 */
	DefaultBpmDefinition getMainByDefKey(String defKey);
	/**
	 * 
	 * 根据流程业务主键查询和该主键一致的流程定义（该定义记录为主版本）
	 * 
	 * @param defKey
	 * @param needData 是否需要设置的数据
	 * @return DefaultBpmDefinition
	 */
	DefaultBpmDefinition getMainByDefKey(String defKey, boolean needData);

	/**
	 * 根据流程主键查询所有流程定义
	 * 
	 * @param defKey
	 * @return List<DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryByDefKey(String defKey);

	/**
	 * 根据流程创建人查询所有自己创建的流程定义
	 * 
	 * @param userId
	 * @return List<DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryByCreateBy(String userId);

	/**
	 * 根据流程主键查询所有历史流程定义
	 * 
	 * @param defKey
	 * @return List<DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryHistorys(String defKey);

	/**
	 * 对传入的流程定义克隆一份，将新产生的流程定义设置为主版本，并更新其它版本的流程定义为历史版本 （传入的流程定义可以是主版本，也可以是历史版本）
	 * 
	 * @param bpmDefinition
	 * @return DefaultBpmDefinition
	 */
	DefaultBpmDefinition cloneToMain(DefaultBpmDefinition bpmDefinition);

	/**
	 * 根据流程主键获得最大的版本号
	 * 
	 * @param defKey
	 * @return
	 */
	Integer getMaxVersion(String defKey);

	/**
	 * 根据查询条件、条件关系、分页对象（其中包含排序）进行流程定义的查询。（这个方法支持分页）
	 * 单元测试类见BpmDefinitionManagerTest
	 * 
	 * @param bpmDefQueryFields
	 *            查询条件
	 * @param fieldRelation
	 *            条件关系
	 * @param page
	 *            分页对象
	 * @return
	 */
	List<DefaultBpmDefinition> query(BpmDefQueryFields bpmDefQueryFields,
			FieldRelation fieldRelation, DefaultPage page);

	/**
	 * 根据查询条件、条件关系、排序条件进行流程定义的查询。（这个方法不支持分页）
	 * 
	 * @param bpmDefQueryFields
	 *            查询条件
	 * @param fieldRelation
	 *            条件关系
	 * @param bpmDefFieldSorts
	 *            分页对象
	 * @return
	 */
	List<DefaultBpmDefinition> query(BpmDefQueryFields bpmDefQueryFields,
			FieldRelation fieldRelation, BpmDefFieldSorts bpmDefFieldSorts);

	/**
	 * 根据查询条件进行流程定义的查询（包含分管授权）。
	 * 
	 * @param queryFilter
	 *            查询条件
	 * @return 流程定义列表
	 */
	List<DefaultBpmDefinition> queryList(QueryFilter queryFilter);

	List<DefaultBpmDefinition> queryList(QueryFilter queryFilter,IUser user);
	
	/**
	 * 级联删除流程，包括历史数据、流程实例、任务实例、流程节点配置、流程本身
	 * 
	 * @param entityId
	 *            流程定义主键ID
	 */
	void removeCascade(String entityId);

	/**
	 * 按bpmn定义ID获取流程定义
	 * 
	 * @param bpmnDefId
	 * @return defId
	 */
	String getDefIdByBpmnDefId(String bpmnDefId);

	/**
	 * 将流程更新到主版本。
	 * 
	 * @param entityId
	 *            void
	 */
	void updMainVersion(String entityId);

	/**
	 * 通过BPMN的流程定义ID获取流程定义实体
	 * 
	 * @param bpmnDefId
	 * @return
	 */
	DefaultBpmDefinition getByBpmnDefId(String bpmnDefId);

	/**
	 * 通过BPMN的流程定义发布ID获取流程定义实体
	 * 
	 * @param bpmnDeployId
	 * @return
	 */
	DefaultBpmDefinition getByBpmnDeployId(String bpmnDeployId);

	/**
	 * 更新流程定义的XML
	 * 
	 * @param defId
	 * @param defXml
	 * @return String 返回转化后后的BPMN XML
	 * @throws Exception 
	 */
	String updateBpmDefXml(String defId, String defXml) throws Exception;

	/**
	 * 更新流程状态
	 * 
	 * @param defId
	 * @param status
	 *            void
	 */
	void updateStatus(String defId, String status);

	/**
	 * 根据流程定义ID更新流程的定义XML。
	 * 
	 * @param defId
	 * @param bpmnXml
	 *            void
	 */
	void updBpmData(String defId, BpmDefData bpmDefData);

	/**
	 * 发布流程定义。
	 * 
	 * @param bpmDefinition
	 *            void
	 * @throws Exception 
	 */
	boolean deploy(BpmDefinition bpmDefinition) throws Exception;

	/**
	 * 更新流程定义。
	 * @param bpmDefinition
	 * @return boolean
	 * @throws Exception 
	 */
	boolean updateBpmDefinition(BpmDefinition bpmDefinition) throws Exception;
	
	
	/**
	 * 更新流程分类。
	 * @param typeId		流程分类
	 * @param defList 		流程定义列表
	 * void
	 */
	void updDefType(String typeId,List<String> defList);

	/**
	 * 保存草稿。
	 * 
	 * @param bpmDefinition
	 * @return boolean
	 * @throws Exception 
	 */
	boolean saveDraft(BpmDefinition bpmDefinition) throws Exception;
	
	
	/**
	 * 根据map查询查询流程定义列表。
	 * @param map
	 * @return  List&lt;DefaultBpmDefinition>
	 */
	List<DefaultBpmDefinition> queryListByMap(Map<String,Object> map);
	
	
	/**
	 * 根据流程定义ID删除。
	 * @param defId 
	 * void
	 */
	void removeDefId(String defId);

	
	/**
	 * 根据流程定义ID删除相关的记录，不包括这个流程的其他版本数据。
	 * @param defIds 
	 * void
	 */
	void removeDefIds(String ...defIds);

	void copyDef(String defId, String name, String defKey);
	
	/**
	 * 修改流程定义状态。
	 * 
	 * @param bpmDefinition
	 * @param oldStatus
	 *            void
	 * @throws Exception 
	 */
	void updBpmDefinitionStatus(DefaultBpmDefinition bpmDefinition, String oldStatus) throws Exception;
	

}
