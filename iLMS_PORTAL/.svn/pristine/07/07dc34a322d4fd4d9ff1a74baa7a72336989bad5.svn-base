package com.hotent.bpmx.activiti.id;

import org.activiti.engine.impl.cfg.IdGenerator;

/**
 * 
 * <pre> 
 * 描述：ID 产生器
 * 构建组：x5-bpmx-activiti
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-25-下午4:22:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ActivitiIdGenerator implements IdGenerator {
	
	private com.hotent.base.db.api.IdGenerator idGenerator=null;
	
	public void setIdGenerator(com.hotent.base.db.api.IdGenerator idGenerator){
		this.idGenerator=idGenerator;
	}
	
	@Override
	public String getNextId() {
		return idGenerator.getSuid();
	}
	
}
