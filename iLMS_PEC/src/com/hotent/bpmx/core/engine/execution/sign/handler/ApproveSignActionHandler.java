package com.hotent.bpmx.core.engine.execution.sign.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.DecideType;
import com.hotent.bpmx.api.constant.FollowMode;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.constant.VoteResult;
import com.hotent.bpmx.api.constant.VoteType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.SignRule;
import com.hotent.bpmx.api.plugin.core.execution.sign.SignResult;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.SignService;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.model.BpmSignData;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 会签完成处理器。
 * <pre> 
 *  1.首先判断是否为直接通过。
 *  	如果为直接通过则，返回会签结果为同意或反对完成。
 *  	需要注意的是 actionName分别为: agreeDirect,opposeDirect。
 *  2.按照规则进行判定流程是否结束。
 *  	1.首先判定一票通过特权。
 *  	2.没有特权则按设定的投票规则进行处理。
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-30-上午9:29:07
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ApproveSignActionHandler extends AbstractSignActionHandler{
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	SignService signService;
	@Resource
	BpmSignDataManager bpmSignDataManager;
	@Resource
	BpmFormService bpmFormService;

	@Override
	public SignResult handByActionType(TaskFinishCmd cmd,
			BpmDelegateExecution bpmDelegateExecution) {
		
		BpmProcessInstance instance= (BpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);
		
		String nodeId=bpmDelegateExecution.getNodeId();
		
		SignNodeDef nodeDef=(SignNodeDef)bpmDefinitionAccessor.getBpmNodeDef(instance.getProcDefId(), nodeId);
		//
		String actionName=cmd.getActionName();
		
		
		String direct=(String) cmd.getTransitVars(BpmConstants.SIGN_DIRECT);
		//直接完成
		if(StringUtil.isNotEmpty(direct) && "1".equals( direct)){
			SignResult result=handDirect(actionName);
			return result;
		}
	
		//按照规则进行处理。
		SignResult result= handByRule(cmd,nodeDef,bpmDelegateExecution,instance);
		
		
		return result;
	}
	
	/**
	 * 处理直接完成。
	 * @param actionName
	 * @return  SignResult
	 */
	private SignResult handDirect(String actionName){
		SignResult result=new SignResult(true, NodeStatus.AGREE,OpinionStatus.SIGN_PASS_CANCEL);
		if(!actionName.equals(OpinionStatus.AGREE.getKey())){
			result=new SignResult(true, NodeStatus.OPPOSE,OpinionStatus.SIGN_NOPASS_CANCEL);
		}
		return result;
	}
	
	/**
	 * 按照流程规则进行处理。
	 * @param cmd
	 * @param nodeDef
	 * @param bpmDelegateExecution
	 * @return SignResult
	 */
	private SignResult handByRule(TaskFinishCmd cmd ,SignNodeDef nodeDef,BpmDelegateExecution bpmDelegteExecution,BpmProcessInstance processInstance){
		
		String nodeId=bpmDelegteExecution.getNodeId();
		boolean isParallel=!MultiInstanceType.SEQUENTIAL.equals(bpmDelegteExecution.multiInstanceType());
		String executeId=isParallel?bpmDelegteExecution.getParentExecution().getParentExecution().getId():bpmDelegteExecution.getParentId();
		
		List<BpmSignData> list= bpmSignDataManager.getVoteByExecuteNode(executeId, nodeId,1);
		int signSize=list.size();
		int aggreeCount=getAmount(list,VoteResult.AGREE);
		int opposeCount=getAmount(list,VoteResult.OPPOSE);
		int notVoteAmount=getAmount(list, VoteResult.NO);
	
		boolean isFinished=notVoteAmount==0;
		SignRule rule=nodeDef.getSignRule();
		//获取用户是否有一票的特权。
		SignResult result=getByOneTicket(cmd,nodeDef,bpmDelegteExecution,processInstance);

		if(result!=null){
			return result;
		}
		//如果特权为空，则根据规则进行计算。
		result=getResult(rule,aggreeCount,opposeCount,signSize,isFinished);
		/**
		完成规则的处理。
			如果满足以下条件：
			1.会签规则的完成模式为等待。
			2.如果会签没有全部投完票。
			3.会签结果为完成。
		那么标记会签结果为未完成。
		*/
		if(FollowMode.WAIT.equals(rule.getFollowMode()) && !isFinished &&   result.isComplete()){
			result.setComplete(false);
		}
		
		return result;
		
	}
	
	/**
	 * 有一票通过特权的人获取会签结果。
	 * <pre>
	 * 1.如果投票意见不为同意或反对的话则返回。
	 * 2.判断当前人是否有一票特权。
	 * 		如果有如果之前有特权人投过票的话，那么该特权人的投票信息不影响之前的人投票结果。
	 * 		否则在流程变量中获取投票结果。
	 * 3.从流程变量中获取投票结果。
	 * 		如果流程变量不为空的话则根据投票结果返回SignResult对象。
	 * </pre>
	 * @param cmd
	 * @param nodeDef
	 * @param bpmDelegteExecution
	 * @return SignResult
	 */
	private SignResult getByOneTicket(TaskFinishCmd cmd ,SignNodeDef nodeDef,BpmDelegateExecution bpmDelegteExecution,BpmProcessInstance bpmProcessInstance){
		String actionName=cmd.getActionName();
		//如果不是或反对则直接返回为空。
		if(!OpinionStatus.AGREE.getKey().equals(actionName) 
				&& !OpinionStatus.OPPOSE.getKey().equals(actionName)) return null;
		boolean hasOneTicket=getHasOneTicket(bpmDelegteExecution,nodeDef,bpmProcessInstance);
		
		if(!hasOneTicket) 	return null;
		
		
		String resultVarName=BpmConstants.SIGN_RESULT + bpmDelegteExecution.getNodeId();
		String resultVar=(String) bpmDelegteExecution.getVariable(resultVarName);
		
		//是否有一票特权，如果有并且之前没有设置过会签结果。
		if(hasOneTicket){
			if(StringUtil.isEmpty(resultVar)){
				bpmDelegteExecution.setVariable(resultVarName,actionName );
			}
		}
		//取得会签的结果。
		resultVar=(String) bpmDelegteExecution.getVariable(resultVarName);
		
		if(StringUtil.isNotEmpty(resultVar)){
			SignResult result=new SignResult(true, NodeStatus.AGREE,OpinionStatus.SIGN_PASS_CANCEL);
			if(OpinionStatus.OPPOSE.getKey().equals(resultVar)){
				result=new SignResult(true, NodeStatus.OPPOSE,OpinionStatus.SIGN_NOPASS_CANCEL);
				return result;
			}
			return result;
		}
		return null;
	}
	
	
	/**
	 * 根据票数和投票规则获取会签投票结果。
	 * @param rule
	 * @param agreeAmount
	 * @param opposeAmount
	 * @param totalAmount
	 * @param isFinished
	 * @return SignResult
	 */
	private SignResult getResult(SignRule rule,int agreeAmount,int opposeAmount,int totalAmount, boolean isFinished){
		SignResult result=new SignResult();
		DecideType decideType=rule.getDecideType();
	
		int voteAmount=rule.getVoteAmount();
		//投票决策方式为通过。
		if(DecideType.AGREE.getKey().equals(decideType.getKey())){
			if(VoteType.PERCENT.getKey().equals(rule.getVoteType().getKey())){
				agreeAmount=(int)((float)agreeAmount/totalAmount *100);
			}
			//如果投票完成，但是同意票数没有达到设定票数，则认为不通过。
			if(agreeAmount>=voteAmount){
				result.setComplete(true);
				result.setNodeStatus(NodeStatus.AGREE);
				result.setOpinionStatus(OpinionStatus.SIGN_PASS_CANCEL);
			}
			else if(isFinished){
				result.setComplete(true);
				result.setNodeStatus(NodeStatus.OPPOSE);
				result.setOpinionStatus(OpinionStatus.SIGN_NOPASS_CANCEL);
			}
		}
		//决策方式不通过
		else{
			if(VoteType.PERCENT.getKey().equals(rule.getVoteType().getKey())){
				opposeAmount=(int)((float)opposeAmount/totalAmount *100);
			}
			//如果投票完成，但是反对票数没有达到设定票数，则认为不通过。
			if(opposeAmount>=voteAmount){
				result.setComplete(true);
				result.setNodeStatus(NodeStatus.OPPOSE);
				result.setOpinionStatus(OpinionStatus.SIGN_NOPASS_CANCEL);
			}
			else if(isFinished){
				result.setComplete(true);
				result.setNodeStatus(NodeStatus.AGREE);
				result.setOpinionStatus(OpinionStatus.SIGN_PASS_CANCEL);
			}
		}
		return result;
	}
	
	/**
	 * 根据投票结果计算不同类型的投票次数。
	 * <pre>
	 * 1.通过票数
	 * 2.反对票数
	 * 3.还未投票的数量。
	 * </pre>
	 * @param list
	 * @param voteResult
	 * @return int
	 */
	private int getAmount(List<BpmSignData> list,VoteResult voteResult){
		int count=0;
		for(BpmSignData signData:list){
			if(voteResult.getKey() .equals(signData.getVoteResult())){
				count++;
			}
		}
		return count;
	}

	
	/**
	 * 判定当前人是否有一票特权。
	 * @param bpmDelegteExecution
	 * @param nodeDef
	 * @return  boolean
	 */
	private boolean getHasOneTicket(BpmDelegateExecution bpmDelegteExecution,SignNodeDef nodeDef,BpmProcessInstance bpmProcessInstance){
		
		IUser user=ContextUtil.getCurrentUser();
		String userId=user.getUserId();
		Map<String, Object> vars=bpmDelegteExecution.getVariables();
		List<PrivilegeMode> list= signService.getPrivilege(userId, nodeDef, vars);
		if(list.contains(PrivilegeMode.ALL) || list.contains(PrivilegeMode.ONETICKET)) return true;
		return false;
	}

	
	

}
