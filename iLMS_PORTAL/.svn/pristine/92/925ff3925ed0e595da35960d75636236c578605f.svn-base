package com.hotent.mail.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.core.mail.model.MailSeting;

/**
 * 
 * <pre> 
 * 描述：外部邮件用户设置 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:52:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailSettingManager extends Manager<String, MailSetting>{
	
	/**
	 * 测试外部邮箱的smtp，pop3/imap配置是否通过;
	 * @param MailSetting
	 * @throws Exception
	 */
	public void testConnection(MailSetting mailSetting) throws Exception;
	
	/**
	 * 设置默认邮箱
	 * @param MailSetting
	 * @throws Exception
	 */
	public void setDefault(MailSetting mailSetting)throws Exception;
	
	/**
	 * 验证设置的邮箱地址的唯一性
	 * @param MailSetting
	 * @return
	 * @throws Exception
	 */
	public boolean isExistMail(String id,MailSetting mailSetting)throws Exception;
	
	/**
	 * 根据邮箱地址返回相应的邮箱配置实体
	 * @param address
	 * @return
	 */
	public MailSetting getMailByAddress(String address);
	
	/**
	 * 获取用户的默认邮箱
	 * @param userId
	 * @return
	 */
	public MailSetting getByIsDefault(String userId);
	
	/**
	 * 获取当前用户的邮箱列表
	 * @param userId
	 * @return
	 */
	public List<MailSetting> getMailByUserId(String userId);
	
	/**
	 * 获取当前用户的邮箱分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<MailSetting> getAllByUserId(QueryFilter queryFilter);

	/**
	 * 获取用户的邮件数
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId);

	public void delAllByIds(String[] lAryId);
	
}
