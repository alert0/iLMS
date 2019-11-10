package com.hanthink.sw.dao.impl;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwUserDao;
import com.hanthink.sw.model.SwUserModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
@Repository
public class SwUserDaoImpl extends MyBatisDaoImpl<String, SwUserModel>
implements SwUserDao{

	@Override
	public String getNamespace() {
		
		return SwUserModel.class.getName();
	}

	/**
	 * 
	 * @Description: 根据当前登录账号获取登录对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月23日 下午2:32:37
	 */
	@Override
	public SwUserModel getUserModel(String account) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getUserModel", account);
	}

}
