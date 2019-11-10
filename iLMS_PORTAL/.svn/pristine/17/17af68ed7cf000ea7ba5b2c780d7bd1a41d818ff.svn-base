package com.hotent.bpmx.plugin.task.userassign;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.exception.UserCalcException;
import com.hotent.bpmx.api.helper.identity.IConditionCheck;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.usercalc.UserCalcHelper;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <pre> 
 * 描述：人员条件规则设置计算
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-8-下午2:47:50
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class UserConditionCheck implements IConditionCheck {
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	public boolean check(String condition, String mode,BpmUserCalcPluginSession session) {
		try { 
			if(StringUtil.isEmpty(condition))return true;
			
			JSONArray conditionList = JSONArray.fromObject(condition);
			
			return groovyScriptEngine.executeBoolean(calConditions(conditionList,session), null);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserCalcException("人员条件表达式解析异常！" +e.getMessage());
		}
	}
	/***
	 *  计算每条规则的Boolean 值 ,返回类似：  (true&&false||true)  的字符串
	 *  让 脚本根据算术优先级进行运算返回结果。
	 * @param conditionList
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private String calConditions(JSONArray conditionList,BpmUserCalcPluginSession session) throws Exception{
		if(conditionList.size()==0) return "true";
		
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(int i =0; i< conditionList.size();i++){
			JSONObject conditionParam = conditionList.getJSONObject(i);
			
			if(i!= 0) 
				sb.append(getCompType(conditionParam));//运算类型
			
			//如果含子项
			if(conditionParam.containsKey("sub")){
				JSONArray subConditions = JSONArray.fromObject(conditionParam.getJSONArray("sub"));
				sb.append(calConditions(subConditions,session));
			}//如果单一表达式
			else{
				if(calculate(conditionParam,session))  sb.append("true");
				else sb.append("false");
			}
		}
		sb.append(")");
		
		
		return sb.toString();
	}
	
	private String getCompType(JSONObject conditionParam) {
		if(!conditionParam.containsKey("compType")) return "";
		
		String compType = conditionParam.getString("compType");
		if(compType.equals("and")) {
			return "&&";
		}
		else {
			return "||";  
		}   
		
	}
	/*计算单一条件 */
	private boolean calculate(JSONObject condition, BpmUserCalcPluginSession session) throws Exception{
		int ruleType =condition.getInt("ruleType");
		// 脚本类型 的计算
		if(ruleType == 2){
			String script = condition.getString("script");
			Map variables = session.getVariables();
			
			Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
			
			if(BeanUtils.isNotEmpty(boMap)){
				variables.putAll(boMap); 
			}
			
			return groovyScriptEngine.executeBoolean(script,variables);
		}
		
		String expression = condition.getString("expression");
		// 条件类型的 计算 
		JSONObject  executorVarJson = condition.getJSONObject("executorVar");
		ExecutorVar executorVar = (ExecutorVar) JSONObject.toBean(executorVarJson, ExecutorVar.class);
		
		String executorVarValue = executorVar.getValue();
		List<String> keys = UserCalcHelper.calcVarValue(executorVar, session,false);
		
		//流程变量，Bo
		String variable = StringUtil.convertArrayToString(keys.toArray(new String[]{})).trim();
		
		if("notEquals".equals(expression)) 
			return !executorVarValue.equals(variable);
		
		if("equals".equals(expression)) 
			return executorVarValue.equals(variable);
		
		if("contains".equals(expression)) 
			return executorVarValue.contains(variable);
		
		if("notContains".equals(expression)) 
			return !executorVarValue.contains(variable);
		
		
		String dataType = condition.getString("dataType");
		boolean isDate = "date".equals(dataType);
	
		//数字，日期的       > <  >= <=大小判断
		{
			if(isDate){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(executorVarValue);
				Date valueData = sdf.parse(variable);
				int diff = valueData.compareTo(date);
				
				if(">".equals(expression)){
					return diff > 0;
				}else if("<".equals(expression)){
					return diff < 0;
				}else if("<=".equals(expression)){
					return diff <= 0;
				}
				else if(">=".equals(expression)){
					return diff >= 0;
				}
			}
			
			// 如果不是日期就为数字类型，直接 拼装条件表达式，让脚本执行
			return groovyScriptEngine.executeBoolean(executorVarValue+expression+variable,session.getVariables());
		}
		
	}
	
}


