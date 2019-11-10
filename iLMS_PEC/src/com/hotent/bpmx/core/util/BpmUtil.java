package com.hotent.bpmx.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.ProcessInstCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BPMN20ExtConst;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.constant.TaskActionType;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.NoExecutorEvent;
import com.hotent.bpmx.api.event.NoExecutorModel;
import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.IGlobalRestfulPluginDef;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.SkipResult;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginFactory;
import com.hotent.bpmx.api.plugin.core.runtime.BpmExecutionPlugin;
import com.hotent.bpmx.api.plugin.core.runtime.BpmTaskPlugin;
import com.hotent.bpmx.api.plugin.core.task.TaskActionHandlerConfig;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.RestfulService;
import com.hotent.bpmx.api.service.SignService;
import com.hotent.bpmx.core.defxml.entity.ExtensionElements;
import com.hotent.bpmx.core.defxml.entity.FlowElement;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.engine.task.skip.SkipConditionUtil;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.impl.BpmExeStackRelationDaoImpl;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.api.calendar.ICalendarService;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * 描述：BPM常用解析工具类
 * 构建组：x5-bpmx-core
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-2-10-下午2:52:16
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BpmUtil {
	/**
	 * 根据规则模版获取标题。
	 * 
	 * <pre>
	 * 示例：
	 * String rule="{depart:某个部门}申请{时间}公司{活动}";
	 * Map<String,String> map=new HashMap<String,String>();
	 * map.put("depart","测试部");
	 * map.put("时间","周末");
	 * map.put("活动","踏青");
	 * String subject=BpmUtil.getTitleByRule(rule,map);
	 * 返回:
	 * 测试部申请周末公司踏青
	 * </pre>
	 * 
	 * @param titleRule
	 *            主题规则
	 * @param map
	 *            变量map，用于替换规则中的变量。
	 * @return String
	 */
	public static String getTitleByRule(String titleRule, Map<String, Object> map) {
		if (StringUtils.isEmpty(titleRule))
			return "";
		Pattern regex = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher = regex.matcher(titleRule);
		while (matcher.find()) {
			String tag = matcher.group(0);
			String rule = matcher.group(1);
			String[] aryRule = rule.split(":");
			String name = "";
			if (aryRule.length == 1) {
				name = rule;
			} else {
				name = aryRule[1];
			}
			if (map.containsKey(name)) {
				Object obj = map.get(name);
				if (obj != null) {
					try {
						titleRule = titleRule.replace(tag, obj.toString());
					} catch (Exception e) {
						titleRule = titleRule.replace(tag, "");
					}
				} else {
					titleRule = titleRule.replace(tag, "");
				}
			} else {
				titleRule = titleRule.replace(tag, "");
			}
		}
		return titleRule;
	}

	/**
	 * 将activi的任务转换成BpmTask对象实例。
	 * 
	 * @param delegateTask
	 * @return BpmTask
	 */
	public static BpmTask convertTask(BpmDelegateTask delegateTask) {
		String taskId = delegateTask.getId();
		String subject = (String) delegateTask.getVariable(BpmConstants.SUBJECT);

		String instId = (String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);
		String bpmnDefId = delegateTask.getBpmnDefId();

		BpmDefinitionService bpmDefinitionService = AppUtil.getBean(BpmDefinitionService.class);
		
		DefaultBpmDefinition def = (DefaultBpmDefinition) bpmDefinitionService.getByBpmnDefId(bpmnDefId);
		
		
		ActionCmd cmd=ContextThreadUtil.getActionCmd(); 
		
		BpmProcessInstance bpmProcessInstance= (BpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST); 
		
		
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.MOBILE);
		FormModel formModel = bpmFormService.getByDefId(def.getId(), delegateTask.getTaskDefinitionKey(), bpmProcessInstance);
		
		int supportMobile=0;
		
		if(formModel!=null && !formModel.isFormEmpty()){
			supportMobile=1;
		}
		
		

		DefaultBpmTask task = new DefaultBpmTask();
		task.setId(taskId);
		task.setSubject(subject);

		task.setTaskId(taskId);
		task.setDueTime(delegateTask.getDueDate());
		task.setSuspendState((short) delegateTask.getSuspensionState());
		task.setExecId(delegateTask.getExecutionId());

		task.setName(delegateTask.getName());
		task.setNodeId(delegateTask.getTaskDefinitionKey());
		task.setProcInstId(instId);
		task.setBpmnInstId(delegateTask.getProcessInstanceId());
		task.setOwnerId(BpmConstants.EmptyUser);
		task.setAssigneeId(BpmConstants.EmptyUser);
		task.setPriority((long) delegateTask.getPriority());
		task.setProcDefId(def.getDefId());
		task.setProcDefKey(def.getDefKey());
		task.setProcDefName(def.getName());
		task.setStatus(TaskType.NORMAL.name());
		task.setBpmnDefId(bpmnDefId);
		task.setTypeId(def.getTypeId());
		task.setSupportMobile(supportMobile);
		task.setCreateTime(delegateTask.getCreateTime());
		
		if(ActionType.BACK.getKey().equals(cmd.getActionName())||ActionType.BACK_TO_START.getKey().equals(cmd.getActionName())){
			task.setStatus(TaskType.BACK.name());
		}

		return task;

	}

	/**
	 * 根据流程任务对象复制新的任务。
	 * 
	 * <pre>
	 * 1.新的任务的ExecId为空，表示派生的任务。
	 * 2.设定父任务。
	 * 3.设定任务类型。
	 * 4.设定执行人。
	 * </pre>
	 * 
	 * @param task
	 * @param parentTaskId
	 * @param taskType
	 * @param user
	 * @return DefaultBpmTask
	 */
	public static DefaultBpmTask convertTask(DefaultBpmTask task, String parentTaskId, TaskType taskType, IUser user) {
		DefaultBpmTask cloneTask = (DefaultBpmTask) task.clone();
		cloneTask.setId(UniqueIdUtil.getSuid());
		cloneTask.setParentId(parentTaskId);
		cloneTask.setExecId("");
		cloneTask.setTaskId("");
		cloneTask.setStatus(taskType.name());
		cloneTask.setAssigneeId(user.getUserId());
		cloneTask.setOwnerId(user.getUserId());
		cloneTask.setCreateTime(new Date());

		return cloneTask;

	}

	/**
	 * 根据流程定义ID获取流程的扩展属性。
	 * 
	 * @param bpmnDefId
	 * @return BpmDefExtProperties
	 */
	public static BpmDefExtProperties getExtProperties(String bpmnDefId) {
		BpmDefinitionService bpmDefinitionService = AppUtil.getBean(BpmDefinitionService.class);
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionService.getBpmProcessDef(bpmnDefId);
		BpmProcessDefExt procExt = procDef.getProcessDefExt();
		BpmDefExtProperties extProperties = procExt.getExtProperties();
		return extProperties;
	}

	private static Object[] getDefProperties(BpmProcessInstance instance, String nodeId) {
		Object[] aryObj = new Object[3];
		String defId = instance.getProcDefId();
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmDefinitionManager bpmDefinitionManager = AppUtil.getBean(BpmDefinitionManager.class);
		BpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);

		BpmNodeDef nodeDef = null;
		BpmProcessDef<BpmProcessDefExt> processDef = null;

		UserTaskNodeDef taskNodeDef = null;

		NodeProperties properties = null;

		String parentId = instance.getParentInstId();
		if (StringUtil.isNotZeroEmpty(parentId)) {
            BpmProcessInstanceManager instanceManager = AppUtil.getBean(BpmProcessInstanceManager.class);
            BpmProcessInstance parentInstance = instanceManager.get(parentId);
            nodeDef = bpmDefinitionAccessor.getBpmNodeDef(parentInstance.getProcDefId(), nodeId);
            if(BeanUtils.isNotEmpty(nodeDef)){
            	taskNodeDef = (UserTaskNodeDef) nodeDef;
            	properties = taskNodeDef.getLocalProperties();
                processDef = nodeDef.getRootProcessDef();
            }
        }
        if(BeanUtils.isEmpty(nodeDef)){
        	nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
            taskNodeDef = (UserTaskNodeDef) nodeDef;
            properties = taskNodeDef.getLocalProperties();
            processDef = nodeDef.getRootProcessDef();
        }

		aryObj[0] = properties;
		aryObj[1] = processDef;
		aryObj[2] = bpmDefinition;

		return aryObj;
	}
	
	 

	/**
	 * 是否允许节点执行人为空。
	 * 
	 * @param bpmnDefId
	 * @param nodeId
	 * @return boolean
	 */
	public static boolean isAllowEmptyIdentity(BpmProcessInstance instance, String nodeId) {
		boolean isAllowEmpty = false;

		Object[] aryObj = getDefProperties(instance, nodeId);
		NodeProperties properties = (NodeProperties) aryObj[0];
		BpmProcessDef procDef = (BpmProcessDef) aryObj[1];

		if (properties != null) {
			isAllowEmpty = properties.isAllowExecutorEmpty();
		}
		if (!isAllowEmpty) {
			BpmProcessDefExt procExt = procDef.getProcessDefExt();
			BpmDefExtProperties extProperties = procExt.getExtProperties();
			isAllowEmpty = extProperties.isAllowExecutorEmpty();
		}
		
		return isAllowEmpty;

	}

	
	/**
	 * 获取跳过第一个节点。
	 * 
	 * @param defId
	 * @return boolean
	 */
	public static boolean getSkipFirstNode(String defId) {
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		BpmProcessDefExt procExt = procDef.getProcessDefExt();
		BpmDefExtProperties extProperties = procExt.getExtProperties();
		return extProperties.isSkipFirstNode();
	}

	/**
	 * 根据bpmnDefId获取流程定义。
	 * 
	 * @param bpmnDefId
	 * @return BpmProcessDef&lt;BpmProcessDefExt>
	 */
	public static BpmProcessDef<BpmProcessDefExt> getProcessDef(String bpmnDefId) {
		BpmDefinitionService bpmDefinitionService = AppUtil.getBean(BpmDefinitionService.class);
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionService.getBpmProcessDef(bpmnDefId);
		return procDef;
	}

	/**
	 * 根据流程DEFID获取流程定义。
	 * 
	 * @param defId
	 * @return BpmProcessDef&lt;BpmProcessDefExt>
	 */
	public static BpmProcessDef<BpmProcessDefExt> getProcessDefByDefId(String defId) {
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		return procDef;
	}

	/**
	 * 
	 * 是否允许转办，如果是内部子流程，则会取父流程的参数
	 * 
	 * @param bpmNodeDef
	 * @return
	 */
	public static boolean IsAllowTransTo(BpmNodeDef bpmNodeDef) {
		boolean isAllowTransTo = true;
		if (bpmNodeDef.getBpmProcessDef().getProcessDefExt() == null) {// 子流程
			bpmNodeDef = bpmNodeDef.getParentBpmNodeDef();
		}
		try {
			isAllowTransTo = bpmNodeDef.getBpmProcessDef().getProcessDefExt().getExtProperties().isAllowTransTo();
		} catch (Exception e) {}
		return isAllowTransTo;
	}
	
	/**
	 * 
	 * 是否使用表单主版本，如果不是则取全局设置，如果全局未设置则取流程启动时的版本
	 * 
	 * @param bpmNodeDef
	 * @return
	 */
	public static String getUseMainForm(BpmNodeDef bpmNodeDef) {
		String useMainForm = "";
		if (bpmNodeDef.getBpmProcessDef().getProcessDefExt() == null) {// 子流程
			bpmNodeDef = bpmNodeDef.getParentBpmNodeDef();
		}
		try {
			useMainForm = bpmNodeDef.getBpmProcessDef().getProcessDefExt().getExtProperties().getUseMainForm();
		} catch (Exception e) {}
		return useMainForm;
	}

	/**
	 * 默认完成任务，在默认跳过时使用。
	 * 
	 * @param taskId
	 *            void
	 */
	public static void finishTask(BpmTask bpmTask) {

		String taskId = bpmTask.getId();
		// 跳过结果。
		SkipResult result = bpmTask.getSkipResult();

		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		// 目标节点
		String destination = actionCmd.getDestination();
		// 节点人员
		Map<String, List<BpmIdentity>> identityMap = actionCmd.getBpmIdentities();

		BpmTaskActionService bpmTaskActionService = AppUtil.getBean(BpmTaskActionService.class);

		Map<String, BoData> boMap = BpmContextUtil.getBoFromContext();

		DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
		//BeanUtils.copyProperties(cmd, actionCmd);
		cmd.addTransitVars(BpmConstants.BO_INST, boMap);
		if (!result.isSkipTask()) {
			cmd.setDestination(destination);
		} else {
			cmd.setDestination(""); 
		}
		cmd.setTaskId(taskId);
		cmd.setBpmIdentities(identityMap);
		cmd.setBusData(actionCmd.getBusData());
		cmd.setDataMode(actionCmd.getDataMode());
		
		cmd.setActionName(TaskActionType.AGREE.getKey());
		String skipType = result.getSkipType();
		cmd.addTransitVars(BpmConstants.BPM_SKIP_TYPE, skipType);
		bpmTaskActionService.finishTask(cmd);
	}

	/**
	 * 获取通知类型。
	 * 
	 * <pre>
	 * 	1.如果流程状态为测试，那么就获取流程定义测试状态的通知类型。
	 *  2.先获取节点的通知类型。
	 *  3.获取流程定义的通知类型。
	 * </pre>
	 * 
	 * @param bpmnDefId
	 * @param nodeId
	 * @return String
	 */
	public static String getNotifyType(BpmProcessInstance instance, String nodeId) {

		Object[] aryObj = getDefProperties(instance, nodeId);
		NodeProperties properties = (NodeProperties) aryObj[0];
		BpmProcessDef procDef = (BpmProcessDef) aryObj[1];
		BpmDefinition bpmDefinition = (BpmDefinition) aryObj[2];

		BpmProcessDefExt ext = procDef.getProcessDefExt();
		BpmDefExtProperties prop = ext.getExtProperties();

		if (BpmDefinition.TEST_STATUS.TEST.equals(bpmDefinition.getTestStatus())) {
			return prop.getTestNotifyType();
		}

		String notifyType = "";
		if (properties != null) {
			notifyType = properties.getNotifyType();
		}
		if (StringUtil.isNotEmpty(notifyType))
			return notifyType;
		notifyType = prop.getNotifyType();

		return notifyType;

	}

	/**
	 * 当没有执行人时发布事件。
	 * 
	 * @param model
	 *            void
	 */
	public static void publishNoExecutorEvent(NoExecutorModel model) {
		NoExecutorEvent ev = new NoExecutorEvent(model);
		AppUtil.publishEvent(ev);
	}

	/**
	 * 设置任务是否跳过。
	 * 
	 * @param bpmTask
	 * void
	 */
	public static void setTaskSkip(BpmTask bpmTask) {
		if (bpmTask.getSkipResult().isHasGetSkip()) return;

		SkipResult skipResult = new SkipResult();
		skipResult.setHasGetSkip(true);

		bpmTask.setSkipResult(skipResult);

		ActionCmd cmd = ContextThreadUtil.getActionCmd();

		BpmProcessDef<BpmProcessDefExt> procDef = BpmUtil.getProcessDefByDefId(bpmTask.getProcDefId());

		BpmDefExtProperties extProperties = procDef.getProcessDefExt().getExtProperties();
	
		// 发起流程判断
		if (cmd instanceof ProcessInstCmd) {
			// 跳过第一个节点。
			boolean skipFirstNode = extProperties.isSkipFirstNode();
			if (skipFirstNode) {
				skipResult.setSkipTask(true);
				skipResult.setSkipType(SkipResult.SKIP_FIRST);
			}
		}
		//任务审批判断。
		//只要跳转规则满足要求，那么就允许任务直接跳过。
		else{
			String skipRules= extProperties.getSkipRules();
			//当跳转规则为空时，查找当前任务属性设置是否为“执行人为空跳过”，如果是，则将跳转规则设置为“SKIP_EMPTY_USER”
			if(StringUtil.isEmpty(skipRules)){
				try {
					BpmProcessInstance instance = (BpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);
					Object[] aryObj = getDefProperties(instance, bpmTask.getNodeId());
					NodeProperties properties = (NodeProperties) aryObj[0];
					if(properties.isSkipExecutorEmpty()){
						skipRules = SkipResult.SKIP_EMPTY_USER;
					}
				} catch (Exception e) {}
			}
			if(StringUtil.isEmpty(skipRules)) return;
			
			String[] aryRules=skipRules.split(",");
			for(String rule:aryRules){
				ISkipCondition condition=  SkipConditionUtil.getSkipConditionByType(rule);
				boolean rtn=condition.canSkip(bpmTask);
				if(!rtn ) continue;
				
				skipResult.setSkipTask(true);
				skipResult.setSkipType(condition.getType());
				break;
			}
		}
		
		
	}

	/**
	 * 根据用户名和ID获取用户对象。
	 * 
	 * @param userId
	 * @param userName
	 * @return User
	 */
	public static IUser getUser(String userId, String userName) {
		IUser user = new User() {
			/**
			 * serialVersionUID
			 * 
			 * @since 1.0.0
			 */
			private static final long serialVersionUID = -3279144470311301256L;

			String userId = "";
			String fullName = "";

			public String getIdentityType() {
				return null;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}

			public void setFullname(String fullName) {
				this.fullName = fullName;
			}

			public void setAccount(String account) {
			}

			public String getUserId() {
				return this.userId;
			}

			public String getPassword() {
				return null;
			}

			public String getMobile() {
				return null;
			}
			
			public Integer getStatus() {
				return null;
			}

			public String getFullname() {
				return this.fullName;
			}

			public String getEmail() {
				return null;
			}

			public String getAccount() {
				return null;
			}

			public void setAttributes(Map<String, String> map) {
			}

			public Map<String, String> getAttributes() {
				return null;
			}

			public boolean isAdmin() {
				return false;
			}

			public void setCreateBy(String createBy) {
			}

			public Date getCreateTime() {
				return null;
			}

			public void setCreateTime(Date createtime) {
			}

			public Date getUpdateTime() {
				return null;
			}

			public void setUpdateTime(Date updatetime) {
			}

			public String getUpdateBy() {
				return null;
			}

			public void setUpdateBy(String updateBy) {
			}

			public String getCreateOrgId() {
				return null;
			}

			public void setCreateOrgId(String createOrgId) {
			}

			public String getCreateBy() {
				return null;
			}

		@Override
			public String getAttrbuite(String key) {
				return "";
			}

		@Override
		public boolean isEnable() {
			// TODO Auto-generated method stub
			return true;
		}
		
		public String getNotifyType(){
			return null;
		}

		};

		user.setUserId(userId);
		user.setFullname(userName);

		return user;
	}

	/**
	 * 根据用户ID返回用户对象。
	 * 
	 * @param userId
	 * @return User
	 */
	public static IUser getUser(String userId) {
		IUserService userService = AppUtil.getBean(IUserService.class);
		IUser user = userService.getUserById(userId);
		return user;
	}

	/**
	 * 根据流程实例数据构建业务表中间数据。
	 * 
	 * @param instance
	 * @return BpmBusLink
	 */
	public static BpmBusLink buildBusLink(BpmProcessInstance instance, BoResult result, String saveMode) {
		//获取顶级的流程实例。
		BpmProcessInstanceManager instanceManager = AppUtil.getBean(BpmProcessInstanceManager.class);
		instance = instanceManager.getTopBpmProcessInstance(instance);
		
		BpmBusLink busLink = buildBusLink(instance);
		busLink.setSaveMode(saveMode);
		busLink.setIsMain(result.getParentId().equals("0") ? 1 : 0);
		// 设置实体数据名称。
		busLink.setBoDefCode(result.getBoCode());

		busLink.setFormIdentify(result.getBoEnt().getName());
		
		if (result.getBoEnt().isPkNumber()) {
			busLink.setBusinesskey(new Long(result.getPk()));
		} else {
			busLink.setBusinesskeyStr(result.getPk());
		}
		if(StringUtil.isNotEmpty(instance.getSysCode())){
			busLink.setSysCode(instance.getSysCode());
		}
		return busLink;

	}
	
	/**
	 * 根据流程实例数据构建业务表中间数据。
	 * 
	 * @param instance
	 * @return
	 */
	public static BpmBusLink buildBusLink(BpmProcessInstance instance) {
		BpmBusLink busLink = new BpmBusLink();

		busLink.setId(UniqueIdUtil.getSuid());
		busLink.setDefId(instance.getProcDefId());
		busLink.setProcInstId(instance.getId());

		IUser curUser = ContextUtil.getCurrentUser();

		busLink.setStartId(curUser.getUserId());
		busLink.setStartor(curUser.getFullname());
		busLink.setCreateTime(new Date());
		busLink.setCreateDate(new Date());
		if(StringUtil.isNotEmpty(instance.getSysCode())){
			busLink.setSysCode(instance.getSysCode());
		}

		return busLink;

	}

	/**
	 * 根据bpmn20流程定义xml文件，流程属性
	 * 
	 * @param flowElement
	 * @param qname
	 * @return
	 */
	public static List<Object> getFlowElementExtension(FlowElement flowElement, QName qname) {
		List<Object> extensions = new ArrayList<Object>();
		ExtensionElements extensionElements = flowElement.getExtensionElements();
		if (extensionElements == null)
			return extensions;
		List<Object> objects = extensionElements.getAny();
		for (Object obj : objects) {
			if (obj instanceof Element) {
				Element el = (Element) obj;// 要相同的命名空间
				QName qn = new QName(el.getNamespaceURI(), el.getLocalName());
				if (qname.equals(qn)) {
					extensions.add(el.getNodeValue());
				}
			}
		}
		return extensions;
	}

	public static Integer getFlowElementOrder(FlowElement flowElement) {
		Integer nodeOrder = 0;
		List<Object> extensions = getFlowElementExtension(flowElement, BPMN20ExtConst._ORDER_QNAME);

		if (BeanUtils.isNotEmpty(extensions)) {
			String s = (String) extensions.get(0);
			if(StringUtil.isNotEmpty(s) && !"null".equals(s)){
				nodeOrder = Integer.parseInt(s);
			}
			
		}
		return nodeOrder;
	}

	/**
	 * 获取按钮，并按照当前节点和任务状态过滤按钮
	 * 
	 * @param bpmNodeDef
	 *            流程节点
	 * @param task
	 *            任务
	 * @param dataObjects
	 *            数据对象
	 * @return
	 */
	public static List<Button> getButtons(BpmNodeDef bpmNodeDef) {
		return getButtons(bpmNodeDef, null);
	}

	/**
	 * 获取按钮，并按照当前节点和任务状态过滤按钮
	 * 
	 * @param bpmNodeDef
	 *            流程节点
	 * @param task
	 *            任务
	 * @param dataObjects
	 *            数据对象
	 * @return
	 */
	public static List<Button> getButtons(BpmNodeDef bpmNodeDef, DefaultBpmTask task) {
		List<Button> buttons = bpmNodeDef.getButtons();
		
		if(task==null) return buttons;
		
		List<Button> buttons2remove = new ArrayList<Button>();

		Map<String,Button> buttonMap= convertToMap(buttons);
		
		String status =task.getStatus();
		
		
		BpmDefExtProperties prop;
		if (bpmNodeDef.getBpmProcessDef().getProcessDefExt() == null) {
			// 当为子流程时获取父流程的扩展属性
			prop = bpmNodeDef.getBpmProcessDef().getParentProcessDef().getProcessDefExt().getExtProperties();
		} else {
			prop = bpmNodeDef.getBpmProcessDef().getProcessDefExt().getExtProperties();
		}

		boolean isAllowTransTo = prop.isAllowTransTo();
		//根据配置的按钮和任务状态获取按钮。
		buttons=getCommonButtons( buttons, status);

		//处理代理按钮
		handDelegateButton(buttonMap,buttons2remove, isAllowTransTo);
		// 处理会签任务
		handSignButtons(task.getTaskId(), bpmNodeDef, buttons, buttons2remove);
		// 处理锁定按钮
		handLockButton(task, buttons, buttons2remove);
		// 处理任务延迟按钮
		handTaskDelay(task.getTaskId(),bpmNodeDef,prop,buttonMap,buttons2remove);

		buttons.removeAll(buttons2remove);
		
		
		return buttons;
	}
	

	
	private static void handTaskDelay(String taskId,BpmNodeDef bpmNodeDef,BpmDefExtProperties prop,Map<String,Button> buttonMap,List<Button> buttons2remove) {
		if(!buttonMap.containsKey("taskDelay")){
			return ;
		}
		BpmTaskDueTimeManager bpmTaskDueTimeManager = AppUtil.getBean(BpmTaskDueTimeManager.class);
		ICalendarService iCalendarService = AppUtil.getBean(ICalendarService.class);
		BpmTaskDueTime bpmTaskDueTime = bpmTaskDueTimeManager.getByTaskId(taskId);
		if(BeanUtils.isEmpty(bpmTaskDueTime) && buttonMap.containsKey("taskDelay") ){
			buttons2remove.add(buttonMap.get("taskDelay"));
			return;
		}
		int remainingTime=0;
		if("caltime".equals(bpmTaskDueTime.getDateType())){
			// getSecondDiff 秒
			remainingTime = TimeUtil.getSecondDiff(new Date(), bpmTaskDueTime.getStartTime())/60;
		}else{
			// getWorkTimeByUser 毫秒
			remainingTime =(int) (iCalendarService.getWorkTimeByUser(bpmTaskDueTime.getUserId(), bpmTaskDueTime.getStartTime(), new Date())/60000);
		}
		remainingTime = bpmTaskDueTime.getDueTime() - remainingTime;
		if(remainingTime<=0 && buttonMap.containsKey("taskDelay")){
			buttons2remove.add(buttonMap.get("taskDelay"));
		}
		
	}

	/**
	 * 处理转交按钮。
	 * @param buttons
	 * @param buttons2remove
	 * @param isAllowTransTo	是否允许转办。
	 */
	private static void handDelegateButton(Map<String,Button> buttonMap,List<Button>  buttons2remove,boolean isAllowTransTo ){
		boolean rtn=buttonMap.containsKey("delegate");
		if(!isAllowTransTo && rtn){
			buttons2remove.add(buttonMap.get("delegate"));
		}
	}
	
	
	/**
	 * 根据任务状态和节点按钮定义获取按钮。
	 * @param isAllowTransTo
	 * @param buttonsDef
	 * @param status
	 * @return
	 */
	private static List<Button> getCommonButtons(List<Button> buttonsDef, String status){
		List<Button> buttons=new ArrayList<Button>();
		
		TaskActionHandlerConfig config = (TaskActionHandlerConfig) AppUtil.getBean("taskActionHandlerConfig");
		List<? extends TaskActionHandlerDef> listDef= config.getAllActionHandlerDefList();
		
		List<String> allActions=new ArrayList<String>();
		for(TaskActionHandlerDef def:listDef){
			allActions.add(def.getName());
		}
		
		//计算根据任务状态获取应该包含的按钮。
		Map<String,String> defButtonsMap=(Map<String, String>) AppUtil.getBean("buttonsMap");
		//节点上可以允许的按钮。
		String btnsStr=defButtonsMap.get(status);
		
		String[] aryDefault = null;
		if(btnsStr!=null){
			aryDefault=btnsStr.split(",");
		}
		
		//得出自定义的按钮。
		for(Button btn:buttonsDef){
			if(!allActions.contains(btn.getAlias()) || isInDefault(btn.getAlias(),aryDefault)){
				buttons.add(btn);
			}
		}
		
		return buttons;
	}
	
	/**
	 * 按钮是否在默认的按钮中。
	 * @param alias
	 * @param aryDefault
	 * @return
	 */
	private static boolean isInDefault(String alias,String[] aryDefault){
		for(String tmp:aryDefault){
			if(tmp.equals(alias))
				return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 将按钮列表转换成map。
	 * @param buttons
	 * @return
	 */
	private static Map<String,Button> convertToMap(List<Button> buttons){
		Map<String,Button> btnMap=new HashMap<String, Button>();
		for(Button btn:buttons){
			btnMap.put(btn.getAlias(), btn);
		}
		return btnMap;
	}
	 

	/**
	 * 处理会签按钮。
	 * 
	 * @param task
	 * @param bpmNodeDef
	 * @param buttons
	 * @param buttons2remove
	 */
	private static void handSignButtons(String taskId, BpmNodeDef bpmNodeDef, List<Button> buttons, List<Button> buttons2remove) {
		if (!bpmNodeDef.getType().equals(NodeType.SIGNTASK)||StringUtil.isEmpty(taskId)) return;
		// 处理会签任务
		NatTaskService natTaskService = (NatTaskService) AppUtil.getBean(NatTaskService.class);
		SignService signService = (SignService) AppUtil.getBean(SignService.class);
		Map<String, Object> variables = natTaskService.getVariables(taskId);
		List<PrivilegeMode> privilege = signService.getPrivilege(ContextUtil.getCurrentUserId(), (SignNodeDef) bpmNodeDef, variables);
		// 即没有全部特权又没有加签特权时移除加签按钮
		if (!privilege.contains(PrivilegeMode.ALL) && !privilege.contains(PrivilegeMode.ALLOW_ADD_SIGN)) {
			for (Button bnt : buttons) {
				if ("addSign".equals(bnt.getAlias())) {
					buttons2remove.add(bnt);
					break;
				}
			}
		}
	}

	/**
	 * 单独处理锁定按钮。
	 * 
	 * @param task
	 * @param buttons
	 * @param buttons2remove
	 */
	private static void handLockButton(DefaultBpmTask task, List<Button> buttons, List<Button> buttons2remove) {
		if (task == null) return;
		
		String taskId = task.getTaskId();
		//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，
		// 			4,被其他人锁定,5:这种情况一般是管理员操作，所以不用出锁定按钮。
		BpmTaskManager taskManager = (BpmTaskManager) AppUtil.getBean(BpmTaskManager.class);
		int canLock = taskManager.canLockTask(taskId);
		Button button = null;
		for (Button btn : buttons) {
			if (btn.getAlias().equals("lockUnlock")) {
				button = btn;
			}
		}
		if (button == null) return;
		// 删除锁定按钮
		if (canLock == 0 || canLock == 4 || canLock == 5 || canLock == 2) {
			buttons2remove.add(button);
		}
		

		if (canLock == 1) {
			button.setName("锁定");
		}
		if (canLock == 3) {
			button.setName("解锁");
		}

	}

	/**
	 * 获取节点的属性设置
	 * 
	 * <pre>
	 * 子流程会获取实例对应父流程的节点属性设置
	 * </pre>
	 * 
	 * @param instance
	 * @param nodeId
	 * @return
	 */
	public static NodeProperties getNodeProperties(BpmProcessInstance instance, String nodeId) {
		Object[] defProperties = getDefProperties(instance, nodeId);
		Object obj = defProperties[0];
		if (BeanUtils.isNotEmpty(obj) && obj instanceof NodeProperties) {
			return (NodeProperties) obj;
		}
		return null;
	}
	
	
	/**
	 * 处理表单意见。
	 * 
	 * <pre>
	 * 1.没有提交表单数据，不做处理。
	 * 2.如果表单数据中不包含表单意见项不做处理。
	 * 3.如果表单中包含意见项，只管一个表单意见
	 * 	设置：
	 *  cmd.setOpinionIdentity(opinionName);
	 *  cmd.setApprovalOpinion(opinion);
	 *  
	 *  {
	 *  __form_opinion:{"caiwu":"意见数据"}
	 *  }
	 * </pre>
	 * 
	 * @param request
	 * @param cmd
	 *            void
	 */
	public static void handOpinion(String data, DefaultTaskFinishCmd cmd) {
		if (StringUtil.isEmpty(data)) return;
		JSONObject dataJson = JSONObject.parseObject(data);
		if (!dataJson.containsKey(DefaultBpmCheckOpinion.OPINION_FLAG)) return;
		
		JSONObject opinionJson = dataJson.getJSONObject(DefaultBpmCheckOpinion.OPINION_FLAG);
		Iterator<String> keys = opinionJson.keySet().iterator();
		while (keys.hasNext()) {
			String opinionName = keys.next();
			String opinion = opinionJson.getString(opinionName);
			if (StringUtil.isNotEmpty(opinion)) {
				cmd.setOpinionIdentity(opinionName);
				cmd.setApprovalOpinion(opinion);
				break;
			}
		}
		dataJson.remove(DefaultBpmCheckOpinion.OPINION_FLAG);
		data = dataJson.toString();
		cmd.setBusData(data);
	}

	/**
	 * 验证handler输入是否是否有效。
	 * 
	 * <pre>
	 * 	handler 输入规则。
	 *  spring的 serviceId +“." + 方法名称。
	 * </pre>
	 * 
	 * @param handler
	 *            spring 的serviceId + "." + 方法名
	 * @param parameterTypes 
	 * @param args 
	 * @return 0 有效，-1，格式不对，-2 没有找到service类，-3没有找到对应的方法，-4，未知的错误。
	 */
	public static int isHandlerValidNoCmd(String handler, Class<?>[] parameterTypes) {

		if (handler.indexOf(".") == -1)
			return -1;
		String[] aryHandler = handler.split("[.]");
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		Object serviceBean = null;
		try {
			serviceBean = AppUtil.getBean(beanId);
		} catch (Exception ex) {
			return -2;
		}
		if (serviceBean == null)
			return -2;

		try {
			Method invokeMethod = serviceBean.getClass().getMethod(method, parameterTypes);
			if (invokeMethod != null) {
				return 0;
			} else {
				return -3;
			}
		} catch (NoSuchMethodException e) {
			return -3;
		} catch (Exception e) {
			return -4;
		}
	}
	
	
	public static void restfulPluginExecut(DefaultBpmTask task,EventType eventType){
		BpmDefinitionAccessor bpmDefinitionAccessor = (BpmDefinitionAccessor) AppUtil.getBean("bpmDefinitionAccessor");
		BpmPluginFactory bpmPluginFactory = (BpmPluginFactory) AppUtil.getBean("bpmPluginFactory");
		RestfulService restfulService = (RestfulService) AppUtil.getBean(RestfulService.class);
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(task.getProcDefId());
		//获取全局的restful接口事件
		List<BpmPluginContext> pluginContextList=bpmProcessDef.getProcessDefExt().getBpmPluginContexts();
		if(BeanUtils.isNotEmpty(pluginContextList)){
			for(BpmPluginContext bpmPluginContext:pluginContextList){
				BpmPluginDef bpmPluginDef =bpmPluginContext.getBpmPluginDef();
				if(bpmPluginDef instanceof BpmExecutionPluginDef){
					BpmExecutionPluginDef bpmExecutionPluginDef = (BpmExecutionPluginDef)bpmPluginDef;
					BpmExecutionPlugin bpmExecutionPlugin = bpmPluginFactory.buildExecutionPlugin(bpmPluginContext, eventType);
					if(bpmExecutionPlugin!=null){
						if(bpmPluginContext.getEventTypes().contains(eventType)){
							if(bpmExecutionPluginDef instanceof IGlobalRestfulPluginDef){
								IGlobalRestfulPluginDef restfulPluginDef = (IGlobalRestfulPluginDef) bpmExecutionPluginDef;
								List<Restful> restfuls = restfulPluginDef.getRestfulList();
								if(BeanUtils.isNotEmpty(restfuls)){
									restfulService.outTaskPluginExecute(task, restfuls,eventType);
								}
							}
						}
					}	
				}
			}
		}
		
		//执行节点的任务类插件
		BpmNodeDef bpmNodeDef= bpmDefinitionAccessor.getBpmNodeDef( task.getProcDefId(), task.getNodeId());
		for(BpmPluginContext bpmPluginContext:bpmNodeDef.getBpmPluginContexts()){
			//事件为空则跳过。
			if(BeanUtils.isEmpty(bpmPluginContext.getEventTypes())) continue;
			
			BpmPluginDef bpmPluginDef = bpmPluginContext.getBpmPluginDef();
			if(bpmPluginDef instanceof BpmTaskPluginDef){
				BpmTaskPluginDef bpmTaskPluginDef = (BpmTaskPluginDef)bpmPluginDef;
				BpmTaskPlugin bpmTaskPlugin = bpmPluginFactory.buildTaskPlugin(bpmPluginContext, eventType);
				if(bpmTaskPlugin==null) continue;
				if(bpmPluginContext.getEventTypes().contains(eventType)){
					if(bpmTaskPluginDef instanceof IGlobalRestfulPluginDef){
						IGlobalRestfulPluginDef restfulPluginDef = (IGlobalRestfulPluginDef) bpmTaskPluginDef;
						List<Restful> restfuls = restfulPluginDef.getRestfulList();
						if(BeanUtils.isNotEmpty(restfuls)){
							restfulService.outTaskPluginExecute(task, restfuls,eventType);
						}
					}
				}
			
			}
		}
	}
	
	/**
	 * 获取驳回上一步的目标节点
	 * @param taskId
	 * @return "" 不可驳回   ， 非空返回可驳回到上一步节点的名称
	 */
	public static String getRejectPreDestination(String taskId){

		boolean canRejectPreAct=true;//是否可以驳回到上一步
		
		BpmTaskManager bpmTaskManager = (BpmTaskManager) AppUtil.getBean("bpmTaskManager");
		DefaultBpmTask task = bpmTaskManager.get(taskId);

		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		
		boolean canReject = false;
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class); 
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<Button> buttons = BpmUtil.getButtons(taskNodeDef, task);
		for (Button button : buttons) {
			if ("reject".equals(button.getAlias())) 
				canReject = true;
		}
	    
		if(!canReject) return "";
		
		NodeProperties nodeProperties=taskNodeDef.getLocalProperties();
		String backMode= nodeProperties.getBackMode();
		if(StringUtil.isEmpty(backMode))backMode="normal";
		String procInstId = task.getProcInstId();
		
		List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(procInstId, task.getNodeId(), "pre");
		// 允许直来直往的节点
		List<BpmNodeDef> bpmExeStacksUserNode = new ArrayList<BpmNodeDef>();
		// 允许按流程图执行的节点
		List<BpmNodeDef> bpmExeStacksGoMapUserNode = new ArrayList<BpmNodeDef>();
		BpmExeStackRelationDaoImpl relationDao =  (BpmExeStackRelationDaoImpl) AppUtil.getBean("bpmExeStackRelationDaoImpl");
		List<BpmExeStackRelation> relationList= relationDao.getListByProcInstId(procInstId);
		for (BpmNodeDef node : listBpmNodeDef) {
			if ((node.getType().equals(NodeType.USERTASK)||node.getType().equals(NodeType.SIGNTASK))&&!node.getNodeId().equals(nodeId)) {
				bpmExeStacksUserNode.add(node);

				boolean isHavePre = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "pre",relationList);
				boolean isHaveAfter = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "after",relationList);
				if (!(isHavePre && isHaveAfter)) {
					bpmExeStacksGoMapUserNode.add(node);
				}else{
					List<BpmNodeDef> incomeNodes = node.getIncomeNodes();
					if(BeanUtils.isNotEmpty(incomeNodes)){
						BpmNodeDef nodeDef = incomeNodes.get(0);
						//如果是从开始网关进入的用户节点，则允许按流程图驳回
						if(node.getType().equals(NodeType.USERTASK) && (nodeDef.getType().equals(NodeType.START)||nodeDef.getType().equals(NodeType.USERTASK))){
							bpmExeStacksGoMapUserNode.add(node);
						}
					}
				}
			}
		}
		canRejectPreAct=bpmExeStacksGoMapUserNode.size()>0||bpmExeStacksUserNode.size()>0;
		
		if(!canRejectPreAct){
			return "";
		}
		
		if("direct".equals(backMode)){
			return bpmExeStacksUserNode.get(0).getNodeId();
		}else{
			return  bpmExeStacksGoMapUserNode.get(0).getNodeId();
		}
	
	}
}
