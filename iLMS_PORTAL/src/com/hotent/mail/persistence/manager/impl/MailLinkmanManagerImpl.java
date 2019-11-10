package com.hotent.mail.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.mail.persistence.dao.MailLinkmanDao;
import com.hotent.base.core.mail.model.MailLinkman;
import com.hotent.mail.persistence.manager.MailLinkmanManager;

/**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("mailLinkmanManager")
public class MailLinkmanManagerImpl extends AbstractManagerImpl<String, MailLinkman> implements MailLinkmanManager{
	@Resource
	MailLinkmanDao mailLinkmanDao;
	@Override
	protected Dao<String, MailLinkman> getDao() {
		return mailLinkmanDao;
	}
	
	/**
	 * 根据邮箱地址找到联系人
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public MailLinkman findLinkMan(String address,String userId)throws Exception
	{
		return mailLinkmanDao.findLinkMan(address,userId);
	}
	
	/**
	 * 找到当前用户下的最近联系人
	 * @param userId
	 * @return
	 */
	public List<MailLinkman> getAllByUserId(String userId,String condition) {
		Map params = new HashMap<String,Object>();
		params.put("USERID", userId);
		params.put("condition", condition);
		return mailLinkmanDao.getAllByUserId(params);
	}
	
}
