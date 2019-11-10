package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupManualOrderDao;
import com.hanthink.pup.model.PupManualOrderModel;
import com.hanthink.pup.model.PupManualOrderPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:手工调整订单持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PupManualOrderDaoImpl extends MyBatisDaoImpl<String, PupManualOrderModel>
					implements PupManualOrderDao{

	@Override
	public String getNamespace() {
		return PupManualOrderModel.class.getName();
	}
	/**
	 * 手工调整订单查询业务持久层实现
	 */
	@Override
	public List<PupManualOrderModel> queryManualOrderForPage(PupManualOrderPageModel model, Page page) throws Exception {
		return this.getByKey("queryManualOrderForPage", model, page);
	}
	/**
	 * 手工调整订单删除数据实现方法
	 */
	@Override
	public void removeManualOders(String[] purchaseNo) throws Exception {
		this.deleteByKey("removeManualOders", purchaseNo);
	}
	/**
	 * 手工调整订单页面数据导出实现方法
	 */
	@Override
	public List<PupManualOrderModel> queryManualOederForExport(PupManualOrderPageModel model) throws Exception {
		return this.getByKey("queryManualOrderForPage", model);
	}
	/**
	 * 根据UUID删除临时表数据实现方法
	 */
	@Override
	public void deleteTempManualOrderByUUID(String uuid) throws Exception {
		this.deleteByKey("deleteTempManualOrderByUUID",uuid);
	}
	/**
	 * 根据UUID删除临时表数据实现方法
	 */
	@Override
	public void insertManualOrderToTempTable(List<PupManualOrderModel> models) throws Exception {
		this.insertBatchByKey("insertManualOrderToTempTable", models);
	}
	/**
	 * 调用存储过程检查数据实现方法
	 */
	@Override
	public void checkImportManualOrder(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkImportManualOrder",paramMap);
	}
	/**
	 * 调用存储过程检查数据实现方法
	 */
	@Override
	public String queryImportFlag(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryImportFlag", uuid);
	}
	/**
	 * 导出导入数据的查询实现方法
	 */
	@Override
	public List<PupManualOrderModel> queryImportManualOrderForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		return this.getByKey("queryImportManualOrderForPage", paramMap, page);
	}
	/**
	 * 查询需要修改的数据实现方法
	 */
	@Override
	public List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryUpdateManualFromTemp",paramMap);
	}
	/**
	 * 修改导入数据实现方法
	 */
	@Override
	public void updateManualOrderImportData(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateManualOrderImportData",paramMap);
	}
	/**
	 * 修改临时表数据的导入状态实现方法
	 */
	@Override
	public void insertTempDataToRegula(Map<String, Object> paramMap) throws Exception {
		this.insertByKey("insertManualOrderToRegula", paramMap);
	}
	/**
	 * 修改临时表数据的导入状态实现方法
	 */
	@Override
	public void updateManualOrderImportDataImpStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateManualOrderImportDataImpStatus",paramMap);
	}
	/**
	 * 导出导入数据实现方法
	 */
	@Override
	public List<PupManualOrderModel> queryManualOrderTempDataForExport(Map<String, String> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryImportManualOrderNeedUpdate",paramMap);
	}
}
