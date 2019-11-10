package com.hotent.bpmx.persistence.util;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.bpmx.api.model.process.nodedef.JumpRule;
import com.hotent.bpmx.api.service.JumpRuleCalc;

/**
 * 跳转规则计算。
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-27-上午9:18:43
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class JumpRuleCalcImpl implements JumpRuleCalc {
	
	private Log logger=LogFactory.getLog(JumpRuleCalcImpl.class);
	
	@Resource
	GroovyScriptEngine groovyScriptEngine  ;

	@Override
	public String eval(List<? extends JumpRule> jumpRuleList, Map<String, Object> params) {
		for(JumpRule rule:jumpRuleList){
			String condition=rule.getCondition();
			Boolean rtn= groovyScriptEngine.executeBoolean(condition, params);
			if(rtn==null){
				logger.debug("条件表达式返回为空，请使用return 语句返回!");
			}
			else if(rtn){
				return rule.getTargetNode();
			}
		}
		return "";
	}

}
