package com.hanthink.sw.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwUserDao;
import com.hanthink.sw.manager.SwUserManager;
import com.hanthink.sw.model.SwUserModel;
import com.hotent.base.db.api.Dao;
@Service("UserManager")
public class SwUserManagerImpl extends AbstractManagerImpl<String, SwUserModel>
implements SwUserManager{

	@Resource
	private SwUserDao dao;
	@Override
	protected Dao<String, SwUserModel> getDao() {
		
		return dao;
	}
	
	/**
	 * 
	 * @Description: 根据当前登录账号获取登录用户对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月23日 下午2:31:22
	 */
	@Override
	public SwUserModel getUserModel(String account) {
		
		return dao.getUserModel(account);
	}

}
