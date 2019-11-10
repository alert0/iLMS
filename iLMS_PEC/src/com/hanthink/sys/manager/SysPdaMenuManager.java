package com.hanthink.sys.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.sys.model.SysPdaMenuModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：数据权限基础数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysPdaMenuManager extends Manager<String, SysPdaMenuModel>{

	/**
	 * 分页查询
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<SysPdaMenuModel> querySysPdaMenuForPage(SysPdaMenuModel model, DefaultPage p);

	/**
	 * 右侧查询显示
	 * @param idWorkCalendar
	 * @param p
	 * @return
	 */
	PageList<SysPdaMenuModel> getRowClick(String idWorkCalendar, DefaultPage p);

	/**
	 * 查询待添加的PDA菜单权限信息
	 * <p>return: PageList<Map<String,Object>></p>  
	 * <p>Description: SysPdaMenuManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 * @param menuDesc 
	 */
	PageList<SysPdaMenuModel> queryAddPdaMenuByUserName(String userName, String menuDesc, Page p);

	/**
	 * 添加菜单权限
	 * <p>return: void</p>  
	 * <p>Description: SysPdaMenuManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	void addPdaMenu(List<SysPdaMenuModel> pdaMenuList);

	/**
	 * 删除菜单权限
	 * <p>return: void</p>  
	 * <p>Description: SysPdaMenuManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	void deletePdaMenu(List<SysPdaMenuModel> modelList);

	



	
}
