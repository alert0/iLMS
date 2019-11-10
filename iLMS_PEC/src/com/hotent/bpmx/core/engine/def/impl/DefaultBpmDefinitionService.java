package com.hotent.bpmx.core.engine.def.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.transaction.TransactionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldRelation;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.model.DefaultFieldLogic;
import com.hotent.base.db.model.DefaultQueryField;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.exception.ProcessDefException;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.core.engine.def.DefXmlTransForm;
import com.hotent.bpmx.natapi.def.NatProDefinitionService;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmInstFormManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.query.BpmDefFieldSorts;
import com.hotent.bpmx.persistence.model.query.BpmDefQueryFields;
@Service
public class DefaultBpmDefinitionService implements BpmDefinitionService {
	private final Log logger = LogFactory.getLog(getClass());

	// 注入Actviti原生的流程定义服务
	@Resource(name = "proDefinitionServiceImpl")
	NatProDefinitionService natProDefinitionService;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	DefXmlTransForm defXmlTransForm;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmBusLinkManager bpmBusLinkManager;
//	@Resource
//	BoDataHandler boDataHandler; 
	@Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	BpmInstFormManager bpmInstFromManager;
	
	@Override
	public boolean deploy(BpmDefinition bpmDefinition) throws Exception {
		return bpmDefinitionManager.deploy(bpmDefinition);
	}

	@Override
	public boolean saveDraft(BpmDefinition bpmDefinition) throws Exception {
		return bpmDefinitionManager.saveDraft(bpmDefinition);
	}

	@Override
	public BpmNodeDef getBpmNodeDef(String bpmnDefId, String nodeId) {
		String defId = bpmDefinitionManager.getDefIdByBpmnDefId(bpmnDefId);
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		return bpmNodeDef;
	}

	/**
	 * 取得流程定义中的某个节点定义
	 * 
	 * @param procDefId
	 *            X5中的流程定义ID
	 * @param nodeId
	 * @return BpmNodeDef
	 */
	public BpmNodeDef getBpmNodeDefByDefIdNodeId(String procDefId, String nodeId) {
		return bpmDefinitionAccessor.getBpmNodeDef(procDefId, nodeId);
	}

	@Override
	public BpmNodeDef getStartBpmNodeDef(String defId) {
		List<BpmNodeDef> bpmNodeDefs = getAllBpmNodeDefs(defId);
		return bpmNodeDefs.size() > 0 ? bpmNodeDefs.get(0) : null;
	}

	@Override
	public List<BpmNodeDef> getAllBpmNodeDefs(String defId) {
		return bpmDefinitionAccessor.getNodeDefs(defId);
	}

	@Override
	public List<BpmNodeDef> getEndNode(String defId) {
		List<BpmNodeDef> endNodeDefs = new ArrayList<BpmNodeDef>();
		List<BpmNodeDef> bpmNodeDefs = getAllBpmNodeDefs(defId);
		for (BpmNodeDef bpmNodeDef : bpmNodeDefs) {
			if (bpmNodeDef.getOutcomeNodes().size() == 0) {
				endNodeDefs.add(bpmNodeDef);
			}
		}
		return endNodeDefs;
	}

	@Override
	public boolean removeBpmDefinition(String defId) {
		bpmDefinitionManager.removeCascade(defId);
		return true;
	}

	@Override
	public boolean disabledBpmDefinition(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		defaultBpmDefinition.setStatus(BpmDefinition.STATUS.FORBIDDEN);
		bpmDefinitionManager.update(defaultBpmDefinition);

		return true;
	}

	@Override
	public boolean enabledBpmDefinition(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		defaultBpmDefinition.setStatus(BpmDefinition.STATUS.DEPLOY);
		bpmDefinitionManager.update(defaultBpmDefinition);
		// 恢复流程实例。
		bpmProcessInstanceManager.updForbiddenByDefKey(defaultBpmDefinition.getDefKey(), BpmProcessInstance.FORBIDDEN_NO);
		return true;
	}

	@Override
	public boolean updateBpmDefinition(BpmDefinition bpmDefinition) throws Exception {
		return bpmDefinitionManager.updateBpmDefinition(bpmDefinition);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BpmDefinition> getAllVersions(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.queryByDefKey(defaultBpmDefinition.getDefKey());
		return (List)defaultBpmDefinitions;
	}

	@Override
	public List<BpmDefinition> getAllHistoryVersions(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.queryHistorys(defaultBpmDefinition.getDefKey());
		return convertBpmDefinitions(defaultBpmDefinitions);
	}

	@Override
	public List<BpmDefinition> getAll(QueryFilter queryFilter) {
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.query(queryFilter);
		return convertBpmDefinitions(defaultBpmDefinitions);
	}

	@Override
	public boolean hasExternalSubprocess(String defId) {
		List<BpmNodeDef> list = bpmDefinitionAccessor.getNodeDefs(defId);
		for (BpmNodeDef nodeDef : list) {
			if (NodeType.CALLACTIVITY.equals(nodeDef.getType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDesignFile(String defId) {
		if (StringUtils.isEmpty(defId))
			return "";

		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		if (defaultBpmDefinition != null) {
			return defaultBpmDefinition.getDefXml();
		}
		return "";
	}

	@Override
	public String getBpmnFile(String defId) {
		if (StringUtils.isEmpty(defId)) {
			return "";
		}
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		if (defaultBpmDefinition != null) {
			return defaultBpmDefinition.getDefXml();
		}
		return "";
	}

	@Override
	public List<BpmDefinition> queryList(QueryFilter query) {
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.queryList(query);
		return (List)defaultBpmDefinitions;
	}


	@Override
	public boolean isDefCodeExist(String defCode) {
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.queryByDefKey(defCode);
		if (defaultBpmDefinitions.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateTreeType(String defId, String typeId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		if (defaultBpmDefinition != null) {
			defaultBpmDefinition.setTypeId(typeId);
			bpmDefinitionManager.update(defaultBpmDefinition);
			return true;
		}
		return false;
	}

	@Override
	public List<BpmDefinition> getProcessDefinitionByUserId(String userId) {
		BpmDefQueryFields bpmDefQueryFields = new BpmDefQueryFields();
		bpmDefQueryFields.addCreateBy(userId);
		BpmDefFieldSorts bpmDefFieldSorts = new BpmDefFieldSorts();
		bpmDefFieldSorts.addDefId();
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.query(bpmDefQueryFields, FieldRelation.AND, bpmDefFieldSorts);
		return convertBpmDefinitions(defaultBpmDefinitions);
	}

	@Override
	public List<BpmDefinition> getProcessDefinitionByUserId(String userId, QueryFilter queryFilter) {
		if (StringUtils.isEmpty(userId) || queryFilter == null) {
			return new ArrayList<BpmDefinition>();
		}
		DefaultQueryFilter defaultQueryFilter = (DefaultQueryFilter) queryFilter;
		if (defaultQueryFilter.getFieldLogic() == null || defaultQueryFilter.getFieldLogic().getWhereClauses().size() == 0) {
			FieldLogic andFieldLogic = new DefaultFieldLogic(FieldRelation.AND);
			defaultQueryFilter.setFieldLogic(andFieldLogic);
		}
		defaultQueryFilter.getFieldLogic().getWhereClauses().add(new DefaultQueryField("CREATE_BY_", QueryOP.EQUAL, userId));
		List<DefaultBpmDefinition> defaultBpmDefinitions = bpmDefinitionManager.query(defaultQueryFilter);
		return convertBpmDefinitions(defaultBpmDefinitions);
	}

	@Override
	public BpmDefinition getBpmDefinitionByDefId(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.getById(defId);
		return (BpmDefinition) defaultBpmDefinition;
	}

	@Override
	public BpmDefinition getBpmDefinitionByDefKey(String defKey, boolean needData) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey, needData);
		return (BpmDefinition) defaultBpmDefinition;
	}

	private List<BpmDefinition> convertBpmDefinitions(List<DefaultBpmDefinition> defaultBpmDefinitions) {
		List<BpmDefinition> bpmDefinitions = new ArrayList<BpmDefinition>();
		for (DefaultBpmDefinition _defaultBpmDefinition : defaultBpmDefinitions) {
			bpmDefinitions.add((BpmDefinition) _defaultBpmDefinition);
		}
		return bpmDefinitions;
	}

	@Override
	public String getDefIdByBpmnDefId(String bpmnDefId) {
		return bpmDefinitionManager.getDefIdByBpmnDefId(bpmnDefId);
	}

	@Override
	public BpmProcessDef<BpmProcessDefExt> getBpmProcessDef(String bpmnDefId) {

		String defId = bpmDefinitionManager.getDefIdByBpmnDefId(bpmnDefId);
		BpmProcessDef<BpmProcessDefExt> processDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		return processDef;

	}

	@Override
	public String getBpmnXmlByBpmnDefId(String bpmnDefId) {
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.getByBpmnDefId(bpmnDefId);
		return natProDefinitionService.getDefXmlByDeployId(bpmDefinition.getBpmnDeployId());
	}

	@Override
	public String getBpmnXmlByDeployId(String deployId) {
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.getByBpmnDeployId(deployId);
		return natProDefinitionService.getDefXmlByDeployId(bpmDefinition.getBpmnDeployId());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BpmVariableDef> getVariableDefs(String defId) {
		BpmProcessDef<DefaultBpmProcessDefExt> bpmProcessDef = (BpmProcessDef) bpmDefinitionAccessor.getBpmProcessDef(defId);
		return bpmProcessDef.getProcessDefExt().getVariableList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BpmVariableDef> getVariableDefs(String defId, String nodeId) {
		BpmProcessDef<DefaultBpmProcessDefExt> bpmProcessDef = (BpmProcessDef) bpmDefinitionAccessor.getBpmProcessDef(defId);
		return bpmProcessDef.getProcessDefExt().getVariableList(nodeId);
	}

	@Override
	public void switchMainVersion(String defId) {
		bpmDefinitionManager.updMainVersion(defId);

	}

	@Override
	public boolean disabledBpmDefinitionInst(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		defaultBpmDefinition.setStatus(BpmDefinition.STATUS.FORBIDDEN_INSTANCE);
		bpmDefinitionManager.update(defaultBpmDefinition);
		// 更新流程实例为禁止。
		String defKey = defaultBpmDefinition.getDefKey();
		bpmProcessInstanceManager.updForbiddenByDefKey(defKey, BpmProcessInstance.FORBIDDEN_YES);

		return false;
	}

	@Override
	public void cleanData(String defId) {
		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionManager.get(defId);
		
		if (!BpmDefinition.TEST_STATUS.TEST.equals(defaultBpmDefinition.getTestStatus())) {
			throw new ProcessDefException("非测试状态的流程不能清除数据");
		}
		// 清除流程实例
		bpmProcessInstanceManager.removeTestInstByDefKey(defaultBpmDefinition.getDefKey());
		
		bpmBusLinkManager.removeDataByDefId(defId);
		
		bpmInstFromManager.removeDataByDefId(defId);
	}

	@Override
	public BpmDefinition getByBpmnDefId(String bpmnDefId) {
		return bpmDefinitionManager.getByBpmnDefId(bpmnDefId);
	}
}
