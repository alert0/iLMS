package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvUnloadPortModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:卸货口管理业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvUnloadPortManager extends Manager<String, InvUnloadPortModel>{
	/**
	 * 卸货口数据查询业务接口
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	PageList<InvUnloadPortModel> queryUnloadPortForPage(InvUnloadPortModel model, Page page)throws Exception;
	/**
	 * 查询数据Excel导出业务接口
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvUnloadPortModel> queryUnloadPortForExport(InvUnloadPortModel model)throws Exception;
	/**
	 * 逐条/批量删除卸货口数据
	 * @param ids
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	void removeUnloadPort(String[] ids, String ipAddr)throws Exception;
	/**
	 * 卸货口新增/修改
	 * @param list
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	String saveUnloadPortEdit(List<Map<String, String>> list, String ipAddr)throws Exception;
	/**
	 * 查询仓库列表
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvUnloadPortModel> queryWareCodeLsit()throws Exception;
	/**
	 * 查询物理卸货口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月18日
	 */
	List<InvUnloadPortModel> queryLogicUnload()throws Exception;

}
