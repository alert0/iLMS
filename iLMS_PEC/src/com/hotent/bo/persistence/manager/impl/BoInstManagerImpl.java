package com.hotent.bo.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.persistence.dao.BoInstDao;
import com.hotent.bo.persistence.manager.BoInstManager;
import com.hotent.bo.persistence.model.BoInst;

/**
 * 
 * <pre> 
 * 描述：业务对象实例 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-11 09:52:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("boInstManager")
public class BoInstManagerImpl extends AbstractManagerImpl<String, BoInst> implements BoInstManager{
	@Resource
	BoInstDao boInstDao;
	@Override
	protected Dao<String, BoInst> getDao() {
		return boInstDao;
	}
}
