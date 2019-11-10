package com.hotent.bpmx.api.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;

/**
 * 流程上下文数据工具类。
 * @author ray
 *
 */
public class BpmContextUtil {
	/**
	 * 获取上下文的bo实例数据。
	 * @return
	 */
	public static Map<String,BoData> getBoFromContext(){
		ActionCmd cmd =ContextThreadUtil.getActionCmd();
		if(cmd==null) return null;
		Map<String,BoData> boMap= (Map<String, BoData>) cmd.getTransitVars(BpmConstants.BO_INST);
		return boMap;
	}
	
	/**
	 * 将bo数据放到上下文。
	 * 这种试用在没有cmd的环境中。
	 * @param boDatas
	 */
	public static void setBoToContext(List<BoData> boDatas){
		ActionCmd cmd =new BaseActionCmd();
		ContextThreadUtil.setActionCmd(cmd);
		
		Map<String,BoData> boMap=new HashMap<String, BoData>();
		
		for(BoData data:boDatas){
			String code=data.getBoDef().getAlias();
			boMap.put(code, data);
		}
		cmd.addTransitVars(BpmConstants.BO_INST, boMap);
		
	}
	
}
