package com.hanthink.inv.dao;

import java.util.List;

import com.hanthink.inv.model.InvUnloadPortModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:卸货口管理持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvUnloadPortDao extends Dao<String, InvUnloadPortModel>{
	/**
	 * 卸货口数据查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvUnloadPortModel> queryUnloadPortForPage(InvUnloadPortModel model, Page page)throws Exception;
	/**
	 * 查询数据Excel导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvUnloadPortModel> queryUnloadPortForExport(InvUnloadPortModel model)throws Exception;
	/**
	 * 逐条/批量删除卸货口数据
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	void removeUnloadPort(String[] ids)throws Exception;
	/**
	 * 判断卸货口数据是否存在
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	boolean isExistsUnload(InvUnloadPortModel model)throws Exception;
	/**
	 * 判断仓库是否存在
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	boolean isExistsWareCode(InvUnloadPortModel model)throws Exception;
	/**
	 * 新增卸货口数据
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	void createUnloadport(InvUnloadPortModel model)throws Exception;
	/**
	 * 修改卸货口数据
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年12月8日
	 */
	void updateUnloadPort(InvUnloadPortModel model)throws Exception;
	/**
	 * 查询仓库列表
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvUnloadPortModel> queryWareCodeLsit(String factoryCode)throws Exception;
	List<InvUnloadPortModel> queryLogicUnload(String factoryCode)throws Exception;
	boolean unloadPortIsUsedInLocation(String[] ids)throws Exception;
	boolean isUsedInLocation(InvUnloadPortModel model)throws Exception;

}
