package com.hanthink.sys.dao;
import java.util.List;

import com.hanthink.sys.model.SysPdaMenuModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：生产日历查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysPdaMenuDao extends Dao<String, SysPdaMenuModel> {

	/**
	 * 左侧查询
	 * <p>return: PageList<SysPdaMenuModel></p>  
	 * <p>Description: SysPdaMenuDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	PageList<SysPdaMenuModel> querySysPdaMenuForPage(SysPdaMenuModel model, DefaultPage p);

	/**
	 * 右侧查询
	 * <p>return: PageList<SysPdaMenuModel></p>  
	 * <p>Description: SysPdaMenuDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	PageList<SysPdaMenuModel> getRowClick(String idWorkCalendar, DefaultPage p);

	/**
	 * 根据用户名查询菜单权限
	 * <p>return: PageList<SysPdaMenuModel></p>  
	 * <p>Description: SysPdaMenuDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 * @param menuDesc 
	 */
	PageList<SysPdaMenuModel> queryAddPdaMenuByUserName(String userName, String menuDesc, Page p);

	/**
	 * 添加菜单权限
	 * <p>return: void</p>  
	 * <p>Description: SysPdaMenuDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	void addPdaMenu(List<SysPdaMenuModel> pdaMenuList);

	/**
	 * 删除菜单权限
	 * <p>return: void</p>  
	 * <p>Description: SysPdaMenuDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	void deletePdaMenu(List<SysPdaMenuModel> modelList);
	
	
	
}
