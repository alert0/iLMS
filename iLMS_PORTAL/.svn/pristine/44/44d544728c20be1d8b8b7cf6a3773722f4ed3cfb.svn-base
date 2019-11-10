package com.hotent.mail.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.core.mail.model.MailLinkman;

/**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailLinkmanManager extends Manager<String, MailLinkman>{
	
	/**
	 * 根据邮箱地址找到联系人
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public MailLinkman findLinkMan(String address,String userId)throws Exception;
	
	/**
	 * 找到当前用户下的最近联系人
	 * @param userId
	 * @return
	 */
	public List<MailLinkman> getAllByUserId(String userId,String condition);
	
}
