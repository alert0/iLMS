package com.hotent.mail.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.base.core.mail.model.MailLinkman;

/**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailLinkmanDao extends Dao<String, MailLinkman> {
	
	/**
	 * 根据联系人邮箱地址找到相应的联系人实体
	 * @param address
	 * @return
	 */
	public MailLinkman findLinkMan(String address,String userId);
	
	/**
	 * 根据当前用户id查询前20条最近联系人
	 * @param userId
	 * @return
	 */
	public List<MailLinkman> getAllByUserId(Map params);
}
