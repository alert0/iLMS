package com.hotent.bpmx.listener;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.ProcessInstCmd;
import com.hotent.bpmx.api.constant.AopType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.BpmStartEvent;
import com.hotent.bpmx.api.event.BpmStartModel;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 流程启动监听器。
 * 
 * <pre>
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-6-下午4:33:16
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class BpmStartEventListener implements ApplicationListener<BpmStartEvent>, Ordered {


	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource(name="defaultBpmFormService")
	BpmFormService bpmFormService;

	@Override
	public void onApplicationEvent(BpmStartEvent ev) {
		BpmStartModel model = (BpmStartModel) ev.getSource();
		//设置上下文。
		setBuinessKey(model);
		
		if (AopType.PREVIOUS.equals(model.getAopType())) {
			before(model);
		} else {
			after(model);
		}

	}
	
	/**
	 * 将主键放到上下文中。
	 * <pre>
	 *  适用情况，iframe的情况。
	 *  1.直接启动流程是businessKey为空。
	 *  2.在保存草稿的时候从实例中获取主键，放到cmd中。
	 *  在编写处理器时，可以根据这个值判断到底增加数据还是更新数据。
	 * </pre>
	 * @param model
	 */
	private void setBuinessKey(BpmStartModel model) {
		ActionCmd cmd = model.getCmd();
		if (cmd instanceof ProcessInstCmd) {
			String dataMode=cmd.getDataMode();
			if (!ActionCmd.DATA_MODE_PK.equals(dataMode)) return;
			
			DefaultBpmProcessInstance inst = (DefaultBpmProcessInstance) model.getBpmProcessInstance();
			
			String pk=cmd.getBusinessKey();
			String pkInst=inst.getBizKey();
			if(StringUtil.isEmpty(pk) && StringUtil.isNotEmpty(pkInst)){
				cmd.setBusinessKey(pkInst);
				cmd.setSysCode(inst.getSysCode());
			}
		}
	}

	private void before(BpmStartModel model) {
		// 执行前置处理器
		executeHandler(model, true);
		// 添加业务表的中间数据库表
		addBusLink(model);
	}

	/**
	 * 根据流程实例获取发起节点的属性配置。
	 * 
	 * @param instance
	 * @return NodeProperties
	 */
	private NodeProperties getStartProperties(BpmProcessInstance instance) {
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(instance.getProcDefId());

		BpmNodeDef bpmNodeDef = bpmProcessDef.getStartEvent();

		NodeProperties properties = bpmNodeDef.getLocalProperties();

		if (properties != null && ( StringUtil.isNotEmpty(properties.getPrevHandler()) || StringUtil.isNotEmpty(properties.getPostHandler())) ) 	return properties;

		List<BpmNodeDef> bpmNodeDefs = bpmProcessDef.getStartNodes();
		for (BpmNodeDef nodeDef : bpmNodeDefs) {
			if (nodeDef instanceof UserTaskNodeDef) {
				String parentInstId = instance.getParentInstId();
				if (StringUtil.isZeroEmpty(parentInstId)) {
					properties = nodeDef.getLocalProperties();
				} else {
					BpmProcessInstance parentInst = bpmProcessInstanceManager.get(parentInstId);
					String defKey = parentInst.getProcDefKey();
					properties = nodeDef.getPropertiesByParentDefKey(defKey);
				}
				break;
			}
		}
		return properties;
	}

	/**
	 * 执行节点处理器。
	 * 
	 * @param model
	 * @param isBefore
	 *            void
	 */
	private void executeHandler(BpmStartModel model, boolean isBefore) {
		BpmProcessInstance instance = model.getBpmProcessInstance();
		ActionCmd cmd = model.getCmd();
		// 获取发起节点获取不到则获取第一个节点。
		NodeProperties properties = getStartProperties(instance);

		BusDataUtil.executeHandler(properties, cmd, isBefore);
		
	}

	/**
	 * 添加关联数据。 TODO方法名称描述
	 * 
	 * @param model
	 *            void
	 * @throws ParseException 
	 */
	private void addBusLink(BpmStartModel model) {
		ActionCmd cmd = model.getCmd();
		if (cmd instanceof ProcessInstCmd) {

			DefaultBpmProcessInstance inst = (DefaultBpmProcessInstance) model.getBpmProcessInstance();
			
			String dataMode=cmd.getDataMode();
			
			// 设置数据模式。
			inst.setDataMode(cmd.getDataMode());
			if(StringUtil.isNotEmpty(cmd.getSysCode())){
				inst.setSysCode(cmd.getSysCode());
			}
			// bo数据处理
			if (ActionCmd.DATA_MODE_BO.equals(dataMode)) {
				BusDataUtil.handSaveBoData(model.getBpmProcessInstance(), cmd); 
			}
			// 键名 键值。
			else if (ActionCmd.DATA_MODE_PAIR.equals(dataMode)) {
				BusDataUtil.handExt(cmd);
			}
			//主键模式。
			else if (ActionCmd.DATA_MODE_PK.equals(dataMode)) {
				String pk=cmd.getBusinessKey();
				String pkInst=inst.getBizKey();
				if(StringUtil.isNotEmpty(pk) && StringUtil.isEmpty(pkInst)){
					inst.setBizKey(pk);
				}
			}
			
		}
	}

	
	private void after(BpmStartModel model) {
		 
		// 执行后置处理器
		executeHandler(model, false);
		
		// 记录流程实例表单
		handleInstForm(model);
		
		
		
	}
	
	// 流程启动后， 记录每个节点的表单  pc端和手机端的表单， 当流程结束或者终止时， 删除相应记录
	private void handleInstForm(BpmStartModel model) {
		BpmProcessInstance instance = model.getBpmProcessInstance();
		bpmFormService.handleInstForm(instance.getId(), instance.getProcDefId(),false);
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
