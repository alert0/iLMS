package com.hotent.bpmx.plugin.usercalc.cusers.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.expression.spel.ast.InlineList;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.exception.UserCalcException;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.api.service.DataObjectHandler;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.UserCalcHelper;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;
import com.hotent.bpmx.plugin.usercalc.cusers.def.CusersPluginDef;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IOrgService;
import com.hotent.sys.util.ContextUtil;

public class CusersPlugin extends AbstractUserCalcPlugin{

	@Override
	public List<BpmIdentity> queryByPluginDef(
			BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		
		List<BpmIdentity> list=new ArrayList<BpmIdentity>();
		ActionCmd action= ContextThreadUtil.getActionCmd();
		CusersPluginDef def=( CusersPluginDef)pluginDef;
		IOrgService orgEngine= pluginSession.getOrgEngine();
		Map<String, Object> vars= pluginSession.getVariables();
		String source=def.getSource();
		
		if("start".equals(source)){
			String startId=(String)vars.get(BpmConstants.START_USER);
			IUser user= orgEngine.getUserService().getUserById(startId);
			BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
			list.add(bpmIdentity);
		}
		if("currentUser".equals(source)){
			IUser user= orgEngine.getUserService().getUserById(ContextUtil.getCurrentUser().getUserId());
			BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
			list.add(bpmIdentity);
		}
		else if("prev".equals(source)){
			String userId=ContextUtil.getCurrentUser().getUserId();
			IUser user= orgEngine.getUserService().getUserById(userId);
			BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
			list.add(bpmIdentity);
		}
		else if("spec".equals(source)){
			String userKeys=def.getAccount();
			String[] aryAccount=userKeys.split(",");
			for(String account:aryAccount){
				IUser user= orgEngine.getUserService().getUserByAccount(account);
				BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
				list.add(bpmIdentity);
			}
		}else if("var".equals(source)){
			ExecutorVar executorVar = def.getVar();
			
			//预览模式       （所有参数都是ID）
			if(isPreviewMode()){
				if(ExecutorVar.EXECUTOR_TYPE_USER.equals(executorVar.getExecutorType())){
					String userId = (String) vars.get(executorVar.getName());
					IUser user= orgEngine.getUserService().getUserById(userId);
					BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
					list.add(bpmIdentity);
				}
				return list;
			}
			
			//user变量，如果bo类型    取bo的值，如果是流程变量取流程变量的值
			if(ExecutorVar.EXECUTOR_TYPE_USER.equals(executorVar.getExecutorType())){
				if(ExecutorVar.SOURCE_BO.equals(executorVar.getSource())){
					String [] BOData =  executorVar.getName().split("\\.");
					if(BOData.length != 2) throw new UserCalcException("BO["+executorVar.getName()+"]数据 格式不合法");
				
					
//					String pk = (String)databoject.get(BOData[1]);
			 
					List<String> listName= UserCalcHelper.calcVarValue(executorVar, pluginSession, false);
					for (String keyName : listName) {
						IUser user;
						String pk =keyName;
						if("account".equals(executorVar.getUserValType())){
							  user = orgEngine.getUserService().getUserByAccount(pk);
						}
						else{
							user= orgEngine.getUserService().getUserById(pk);
						}	
						BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
						list.add(bpmIdentity);
					} 
					
				}else if(ExecutorVar.SOURCE_FLOW_VAR.equals(executorVar.getSource())){
					String PK=(String)vars.get(executorVar.getName());
					//如果流程变量为发起人，则默认按ID查找
					if(executorVar.getName().equals(BpmConstants.START_USER)){
						executorVar.setUserValType("id");
					}
					String[] PKs=PK.split(",");
					for(String pk:PKs){
						IUser user;
						if("account".equals(executorVar.getUserValType())){
							user= orgEngine.getUserService().getUserByAccount(pk);
						}
						else{
							user= orgEngine.getUserService().getUserById(pk);
						}
						BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
						list.add(bpmIdentity);
					}
				} 
			}
				
			
		}
		return list;
	}
	

}
