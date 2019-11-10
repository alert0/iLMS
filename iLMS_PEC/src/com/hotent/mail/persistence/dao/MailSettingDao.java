package com.hotent.mail.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.MailSetting;

/**
 * 
 * <pre> 
 * 描述：外部邮件用户设置 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:52:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailSettingDao extends Dao<String, MailSetting> {
	
	/**
	 * 根据邮箱地址返回相应的邮箱配置实体
	 * @param mailAddress
	 * @return
	 */
	public MailSetting getMailByAddress(String address);
	
	/**
	 * 根据当前用户ID获得邮箱列表
	 * @param userId
	 * @return
	 */
	public List<MailSetting> getMailByUserId(String userId);
	
	/**
	 * 获得当前用户的默认邮箱
	 * @param userId
	 * @return
	 */
	public MailSetting getByIsDefault(String userId);
	
	/**
	 * 根据当前用户ID获得外部邮箱的分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<MailSetting> getAllByUserId(QueryFilter queryFilter);
	
	/**
	 * 验证邮箱地址的唯一性
	 * @param address
	 * @return
	 */
	public int getCountByAddress(String address);
	
	/**
	 * 更改默认邮箱
	 * @param mail
	 * @return
	 */
	public int updateDefault(MailSetting mail);
	
	/**
	 * 统计当前用户设置的外部邮箱数量
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId);
	
}
