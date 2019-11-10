package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmApprovalItem;

/**
 * 
 * <pre> 
 * 描述：常用语管理 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 10:56:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmApprovalItemManager extends Manager<String, BpmApprovalItem>{

	//添加常用语
	void addTaskApproval(BpmApprovalItem bpmApprovalItem);
	//获取常用语
	public List<String> getApprovalByDefKeyAndTypeId(String defKey,String typeIdPath);
	
}
