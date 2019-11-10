package com.hotent.mail.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.mail.persistence.dao.MailLinkmanDao;
import com.hotent.base.core.mail.model.MailLinkman;

/**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MailLinkmanDaoImpl extends MyBatisDaoImpl<String, MailLinkman> implements MailLinkmanDao{

    @Override
    public String getNamespace() {
        return MailLinkman.class.getName();
    }

	@Override
	public MailLinkman findLinkMan(String address, String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("USERID", userId);
		params.put("LINKADDRESS", address);
		return this.getUnique("findLinkMan", params);
	}

	@Override
	public List<MailLinkman> getAllByUserId(Map params) {
		String statement="getAllByUserId_"+this.getDbType();
		return this.getByKey(statement,  params);
	}
	
}

