package com.hotent.mail.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.mail.persistence.dao.MailSettingDao;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.MailSetting;

/**
 * 
 * <pre> 
 * 描述：外部邮件用户设置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:52:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MailSettingDaoImpl extends MyBatisDaoImpl<String, MailSetting> implements MailSettingDao{

    @Override
    public String getNamespace() {
        return MailSetting.class.getName();
    }
	
    /**
	 * 根据邮箱地址返回相应的邮箱配置实体
	 * @param mailAddress
	 * @return
	 */
	public MailSetting getMailByAddress(String address) {
		return this.getUnique("getMailByAddress", address);
	}
	
	/**
	 * 根据当前用户ID获得邮箱列表
	 * @param userId
	 * @return
	 */
	public List<MailSetting> getMailByUserId(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return getByKey("getMailByUserId",params);
	}
	
	/**
	 * 获得当前用户的默认邮箱
	 * @param userId
	 * @return
	 */
	public MailSetting getByIsDefault(String userId) {
		return this.getUnique("getByIsDefault", userId);
	}
	
	/**
	 * 根据当前用户ID获得外部邮箱的分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<MailSetting> getAllByUserId(QueryFilter queryFilter) {
		return this.getByKey("getAllByUserId", queryFilter);
	}
	
	/**
	 * 验证邮箱地址的唯一性
	 * @param address
	 * @return
	 */
	public int getCountByAddress(String address) {
		return (Integer)this.getOne("getCountByAddress", address);
	}
	
	/**
	 * 更改默认邮箱
	 * @param mail
	 * @return
	 */
	public int updateDefault(MailSetting mail) {
		return this.updateByKey("updateDefault", mail);
	}
	
	/**
	 * 统计当前用户设置的外部邮箱数量
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId) {
		return (Integer)this.getOne("getCountByUserId", userId);
	}

}

