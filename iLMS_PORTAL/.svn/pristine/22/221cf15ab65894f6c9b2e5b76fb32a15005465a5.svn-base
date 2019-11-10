package com.hotent.mail.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.mail.persistence.dao.MailSettingDao;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.core.mail.MailUtil;
import com.hotent.core.mail.model.MailSeting;
import com.hotent.mail.persistence.manager.MailManager;
import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：外部邮件用户设置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:52:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("mailSettingManager")
public class MailSettingManagerImpl extends AbstractManagerImpl<String, MailSetting> implements MailSettingManager{
	@Resource
	MailSettingDao mailSettingDao;
	@Resource
	MailManager mailManager;
	@Override
	protected Dao<String, MailSetting> getDao() {
		return mailSettingDao;
	}
	
	
	
	/**
	 * 测试外部邮箱的smtp，pop3/imap配置是否通过;
	 * @param mailSetting
	 * @throws Exception
	 */
	public void testConnection(MailSetting mailSetting) throws Exception{
		MailSeting seting = getBymailSetting(mailSetting);
		MailUtil mailUtil = new MailUtil(seting);
		mailUtil.connectSmtpAndReceiver();
	}
	
	/**
	 * 根据mailSetting对象，得到MailSeting对象<br/>
	 * 注：mailSetting对象中的密码字段应为使用EncryptUtil.encrypt方法加密后的密文
	 * @param mailSetting
	 * @return
	 * @throws Exception
	 */           
	public static MailSeting getBymailSetting(MailSetting mailSetting)throws Exception{
		MailSeting seting = new MailSeting();
		String protocal = mailSetting.getMailType();
		seting.setSendHost(mailSetting.getSmtpHost());
		seting.setSendPort(mailSetting.getSmtpPort());
		seting.setProtocal(protocal);
		seting.setMailAddress(mailSetting.getMailAddress());
		seting.setPassword(EncryptUtil.decrypt(mailSetting.getPassword()));
		seting.setNickName(mailSetting.getNickName());
		seting.setSSL(mailSetting.getSSL());
		seting.setValidate(mailSetting.getValidate());
		seting.setIsDeleteRemote(mailSetting.getIsDeleteRemote());
		seting.setIsHandleAttach(mailSetting.getIsHandleAttach());
		if("pop3".equals(protocal)){
			seting.setReceiveHost(mailSetting.getPopHost());
			seting.setReceivePort(mailSetting.getPopPort());
		}else{
			seting.setReceiveHost(mailSetting.getImapHost());
			seting.setReceivePort(mailSetting.getImapPort());
		}
		return seting ;
	}
	
	/**
	 * 设置默认邮箱
	 * @param mailSetting
	 * @throws Exception
	 */
	public void setDefault(MailSetting mailSetting)throws Exception{
		MailSetting mail=mailSettingDao.getByIsDefault(ContextUtil.getCurrentUserId());
		if (BeanUtils.isNotEmpty(mail)) {
			mail.setIsDefault((short) 0);
			mailSettingDao.updateDefault(mail);
		}
		mailSettingDao.updateDefault(mailSetting);
	}
	
	/**
	 * 验证设置的邮箱地址的唯一性
	 * @param mailSetting
	 * @return
	 * @throws Exception
	 */
	public boolean isExistMail(String id,MailSetting mailSetting)throws Exception{
		String address=mailSetting.getMailAddress();
		int result=mailSettingDao.getCountByAddress(address);
		if(StringUtil.isNotEmpty(id)){
			MailSetting mail= get(id);
			if(result!=0&&!address.equals(mail.getMailAddress()))
				return true;
			return false;
		}
		if(result!=0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据邮箱地址返回相应的邮箱配置实体
	 * @param address
	 * @return
	 */
	public MailSetting getMailByAddress(String address) {
		return mailSettingDao.getMailByAddress(address);
	}
	
	/**
	 * 获取用户的默认邮箱
	 * @param userId
	 * @return
	 */
	public MailSetting getByIsDefault(String userId) {
		return mailSettingDao.getByIsDefault(userId);
	}	
	
	/**
	 * 获取当前用户的邮箱列表
	 * @param userId
	 * @return
	 */
	public List<MailSetting> getMailByUserId(String userId) {
		return mailSettingDao.getMailByUserId(userId);
	}
	
	/**
	 * 获取当前用户的邮箱分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<MailSetting> getAllByUserId(QueryFilter queryFilter) {
		return mailSettingDao.getAllByUserId(queryFilter);
	}

	/**
	 * 获取用户的邮件数
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId) {
		return mailSettingDao.getCountByUserId(userId);
	}

	public void delAllByIds(String[] lAryId) {
		for(String setId:lAryId){
			remove(setId);
			mailManager.remove(setId);
		}
		
	}
	
}
