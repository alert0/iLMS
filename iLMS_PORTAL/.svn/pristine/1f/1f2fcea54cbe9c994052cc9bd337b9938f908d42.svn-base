package com.hotent.bpmx.persistence.manager.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.Page;
import com.hotent.base.api.query.FieldRelation;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultFieldLogic;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.constant.DesignerType;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.event.BpmDefinitionDelEvent;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.core.engine.def.DefXmlTransForm;
import com.hotent.bpmx.core.engine.def.impl.DefaultBpmDefConditionService;
import com.hotent.bpmx.natapi.def.DefTransform;
import com.hotent.bpmx.natapi.def.NatProDefinitionService;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.dao.BpmDefDataDao;
import com.hotent.bpmx.persistence.dao.BpmDefinitionDao;
import com.hotent.bpmx.persistence.dao.BpmProBoDao;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.AuthorizeRight;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.BpmDefData;
import com.hotent.bpmx.persistence.model.BpmProBo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.query.BpmDefFieldSorts;
import com.hotent.bpmx.persistence.model.query.BpmDefQueryFields;
import com.hotent.bpmx.persistence.util.BpmnXmlValidateUtil;
import com.hotent.form.persistence.dao.BpmFormRightDao;
import com.hotent.form.persistence.model.BpmFormRight;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.util.ContextUtil;



@Service("defaultBpmDefinitionManager")
public class BpmDefinitionManagerImpl extends AbstractManagerImpl<String, DefaultBpmDefinition> implements BpmDefinitionManager {

	private final Log logger = LogFactory.getLog(getClass());

	@SuppressWarnings("rawtypes")
	@Resource
	ICache iCache;
	@Resource
	BpmDefinitionDao bpmDefinitionDao;
	@Resource
	BpmDefDataDao bpmDefDataDao;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	NatProDefinitionService natProDefinitionService;
	@Resource
	DefXmlTransForm defXmlTransForm;
	@Resource
	BpmDefAuthorizeManager bpmDefAuthorizeManager;
	@Resource 
	BpmProBoDao bpmProBoDao;
	@Resource 
	BpmFormRightDao bpmFormRightDao;

	@Resource
	IUserService userServiceImpl;
	@Resource
	ActTaskDao actTaskDao;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	DefaultBpmDefConditionService bpmDefHandler;

	/**
	 * 添加缓存。
	 * 
	 * @param def
	 *            void
	 */
	@SuppressWarnings("unchecked")
	private void addCache(DefaultBpmDefinition def) {
		String defId = def.getDefId();
		String key = BpmDefinition.BPM_DEFINITION + defId;
		String bpmnKey = BpmDefinition.BPM_BPMN_ID + def.getBpmnDefId();

		// 添加缓存。
		iCache.add(key, def);
		iCache.add(bpmnKey, defId);
	}

	/**
	 * 读取缓存。
	 * 
	 * @param defId
	 * @return DefaultBpmDefinition
	 */
	private DefaultBpmDefinition getFromCache(String defId) {
		String key = BpmDefinition.BPM_DEFINITION + defId;
		DefaultBpmDefinition bpmDef = (DefaultBpmDefinition) iCache.getByKey(key);
		return bpmDef;
	}

	/**
	 * 根据流程定义ID获取DEFID
	 * 
	 * @param bpmnDefId
	 * @return String
	 */
	private String getDefIdByBpmnIdFromCache(String bpmnDefId) {
		String bpmnId = BpmDefinition.BPM_BPMN_ID + bpmnDefId;
		return (String) iCache.getByKey(bpmnId);

	}

	private void publishEvent(BpmDefinition def) {
		// 清除相关流程定义缓存。
		List<DefaultBpmDefinition> defList = bpmDefinitionDao.getByDefKey(def.getDefKey());
		for (DefaultBpmDefinition defEntity : defList) {
			AppUtil.publishEvent(new BpmDefinitionDelEvent(defEntity));
		}
		// AppUtil.publishEvent(new BpmDefinitionDelEvent(def));
	}

	@Override
	protected Dao<String, DefaultBpmDefinition> getDao() {
		return bpmDefinitionDao;
	}

	@Override
	public DefaultBpmDefinition getById(String entityId) {
		if (StringUtil.isEmpty(entityId)) {
			return null;
		}

		DefaultBpmDefinition bpmDef = getFromCache(entityId);

		if (bpmDef != null) {
			return bpmDef;
		}

		DefaultBpmDefinition defaultBpmDefinition = bpmDefinitionDao.get(entityId);

		BpmDefData bpmDefData = bpmDefDataDao.get(entityId);
		defaultBpmDefinition.setBpmDefData(bpmDefData);
		// 添加缓存。
		addCache(defaultBpmDefinition);

		return defaultBpmDefinition;
	}

	@Override
	public DefaultBpmDefinition getMainByDefKey(String defKey) {
		return getMainByDefKey(defKey, false);
	}

	@Override
	public DefaultBpmDefinition getMainByDefKey(String defKey, boolean needData) {
		DefaultBpmDefinition bpmdef = bpmDefinitionDao.getMainByDefKey(defKey);
		if (bpmdef == null || !needData)
			return bpmdef;

		// 流程定义XML数据。
		BpmDefData bpmDefData = bpmDefDataDao.get(bpmdef.getDefId());
		bpmdef.setBpmDefData(bpmDefData);

		return bpmdef;
	}

	@Override
	public void create(DefaultBpmDefinition entity) {
		super.create(entity);

		if (StringUtil.isEmpty(entity.getBpmDefData().getId())) {
			entity.getBpmDefData().setId(entity.getDefId());
		}
		bpmDefDataDao.create(entity.getBpmDefData());
	}

	@Override
	public void update(DefaultBpmDefinition entity) {
		super.update(entity);

		if (StringUtil.isNotEmpty(entity.getBpmDefData().getId())) {
			bpmDefDataDao.update(entity.getBpmDefData());
		}
		// 清理缓存
		publishEvent(entity);
	}

	@Override
	public void removeCascade(String defId) {
		BpmDefinition bpmDef = bpmDefinitionDao.get(defId);
		String defKey = bpmDef.getDefKey();
		// 删除时清除缓存。
		List<DefaultBpmDefinition> defList = bpmDefinitionDao.getByDefKey(defKey);
		for (DefaultBpmDefinition def : defList) {
			publishEvent(def);
		}

		List<DefaultBpmProcessInstance> instList = bpmProcessInstanceManager.getListByBpmnDefKey(defKey);

		for (DefaultBpmProcessInstance inst : instList) {
			bpmProcessInstanceManager.remove(inst.getId());
		}
		
		//删除Actviti流程任务
		if(StringUtil.isNotEmpty(bpmDef.getBpmnDefId())){
			bpmDefinitionDao.delActTask(bpmDef.getBpmnDefId());
		}
		
		// 删除流程数据表。
		removeActviti(defKey);
		// 删除平台流程数据。
		removeDef(defKey);
	}

	/**
	 * 删除流程平台数据。
	 * 
	 * @param defKey
	 *            void
	 */
	private void removeDef(String defKey) {
		bpmDefDataDao.delByDefKey(defKey);
		bpmDefinitionDao.delByKey(defKey);
		// 删除表单权限
		bpmFormRightDao.removeByFlowKey(defKey, "", 1);
		// 删除表单权限
	    bpmFormRightDao.removeByFlowKey(defKey, "", 2);
	}

	/**
	 * 删除流程引擎的流程数据。
	 * 
	 * @param defKey
	 *            void
	 */
	private void removeActviti(String defKey) {
		bpmDefinitionDao.delActByteArray(defKey);
		bpmDefinitionDao.delActDeploy(defKey);
		bpmDefinitionDao.delActDef(defKey);
	}

	@Override
	public DefaultBpmDefinition cloneToMain(DefaultBpmDefinition bpmDefinition) {
		// 克隆流程定义并新建
		DefaultBpmDefinition newBpmDefinition = (DefaultBpmDefinition) bpmDefinition.clone();
		newBpmDefinition.setDefId(UniqueIdUtil.getSuid());
		// 将新的流程定义设置为主版本
		newBpmDefinition.setIsMain("Y");
		// 设置版本号
		Integer maxVersion = getMaxVersion(bpmDefinition.getDefKey());
		newBpmDefinition.setVersion(maxVersion);

		bpmDefinitionDao.create(newBpmDefinition);

		// 更新其他版本为非主版本，更新当前的为主版本。
		updMainVersion(newBpmDefinition.getDefId());

		// 创建流程定义XML数据记录
		newBpmDefinition.getBpmDefData().setId(newBpmDefinition.getDefId());
		bpmDefDataDao.create(newBpmDefinition.getBpmDefData());

		return newBpmDefinition;
	}

	@Override
	public List<DefaultBpmDefinition> queryByDefKey(String defKey) {
		return bpmDefinitionDao.queryByDefKey(defKey);
	}

	@Override
	public List<DefaultBpmDefinition> queryHistorys(String defKey) {
		return bpmDefinitionDao.queryHistorys(defKey);
	}

	@Override
	public Integer getMaxVersion(String defKey) {
		Integer version = bpmDefinitionDao.getMaxVersion(defKey);
		if (version != null) {
			return version + 1;
		}
		return 1;
	}

	public List<DefaultBpmDefinition> query(BpmDefQueryFields bpmDefQueryFields, FieldRelation fieldRelation, DefaultPage page) {
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 查询条件
		DefaultFieldLogic fieldLogic = new DefaultFieldLogic(fieldRelation);
		fieldLogic.setWhereClauses(bpmDefQueryFields.getQueryFields());
		// 排序和分页
		queryFilter.setFieldLogic(fieldLogic);
		queryFilter.setPage((Page) page);
		return this.query(queryFilter);
	}

	@Override
	public List<DefaultBpmDefinition> query(BpmDefQueryFields bpmDefQueryFields, FieldRelation fieldRelation, BpmDefFieldSorts bpmDefFieldSorts) {
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 查询条件
		DefaultFieldLogic fieldLogic = new DefaultFieldLogic(fieldRelation);
		fieldLogic.setWhereClauses(bpmDefQueryFields.getQueryFields());
		// 设置
		queryFilter.setFieldLogic(fieldLogic);
		queryFilter.setFieldSortList(bpmDefFieldSorts.getFieldSorts());
		queryFilter.setPage(null);
		return query(queryFilter);
	}

	public String getDefIdByBpmnDefId(String bpmnDefId) {
		String defId = getDefIdByBpmnIdFromCache(bpmnDefId);
		if (StringUtil.isNotEmpty(defId))
			return defId;
		return bpmDefinitionDao.getDefIdByBpmnDefId(bpmnDefId);
	}

	@Override
	public void updMainVersion(String entityId) {
		BpmDefinition def = bpmDefinitionDao.get(entityId);
		bpmDefinitionDao.updateToMain(entityId);
		publishEvent(def);

	}

	@Override
	public DefaultBpmDefinition getByBpmnDefId(String bpmnDefId) {
		return bpmDefinitionDao.getByBpmnDefId(bpmnDefId);
	}

	@Override
	public DefaultBpmDefinition getByBpmnDeployId(String bpmnDeployId) {
		return bpmDefinitionDao.getByBpmnDeployId(bpmnDeployId);
	}

	@Override
	public String updateBpmDefXml(String defId, String defXml) throws Exception {
		DefaultBpmDefinition bpmDefinition = bpmDefinitionDao.get(defId);
		BpmDefData bpmDefData = bpmDefDataDao.get(defId);
		if (bpmDefData == null)
			return null;
		bpmDefData.setDefXml(defXml);
		DesignerType designType = DesignerType.valueOf(bpmDefinition.getDesigner());
		DefTransform trans = natProDefinitionService.getDefTransform(designType);
		String bpmnDefXml = trans.convert(bpmDefinition.getDefKey(), bpmDefinition.getName(), defXml);
		bpmDefData.setBpmnXml(bpmnDefXml);
		bpmDefDataDao.update(bpmDefData);
		// 清理缓存
		publishEvent(bpmDefinition);
		return bpmnDefXml;
	}

	@Override
	public void updateStatus(String defId, String status) {
		bpmDefinitionDao.updateStatus(defId, status);
	}

	@Override
	public void updBpmData(String defId, BpmDefData bpmDefData) {
		DefaultBpmDefinition bpmDefinition = bpmDefinitionDao.get(defId);

		bpmDefDataDao.update(bpmDefData);
		// 更新activiti的XML。
		String deployId = bpmDefinition.getBpmnDeployId();
		natProDefinitionService.writeDefXml(deployId, bpmDefData.getBpmnXml());
		// 清理缓存
		publishEvent(bpmDefinition);

	}

	@Override
	public List<DefaultBpmDefinition> queryByCreateBy(String userId) {
		return bpmDefinitionDao.queryByCreateBy(userId);
	}

	@Override
	public void removeByIds(String... ids) {
		for (String id : ids) {
			removeCascade(id);
		}
	}

	@Override
	public boolean deploy(BpmDefinition bpmDefinition) throws Exception {
		// 数据有效性判断
		if (!isAvailable(bpmDefinition)) {
			return false;
		}
		// 如果是新的流程定义标识
		boolean isNewDef = StringUtil.isEmpty(bpmDefinition.getDefId()) ? true : false;
		// 是否为草稿
		boolean isDraft = false;
		// 旧的bpmnXml数据
		String oldBpmnXml = "";
		if (isNewDef) {
			// 判断defKey是否存在，存在则不允许发布
			DefaultBpmDefinition mainBpmDefinition = getMainByDefKey(bpmDefinition.getDefKey(), false);
			if (mainBpmDefinition != null) {
				logger.error("defKey '" + bpmDefinition.getDefKey() + "' is exists ");
				return false;
			}
		} else {
			DefaultBpmDefinition tempDef = this.getById(bpmDefinition.getDefId());
			oldBpmnXml = tempDef.getBpmnXml();
			if (tempDef != null && StringUtil.isEmpty(tempDef.getBpmnDefId())) {
				isDraft = true;
			}
		}
		
		// 调用native的xml转换接口，转换defXml为bpmnXml
		String bpmnXml = getBpmnXmlByDesignFile(bpmDefinition);
		if (StringUtil.isEmpty(bpmnXml)) {
			throw new RuntimeException("流程发布失败！流程图不能为空");
		}
		// 更新插件
		bpmnXml = updateBpmnXmlPlugins(bpmnXml, oldBpmnXml);
		
		//校验流程完整性
		List<BpmNodeDef> nodeDefs = new  ArrayList<BpmNodeDef>();
		if(isNewDef){
			try {
				nodeDefs = BpmnXmlValidateUtil.getNodeDefs(bpmnXml);
			} catch (Exception e) {
				throw new RuntimeException("流程定义解析失败（可能是未执行更新流程引擎操作）："+e.getMessage());
			}
		}else{
			try {
				nodeDefs = bpmDefinitionService.getAllBpmNodeDefs(bpmDefinition.getDefId());
			} catch (Exception e) {
				throw new RuntimeException("流程定义校验失败："+e.getMessage());
			}
		}
		net.sf.json.JSONObject msg = BpmnXmlValidateUtil.vilateBpmXml(nodeDefs);
		if(!msg.getBoolean("isTrue")){
			throw new RuntimeException("流程定义校验失败："+msg.getString("errorMsgs"));
		}
		
		
		// 调用native接口发布流程
		String deployId = null;
		String bpmnDefId = null;
		try {
			deployId = natProDefinitionService.deploy("", bpmDefinition.getName(), bpmnXml);
			bpmnDefId = natProDefinitionService.getProcessDefinitionIdByDeployId(deployId);
		} catch (UnsupportedEncodingException e) {
			logger.error("Invoke natProDefinitionService.deploy method error = " + e.getMessage());
			return false;
		}
		DefaultBpmDefinition def = (DefaultBpmDefinition) bpmDefinition;
		// 根据该流程是否已持久化做分支处理
		if (isNewDef) { // 新流程定义

			def.setDefId(UniqueIdUtil.getSuid());
			def.setVersion(1);
			def.setMainDefId(bpmDefinition.getDefId());
			def.setIsMain("Y");
			def.setStatus(BpmDefinition.STATUS.DEPLOY);
			def.setTestStatus(BpmDefinition.TEST_STATUS.TEST);
			
			//def.setDesc("");
			def.setBpmnDefId(bpmnDefId);
			def.setBpmnDeployId(deployId);
			def.setBpmnXml(bpmnXml);
			// 保存
			this.create(def);

		} else if (isDraft) {// 若从草稿进行发布，即需要更新该定义
			def.setStatus(BpmDefinition.STATUS.DEPLOY);
			def.setTestStatus(BpmDefinition.TEST_STATUS.TEST);
			def.setVersion(1);
			def.setMainDefId(bpmDefinition.getDefId());
			def.setIsMain("Y");
			def.setBpmnDefId(bpmnDefId);
			def.setBpmnDeployId(deployId);
			def.setBpmnXml(bpmnXml);
			this.update(def);
		} else {// 发布新版本

			DefaultBpmDefinition oldBpmDefinition = (DefaultBpmDefinition) bpmDefinition;

			oldBpmDefinition.setStatus(BpmDefinition.STATUS.DEPLOY);
			oldBpmDefinition.setTestStatus(BpmDefinition.TEST_STATUS.TEST);

			oldBpmDefinition.setBpmnDefId(bpmnDefId);
			oldBpmDefinition.setBpmnDeployId(deployId);
			oldBpmDefinition.setBpmnXml(bpmnXml);

			// 根据旧的流程定义克隆一份，并将新的流程定义作为主版本
			this.cloneToMain(oldBpmDefinition);

		}
		// 清除缓存
		publishEvent(def);
		return true;

	}

	@Override
	public boolean updateBpmDefinition(BpmDefinition bpmDefinition) throws Exception {
		// 进行数据有效性判断
		if (!isAvailable(bpmDefinition))
			return false;

		// 调用native的xml转换接口，转换defXml为bpmnXml
		String bpmnXml = getBpmnXmlByDesignFile(bpmDefinition);
		if (StringUtil.isEmpty(bpmnXml)) {
			return false;
		}
		// 更新插件
		DefaultBpmDefinition oldBpmDefinition = this.getById(bpmDefinition.getDefId());
		String oldBpmnXml = oldBpmDefinition.getBpmnXml();
		bpmnXml = updateBpmnXmlPlugins(bpmnXml, oldBpmnXml);

		// 判断bpmnXml是否改变
		boolean isBpmnXmlChange = bpmnXml.equals(bpmDefinition.getBpmnXml()) ? false : true;

		DefaultBpmDefinition def = (DefaultBpmDefinition) bpmDefinition;
		if (isBpmnXmlChange) {
			// 保存流程
			def.setBpmnXml(bpmnXml);
			// 确定是否已经发布，为空则为未发布。
			if (StringUtil.isNotEmpty(bpmDefinition.getBpmnDeployId())) {
				// 发布流程引擎中的定义
				natProDefinitionService.writeDefXml(bpmDefinition.getBpmnDeployId(), bpmnXml);
			}
		}
		this.update(def);

		// 清除缓存
		publishEvent(def);

		return true;
	}

	@Override
	public boolean saveDraft(BpmDefinition bpmDefinition) throws Exception {
		// 先判断是否可以处理
		if (bpmDefinition == null || StringUtil.isEmpty(bpmDefinition.getDefKey())) {
			//return false;
		}
		DefaultBpmDefinition def = (DefaultBpmDefinition) bpmDefinition;

		String bpmnXml = getBpmnXmlByDesignFile(bpmDefinition);
		if(StringUtil.isEmpty(bpmnXml)){
			throw new RuntimeException("流程图不能为空！");
		}
		def.setBpmnXml(bpmnXml);
		// 设置状态
		def.setIsMain("Y");
		def.setStatus(BpmDefinition.STATUS.DRAFT);
		def.setTestStatus(BpmDefinition.TEST_STATUS.TEST);
		// 草稿默认为版本0
		def.setVersion(0);
		String defId = UniqueIdUtil.getSuid();
		def.setDefId(defId);
		def.setMainDefId(defId);
		this.create(def);
		return true;
	}

	/**
	 * 
	 * 根据根据设计文件转换成标准的bpmn20定义文件。
	 * 
	 * @param bpmDefinition
	 * @return String
	 * @throws Exception 
	 */
	private String getBpmnXmlByDesignFile(BpmDefinition bpmDefinition) throws Exception {
		String bpmnXml = "";
		DesignerType designType = DesignerType.valueOf(bpmDefinition.getDesigner());
		DefTransform trans = natProDefinitionService.getDefTransform(designType);
		if(DesignerType.WEB.name().equals(bpmDefinition.getDesigner())){
			DefaultBpmDefinition def = (DefaultBpmDefinition)bpmDefinition;
			bpmnXml = trans.convert(bpmDefinition.getDefKey(), bpmDefinition.getName(), def.getDefJson());
		}else{
			bpmnXml = trans.convert(bpmDefinition.getDefKey(), bpmDefinition.getName(), bpmDefinition.getDefXml());
		}
		
		//web流程设计器需将分支条件等信息拷贝过来
		if(StringUtil.isNotEmpty(bpmDefinition.getDefId())&&DesignerType.WEB.name().equals(bpmDefinition.getDesigner())){
			List<BpmNodeDef> nodeDefs = bpmDefinitionService.getAllBpmNodeDefs(bpmDefinition.getDefId());
			for (BpmNodeDef bpmNodeDef : nodeDefs) {
				if(NodeType.EXCLUSIVEGATEWAY.equals(bpmNodeDef.getType())){
					Map<String, String> conditionsMap = bpmNodeDef.getConditions();
					if(BeanUtils.isNotEmpty(conditionsMap)){
						try {
							bpmnXml = bpmDefHandler.converConditionXml(bpmNodeDef.getNodeId(), conditionsMap, bpmnXml);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return bpmnXml;
	}

	private String updateBpmnXmlPlugins(String newBpmnXml, String oldBpmnXml) {
		if (StringUtil.isEmpty(oldBpmnXml)) {
			return newBpmnXml;
		}
		String bpmnXml = defXmlTransForm.transform(oldBpmnXml, newBpmnXml);
		return bpmnXml;
	}

	/**
	 *
	 * 判断流程定义的数据是否有效（即包含必须的一些数据）
	 * 
	 * @param bpmDefinition
	 * @return boolean
	 */
	private boolean isAvailable(BpmDefinition bpmDefinition) {
		if (bpmDefinition == null || StringUtil.isEmpty(bpmDefinition.getName()) || StringUtil.isEmpty(bpmDefinition.getDefKey())) {
			logger.error("DefaultBpmDefinitionService.deploy data error, bpmDefinition=" + bpmDefinition);
			return false;
		}
		return true;
	}

	@Override
	public List<DefaultBpmDefinition> queryList(QueryFilter queryFilter) {
		IUser user = ContextUtil.getCurrentUser();
		return queryList(queryFilter, user);
	}

	@Override
	public List<DefaultBpmDefinition> queryList(QueryFilter queryFilter, IUser user) {
		String userId = user.getUserId();
		Map<String, JSONObject> authorizeRightMap = null;
		//当前人是否超管
		boolean isAdmin=user.isAdmin();
		
		queryFilter.addParamsFilter("isAdmin", isAdmin?1:0);
		//普通用户先获取授权流程及权限。
		if (!isAdmin) {
			// 获得流程分管授权与用户相关的信息集合的流程权限内容
			authorizeRightMap = getCommUserCondition(userId,queryFilter);
		}
		// 查询列表
		List<DefaultBpmDefinition> list = this.query(queryFilter);
		if (list == null) return list;
		
		// 把前面获得的流程分管授权的权限内容设置到流程管理列表
		for (DefaultBpmDefinition bpmDefinition : list) {
			JSONObject authorizeRight =null;
			if(isAdmin){
				//超管的权限默认。
				authorizeRight=AuthorizeRight.getAdminRight();
			}
			else{
				authorizeRight = authorizeRightMap.get(bpmDefinition.getDefKey());
			}
			bpmDefinition.setAuthorizeRight(authorizeRight);
		}
		return list;
	}
	
	/**
	 * 普通用户获取有权限的流程定义及权限。
	 * @param userId
	 * @param queryFilter
	 * @return
	 */
	private Map<String, JSONObject> getCommUserCondition(String userId,QueryFilter queryFilter){
			String bpmDefAuthorizeRightType = BPMDEFAUTHORIZE_RIGHT_TYPE.MANAGEMENT;
			// 有传入权限类型的话
			Map<String, Object> params = queryFilter.getParams();
			String right = (String) params.get("bpmDefAuthorizeRightType");
			if (StringUtil.isNotEmpty(right)) {
				bpmDefAuthorizeRightType = right;
			}
			// 获得流程分管授权与用户相关的信息
			Map<String, Object> actRightMap = bpmDefAuthorizeManager.getActRightByUserId(userId, bpmDefAuthorizeRightType, true, true);
			// 获得流程分管授权与用户相关的信息集合的流程KEY
			String defKeys = (String) actRightMap.get("defKeys");
			if (StringUtil.isNotEmpty(defKeys)){
				queryFilter.addParamsFilter("defKeys", defKeys);
			}
			// 获得流程分管授权与用户相关的信息集合的流程权限内容
			Map<String, JSONObject> authorizeRightMap = (Map<String, JSONObject>) actRightMap.get("authorizeRightMap");
			return authorizeRightMap;
	}

	@Override
	public List<DefaultBpmDefinition> queryListByMap(Map<String, Object> map) {
		return this.bpmDefinitionDao.queryListByMap(map);
	}

	@Override
	public void updDefType(String typeId, List<String> defList) {
		this.bpmDefinitionDao.updDefType(typeId, defList);
	}

	@Override
	public void removeDefId(String defId) {
		BpmDefinition bpmDef = bpmDefinitionDao.get(defId);

		publishEvent(bpmDef);

		// 根据流程定义ID获取实例列表。
		List<DefaultBpmProcessInstance> instList = bpmProcessInstanceManager.getListByDefId(defId);

		for (DefaultBpmProcessInstance inst : instList) {
			bpmProcessInstanceManager.remove(inst.getId());
		}
		// 删除流程引擎表
		removeActvitiByDefId(defId);
		// 删除流程定义表。
		removeDefByDefId(defId);
	}

	/**
	 * 删除流程平台数据。
	 * 
	 * @param defId
	 *            void
	 */
	private void removeDefByDefId(String defId) {
		bpmDefDataDao.remove(defId);
		bpmDefinitionDao.remove(defId);
	}

	/**
	 * 删除流程引擎的流程数据。
	 * 
	 * @param defId
	 *            void
	 */
	private void removeActvitiByDefId(String defId) {
		bpmDefinitionDao.delActByteArrayByDefId(defId);
		bpmDefinitionDao.delActDeployByDefId(defId);
		bpmDefinitionDao.delActDefByDefId(defId);
	}

	@Override
	public void removeDefIds(String... defIds) {
		for (String defId : defIds) {
			removeDefId(defId);
		}
	}

	@Override
	public void copyDef(String defId, String name, String defKey) {
		
		DefaultBpmDefinition bpmDefinition = bpmDefinitionDao.get(defId);
		BpmDefData bpmDefData =  bpmDefDataDao.get(defId);
		String bpmnXml = bpmDefData.getBpmnXml();
		String oldDefKey = bpmDefinition.getDefKey();

		bpmnXml =  updateBpmXml(bpmnXml, defKey, name);
		bpmDefinition.setDefKey(defKey);
		bpmDefinition.setName(name);
		bpmDefData.setBpmnXml(bpmnXml);
		
		// 发布
		// 调用native接口发布流程
		String deployId = null;
		String bpmnDefId = null;
		try {
			deployId = natProDefinitionService.deploy("", bpmDefinition.getName(), bpmnXml);
			bpmnDefId = natProDefinitionService.getProcessDefinitionIdByDeployId(deployId);
		} catch (UnsupportedEncodingException e) {
			logger.error("Invoke natProDefinitionService.deploy method error = " + e.getMessage());
		}
		bpmDefinition.setDefId(UniqueIdUtil.getSuid());
		bpmDefinition.setVersion(1);
		bpmDefinition.setMainDefId(bpmDefinition.getDefId());
		bpmDefinition.setIsMain("Y");
		bpmDefinition.setStatus(BpmDefinition.STATUS.DEPLOY);
		bpmDefinition.setTestStatus(BpmDefinition.TEST_STATUS.TEST);
		bpmDefinition.setDesc("");
		bpmDefinition.setBpmnDefId(bpmnDefId);
		bpmDefinition.setBpmnDeployId(deployId);
		bpmDefinition.setBpmnXml(bpmnXml);
		bpmDefData.setId(bpmDefinition.getDefId());
		// 保存
		bpmDefinitionDao.create(bpmDefinition);
		bpmDefDataDao.create(bpmDefData);
		copyBpmProByDefId(defId,bpmDefinition.getDefId());
		copyBpmFormRightByFlowKey(oldDefKey,defKey);
		
	}
	
	// 复制流程表单权限
	private void copyBpmFormRightByFlowKey(String oldDefKey, String defKey) {
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("flow_key_", oldDefKey,QueryOP.EQUAL);
		queryFilter.addFilter("permission_type_", 1,QueryOP.EQUAL);
		queryFilter.setPage(null);
		List<BpmFormRight> lists = bpmFormRightDao.query(queryFilter);
		for (BpmFormRight bpmFormRight : lists) {
			bpmFormRight.setId(UniqueIdUtil.getSuid());
			bpmFormRight.setFlowKey(defKey);
			bpmFormRightDao.create(bpmFormRight);
		}
	}

	// 复制流程业务对象设置表
	private void copyBpmProByDefId(String oldDefId, String newDefId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processId", oldDefId);
		List<BpmProBo> lists = bpmProBoDao.getByProcess(params);
		for (BpmProBo bpmProBo : lists) {
			bpmProBo.setId(UniqueIdUtil.getSuid());
			bpmProBo.setProcessId(newDefId);
			bpmProBoDao.create(bpmProBo);
		}
	}

	@SuppressWarnings("rawtypes")
	private String updateBpmXml(String bpmnXml,String defKey, String name){
		Document loadXml = Dom4jUtil.loadXml(bpmnXml);
	   // 根节点  
       Element root = loadXml.getRootElement();
       // 取得某节点下名为"process"的所有字节点  
       List nodes = root.elements("process");  
       // xml元素  
       if(nodes!=null&&nodes.size()==1){
	       	Element element = (Element) nodes.get(0);
	       	element.attribute("id").setText(defKey);
	       	element.attribute("name").setText(name);
       }
       return Dom4jUtil.docToString(loadXml);
	}

	@Override
	public void updBpmDefinitionStatus(DefaultBpmDefinition bpmDefinition, String oldStatus) throws Exception {
		String status = bpmDefinition.getStatus();
		// 原来为草稿改成发布
		if (BpmDefinition.STATUS.DRAFT.equalsIgnoreCase(oldStatus) && BpmDefinition.STATUS.DEPLOY.equalsIgnoreCase(status)) {
			bpmDefinitionService.deploy(bpmDefinition);
		}
		// 状态修改成发布。
		if (!status.equalsIgnoreCase(oldStatus) && BpmDefinition.STATUS.DEPLOY.equalsIgnoreCase(status)) {
			bpmProcessInstanceManager.updForbiddenByDefKey(bpmDefinition.getDefKey(), BpmProcessInstance.FORBIDDEN_NO);
		}
		// 将流程实例修改成禁用实例。
		if (!status.equalsIgnoreCase(oldStatus) && BpmDefinition.STATUS.FORBIDDEN_INSTANCE.equalsIgnoreCase(status)) {
			bpmProcessInstanceManager.updForbiddenByDefKey(bpmDefinition.getDefKey(), BpmProcessInstance.FORBIDDEN_YES);
		}
	}

}
