package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.inv.dao.InvWareHouseDao;
import com.hanthink.inv.model.InvWareHouseModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:仓库管理持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvWareHouseDaoImpl extends MyBatisDaoImpl<String, InvWareHouseModel>
				implements InvWareHouseDao{

	@Override
	public String getNamespace() {
		return InvWareHouseModel.class.getName();
	}
	/**
	 * 仓库信息分页查询业务持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public List<InvWareHouseModel> queryWareHouseForPage(InvWareHouseModel model, Page page) throws Exception {
		return this.getByKey("queryWareHouseForPage", model, page);
	}
	/**
	 * 获取仓库类型业务持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getWareType(Map<String, String> paramMap) throws Exception {
		return (List<DictVO>) this.getList("getWareType", paramMap);
	}
	/**
	 * 根据ID查询仓库数据业务持久层实现方法
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public InvWareHouseModel getWareHouseById(String id) throws Exception {
		return (InvWareHouseModel) this.getOne("getWareHouseById", id);
	}
	/**
	 * 添加仓库信息业务持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void createWareHouse(InvWareHouseModel model) throws Exception {
		this.sqlSessionTemplate.insert("createWareHouse", model);
	}
	/**
	 * 查询数据库是否存在相同仓库代码持久层实现方法
	 * @param wareCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public List<InvWareHouseModel> queryWareHouseByWareCode(InvWareHouseModel model) throws Exception {
		return this.getByKey("queryWareHouseByWareCode",model);
	}
	/**
	 * 修改仓库信息业务持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void updateWareHouse(InvWareHouseModel model) throws Exception {
		this.updateByKey("updateWareHouse", model);
	}
	/**
	 * 单条/批量删除数据业务持久层实现方法
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void deleteWareHouseByIds(String[] ids) throws Exception {
		this.deleteByKey("deleteWareHouseByIds", ids);
	}
}
