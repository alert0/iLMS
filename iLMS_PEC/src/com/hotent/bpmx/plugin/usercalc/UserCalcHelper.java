package com.hotent.bpmx.plugin.usercalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.exception.UserCalcException;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.api.service.DataObjectHandler;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IOrgService;
/**
 * 用户计算帮助类
 * @author miao
 *
 */
public class UserCalcHelper {
	/**
	 * 计算流程变量值       将参数和值进行匹配 （将流程变量的值抽取出来）
	 * @param executorVar
	 * @param pluginSession
	 * @param turnKeys2Ids 是否将key转成 Id ，account类型的转成id返回
	 * @return  userIds  or  groupIds
	 */
	public static List<String> calcVarValue(ExecutorVar executorVar,BpmUserCalcPluginSession pluginSession,boolean turnKeys2Ids){
		Map<String, Object> vars= pluginSession.getVariables();
		IOrgService orgEngine= pluginSession.getOrgEngine();
		
		List<String> ids = new ArrayList<String>();  /// 需要返回的 id  List
		
		//预览模式     (所有参数都是ID)
		if(AbstractUserCalcPlugin.isPreviewMode()){
			String Id = (String) vars.get(executorVar.getName());
			ids.add(Id);
			return ids;
		}
		
		// 非预览模式
		String PK = ""; //从流程变量，或者bo中获取值 中间变量
		
		//非预览模式
		if(ExecutorVar.SOURCE_BO.equals(executorVar.getSource())){
			String [] BOData =  executorVar.getName().split("\\.");
			if(BOData.length != 2) throw new UserCalcException("BO["+executorVar.getName()+"]数据 格式不合法");
			
			Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
			BoData boData = boMap.get(BOData[0]);
			PK =boData.getString(BOData[1]);
			 
		} 
		//流程变量
		else if(ExecutorVar.SOURCE_FLOW_VAR.equals(executorVar.getSource())){
			PK =(String) vars.get(executorVar.getName());
		}
		
		String [] PKs=PK.split(",");
		if(BeanUtils.isEmpty(PK)) return Collections.emptyList(); 
		
		//如果是固定值，返回值
		if(executorVar.getExecutorType().equals("fixed")){
			ids.addAll(Arrays.asList(PKs));
			return ids;
		}
		
		//用户参数
		if(ExecutorVar.EXECUTOR_TYPE_USER.equals(executorVar.getExecutorType())){
			if("userId".equals(executorVar.getUserValType()) || !turnKeys2Ids){
				ids.addAll(Arrays.asList(PKs));
			} //为账号且需要将账号转换成ID
			else {
				for(String account : PKs){
					IUser u = orgEngine.getUserService().getUserByAccount(account);
					if(u!=null) ids.add(u.getUserId());
				}
			}
		 //组参数
		 }else{
			//id的形式的数据
			if("groupId".equals(executorVar.getGroupValType()) || !turnKeys2Ids)
				ids.addAll(Arrays.asList(PKs));
			//key并且需要将key转换成ID
			else{
				String dimension = executorVar.getDimension();
				for(String groupKey : PKs){
					IGroup group = orgEngine.getUserGroupService().getByCode(dimension,groupKey);
					if(group!=null) ids.add(group.getGroupId());
				}
			}
		 }
		
		return ids;
	}
}
