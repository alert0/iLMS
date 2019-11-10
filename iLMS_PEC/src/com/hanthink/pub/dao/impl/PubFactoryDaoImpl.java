package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubFactoryDao;
import com.hanthink.pub.model.PubFactoryModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: PubFactoryDaoImpl
 * @Description: 工厂维护界面DaoImpl
 * @author dtp
 * @date 2019年5月29日
 */
@Repository
public class PubFactoryDaoImpl extends MyBatisDaoImpl<String, PubFactoryModel> implements PubFactoryDao{

	@Override
	public String getNamespace() {
		return PubFactoryModel.class.getName();
	}

	/**
	 * @Description: 查询工厂
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PubFactoryModel> queryFactoryPage(PubFactoryModel model, DefaultPage page) {
		return (PageList<PubFactoryModel>) this.getList("queryFactoryPage", model, page);
	}

	/**
	 * @Description:  新增工厂 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void insertFactory(PubFactoryModel model) {
		this.insertByKey("insertFactory", model);
	}

	/**
	 * @Description: 更新工厂  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void updateFactory(PubFactoryModel model) {
		this.updateByKey("updateFactory", model);
	}

	/**
	 * @Description: 删除工厂  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void deleteFactory(PubFactoryModel model) {
		this.deleteByKey("deleteFactory", model);
	}

	/**
	 * @Description: 分页查询工厂对应账号   
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubFactoryModel> queryFactoryUserList(PubFactoryModel model, Page p) {
		return this.getListByKey("queryFactoryUserList", model, p);
	}

	/**
	 * @Description: 查询非当前工厂的系统用户账号   
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubFactoryModel> queryNotThisFactorySystemUserPage(PubFactoryModel model, Page p) {
		return this.getListByKey("queryNotThisFactorySystemUserPage", model,  p);
	}

	/**
	 * @Description: 工厂新增账号  
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public void addFactoryUser(List<PubFactoryModel> list) {
		for (PubFactoryModel pubFactoryModel : list) {
			this.insertByKey("addFactoryUser", pubFactoryModel);
		}
	}

	/**
	 * @Description:  删除工厂下账号
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public void deleteFactoryUser(List<PubFactoryModel> list) {
		for (PubFactoryModel pubFactoryModel : list) {
			this.deleteByKey("deleteFactoryUser", pubFactoryModel);
		}
	}

	/**
	 * @Description: 新增前判断工厂代码是否唯一  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月31日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubFactoryModel> queryIsExistsFactoryCode(PubFactoryModel model) {
		return this.getListByKey("queryIsExistsFactoryCode", model);
	}

}
