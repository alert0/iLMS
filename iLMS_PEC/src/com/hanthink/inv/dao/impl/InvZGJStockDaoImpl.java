package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvZGJStockDao;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.inv.model.InvZGJStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * @ClassName: InvZGJStockDaoImpl
 * @Description: 支给件库存管理DaoImpl
 * @author dtp
 * @date 2019年4月9日
 */
@Repository
public class InvZGJStockDaoImpl extends MyBatisDaoImpl<String, InvZGJStockModel> implements
		InvZGJStockDao{

	@Override
	public String getNamespace() {
		return InvZGJStockModel.class.getName();
	}

	/**
	 * 分页查询业务持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvZGJStockModel> queryStockForPage(InvZGJStockModel model, Page page) throws Exception {
		return (List<InvZGJStockModel>) this.getList("queryStockForPage", model, page);
	}
	/**
	 * 单条数据详情查询持久层实现方法
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public InvZGJStockModel queryStockById(String id) throws Exception {
		return (InvZGJStockModel) this.getOne("queryStockById", id);
	}
	/**
	 * 获取零件收容数
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月28日
	 */
	@Override
	public Integer queryStandPackageForPart(InvZGJStockModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryStandPackageForPart", model);
	}
	/**
	 * 修改安全库存数持久层实现方法(修改库存数量)
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public void updateForSafeStockNum(InvZGJStockModel model) throws Exception {
		this.updateByKey("updateForSafeStockNum", model);
	}
	/**
	 * 库存管理查询数据导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvZGJStockModel> queryExportDataForExcel(InvZGJStockModel model) throws Exception {
		return (List<InvZGJStockModel>) this.getList("queryStockForPage", model);
	}
	/**
	 *  批量修改库存数据
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月18日
	 */
	@Override
	public void batchUpdateStock(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("batchUpdateStock", paramMap);
	}

	/**
	 * @Description:  加载仓库名称下拉框 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvUnloadPortModel>   
	 * @author: dtp
	 * @date: 2019年4月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvUnloadPortModel> queryWareCodeLsit(InvUnloadPortModel model) {
		return this.getListByKey("queryWareCodeLsit", model);
	}

	/**
	 * @Description: 查询收容数
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvZGJStockModel> queryStandPackageForPartList(InvZGJStockModel model) {
		return this.getListByKey("queryStandPackageForPartList", model);
	}

}
