package com.hotent.bpmx.core.engine.execution.sign.handler;

import javax.annotation.Resource;

import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.plugin.core.execution.sign.SignResult;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;

/**
 * 获取处理器。
 * <pre> 
 * 在会签中驳回时只能按流程图返回，不能选择直来直往模式
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-30-下午4:28:15
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BackSignActionHandler extends AbstractSignActionHandler{
	
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;

	@Override
	public SignResult handByActionType(TaskFinishCmd cmd, BpmDelegateExecution bpmDelegateExecution) {
		SignResult result = new SignResult(true, NodeStatus.BACK,OpinionStatus.SIGN_BACK_CANCEL);
		return result;
	}
}