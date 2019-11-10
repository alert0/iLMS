package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.inv.model.InvWareHouseModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:仓库管理业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvWareHouseManager extends Manager<String, InvWareHouseModel>{
	/**
	 * 仓库信息分页查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	PageList<InvWareHouseModel> queryWareHouseForPage(InvWareHouseModel model, Page page)throws Exception;
	/**
	 * 获取仓库类型业务接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	List<DictVO> getWareType()throws Exception;
	/**
	 * 根据ID查询仓库数据业务接口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	InvWareHouseModel getWareHouseById(String id)throws Exception;
	/**
	 * 添加仓库信息业务接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void createWareHouse(InvWareHouseModel model)throws Exception;
	/**
	 * 修改仓库信息业务接口
	 * @param model
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void updateWareHouse(InvWareHouseModel model)throws Exception;
	/**
	 * 单条/批量数据删除业务接口
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void deleteWareHouseByIds(String factoryCode,String[] ids,String ipAddr)throws Exception;

}
