package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.PubFactoryModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: PubFactoryManager
 * @Description: 工厂Manager
 * @author dtp
 * @date 2019年5月29日
 */
public interface PubFactoryManager extends Manager<String, PubFactoryModel>{

	/**
	 * @Description: 查询工厂
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	PageList<PubFactoryModel> queryFactoryPage(PubFactoryModel model, DefaultPage page);

	/**
	 * @Description: 新增工厂   
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	void insertFactory(PubFactoryModel model, String ipAddr);

	/**
	 * @Description: 更新工厂 
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	void updateFactory(PubFactoryModel model, String ipAddr);

	/**
	 * @Description:  删除工厂   
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	void deleteFactory(PubFactoryModel model, String ipAddr);

	/**
	 * @Description: 分页查询工厂对应账号 
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	List<PubFactoryModel> queryFactoryUserList(PubFactoryModel model, Page p);

	/**
	 * @Description: 查询非当前工厂的系统用户账号   
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	List<PubFactoryModel> queryNotThisFactorySystemUserPage(PubFactoryModel model, Page p);

	/**
	 * @Description: 工厂新增账号 
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	void addFactoryUser(List<PubFactoryModel> list);

	/**
	 * @Description: 删除工厂下账号 
	 * @param: @param modelList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	void deleteFactoryUser(List<PubFactoryModel> modelList);

	/**
	 * @Description: 新增前判断工厂代码是否唯一  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月31日
	 */
	List<PubFactoryModel> queryIsExistsFactoryCode(PubFactoryModel model);

	
}
