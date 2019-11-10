package com.hanthink.sw.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwMaterialOrderDao;
import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class SwMaterialOrderDaoImpl extends MyBatisDaoImpl<String, SwMaterialOrderModel> implements SwMaterialOrderDao{

	
	@Override
	public String getNamespace() {
		return SwMaterialOrderModel.class.getName();
	}
	
	/**
	 * 订单分页查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryMaterialOrderPage(SwMaterialOrderModel model, DefaultPage p) {
		return (PageList<SwMaterialOrderModel>) this.getByKey("queryMaterialOrderPage", model, p);
	}

	
	/**
	 * 资材订单导出
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwMaterialOrderModel> downloadMaterialOrder(SwMaterialOrderModel model) {
		return (List<SwMaterialOrderModel>) this.getByKey("downloadMaterialOrder", model);
	}

	/**
	 * 交货反馈修改
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean updateMaterialOrderPage(SwMaterialOrderModel model) {

		int updateByKey = this.updateByKey("updateMaterialOrderPage", model);
		if (updateByKey >= 1) {
			return true;
		}
		return false;
	}


	/**
	 * 标签打印
	 * @param orderModel
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwMaterialOrderModel> queryDemondOrderPrintLabelList(SwMaterialOrderModel orderModel) {

		return (List<SwMaterialOrderModel>) this.getByKey("queryDemondOrderPrintLabelList", orderModel);
	}

	/**
	 * 交货反馈临时数据新增
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public Boolean insertMaterialOrderReturnTmp(SwMaterialOrderModel model) {

		int insertByKey = this.insertByKey("insertMaterialOrderReturnTmp", model);
		if (insertByKey >= 1) {
			return true;
		}
		return false;
	}

	/**
	 * 交货反馈临时数据查询
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryMaterialOrderReturnTmp(SwMaterialOrderModel model, DefaultPage p) {
		
		return (PageList<SwMaterialOrderModel>) this.getByKey("queryMaterialOrderReturnTmp", model, p);
	}

	/**
     * 判断反馈数量汇总是否满足订单总数
     * @param model
     * @return
     * Lxh
     */
	@Override
	public Boolean checkMeetDelivery(SwMaterialOrderModel model) {
		List<SwMaterialOrderModel> byKey = this.getByKey("checkMeetDelivery", model);
		String returnSum = byKey.get(0).getReturnSum();
		String orderQty = model.getOrderQty() != null && !"".equals(model.getOrderQty()) ? model.getOrderQty() : "0";
		int meet = Integer.parseInt(orderQty) - Integer.parseInt(returnSum);
		if (meet == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取反馈条数
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public int getMaterialOrderReturnTmpCount(SwMaterialOrderModel model) {
		List<SwMaterialOrderModel> byKey = this.getByKey("getMaterialOrderReturnTmpCount", model);
		String returnCount = byKey.get(0).getReturnCount();
		int parseInt = Integer.parseInt(returnCount);
		return parseInt;
	}

	/**
	 * 更新反馈状态
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean updateReturnStatus(SwMaterialOrderModel model) {
		int updateByKey = this.updateByKey("updateReturnStatus", model);
		if (updateByKey > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 反馈临时数据提交到业务
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean MaterialOrderReturnTmpToOrder(SwMaterialOrderModel model) {
		int updateByKey = this.updateByKey("MaterialOrderReturnTmpToOrder", model);
		if (updateByKey > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 交货反馈临时数据修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public Boolean updateMaterialOrderReturnTmp(SwMaterialOrderModel model) {
		int updateByKey = this.updateByKey("updateMaterialOrderReturnTmp", model);
		if (updateByKey > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 交货反馈临时数据删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public void deleteMaterialOrderReturnTmp(SwMaterialOrderModel model) {
		this.deleteByKey("deleteMaterialOrderReturnTmp", model);
		
	}

	/**
	 * 根据uuid删除临时表数据
	 * @param uuid
	 * @throws Exception
	 *
	 */
	@Override
	public void deleteTempCongfigByUUID(String uuid) throws Exception{
		this.deleteByKey("deleteTempCongfigByUUID", uuid);
		
	}

	/**
	 * 查询分页导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryImportForPage(String uuid, Page page) {
		
		return (PageList<SwMaterialOrderModel>) this.getByKey("queryImportForPage", uuid, page);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * 
	 * 
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	/**
	 * 临时数据导入到业务表
	 */
	@Override
	public void insertTempToFormal(Map<String, Object> paramMap) {
		this.insertByKey("insertTempToFormal", paramMap);
		
	}

	/**
	 * 更新导入状态
	 */
	@Override
	public void updateImportStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateImportStatus", paramMap);
		
	}

	@Override
	public List<SwMaterialOrderModel> exportForImport(String uuid) {
		return this.getByKey("queryImportForPage", uuid);
	}

	/**
	 * excel数据插入到临时表
	 */
	@Override
	public void insertConfigToTemp(List<SwMaterialOrderModel> importList) {
		this.insertBatchByKey("insertConfigToTemp", importList);
		
	}

	/**
	 * 数据校验
	 */
	@Override
	public void ckeckImportConfig(Map<String, String> checkMap) {
		this.sqlSessionTemplate.selectOne("ckeckImportConfig", checkMap);
		
	}

	/**
	 * 修改打印状态
	 */
	@Override
	public void updatePrintStatus(SwMaterialOrderModel model) {
		this.updateByKey("updatePrintStatus", model);
		
	}
	

}
