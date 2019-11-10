package com.hotent.sys.jms.handler;

import java.util.List;

import com.hotent.sys.api.msg.model.DefaultMsgVo;

public class TaoBaoUtil {
	
	/**
	 * 构建变量json。
	 * @param vo
	 * @return
	 */
	public static String buildParams(DefaultMsgVo vo){
		// 构建参数
		String parmString = "{";
		List<String> parmList = vo.getParms();
		int n = parmList.size();
		int index = 0;
		for (String parm : parmList) {
			index++;
			if (vo.getExtendVars().containsKey(parm)) {
				parmString += parm + ":'" + vo.getExtendVars().get(parm).toString()+"'";
				if (index != n) {
					parmString += ",";
				}
			}
		}
		parmString += "}";
		
		return parmString;
	}

}
