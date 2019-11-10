package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.inv.model.InvWareHouseModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:仓库管理持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvWareHouseDao extends Dao<String, InvWareHouseModel>{
	/**
	 * 仓库信息分页查询业务持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	List<InvWareHouseModel> queryWareHouseForPage(InvWareHouseModel model, Page page)throws Exception;
	/**
	 * 获取仓库类型业务持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	List<DictVO> getWareType(Map<String, String> paramMap)throws Exception;
	/**
	 * 根据ID查询仓库数据业务持久层接口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	InvWareHouseModel getWareHouseById(String id)throws Exception;
	/**
	 * 添加仓库信息业务持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void createWareHouse(InvWareHouseModel model)throws Exception;
	/**
	 * 查询数据库是否存在相同仓库代码持久层接口
	 * @param wareCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	List<InvWareHouseModel> queryWareHouseByWareCode(InvWareHouseModel model)throws Exception;
	/**
	 * 修改仓库信息业务持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void updateWareHouse(InvWareHouseModel model)throws Exception;
	/**
	 * 单条/批量删除数据业务持久层接口
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	void deleteWareHouseByIds(String[] ids)throws Exception;

}
