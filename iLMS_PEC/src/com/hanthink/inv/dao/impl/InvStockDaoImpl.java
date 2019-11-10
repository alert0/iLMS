package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvStockDao;
import com.hanthink.inv.model.InvStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class InvStockDaoImpl extends MyBatisDaoImpl<String, InvStockModel>
					implements InvStockDao{

	@Override
	public String getNamespace() {
		return InvStockModel.class.getName();
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
	public List<InvStockModel> queryStockForPage(InvStockModel model, Page page) throws Exception {
		return (List<InvStockModel>) this.getList("queryStockForPage", model, page);
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
	public InvStockModel queryStockById(String id) throws Exception {
		return (InvStockModel) this.getOne("queryStockById", id);
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
	public Integer queryStandPackageForPart(InvStockModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryStandPackageForPart", model);
	}
	/**
	 * 修改安全库存数持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public void updateForSafeStockNum(InvStockModel model) throws Exception {
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
	public List<InvStockModel> queryExportDataForExcel(InvStockModel model) throws Exception {
		return (List<InvStockModel>) this.getList("queryStockForPage", model);
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
	@Override
	public void insertToTempStock(List<InvStockModel> importList) throws Exception {
		this.insertBatchByKey("insertToTempStock", importList);
	}
	@Override
	public void checkImportDataInformation(Map<String, String> checkMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkImportDataInformation", checkMap);
	}
	@Override
	public boolean queryStockImportFlag(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryStockImportFlag", uuid);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockModel> queryImportTemp(Map<String, String> paramMap, Page page) throws Exception {
		return (List<InvStockModel>) this.getList("queryStockForImport", paramMap, page);
	}
	@Override
	public void delTempDataByUUID(String uuid) throws Exception {
		this.deleteByKey("delStockImportByUUID", uuid);
	}
	@Override
	public void insertTempDataToFormal(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertTempStockToFormal", paramMap);
		List<InvStockModel> list = this.sqlSessionTemplate.selectList("queryForStockUpdateList",paramMap);
		if (list.size() > 0) {
			for (InvStockModel invStockModel : list) {
				invStockModel.setFactoryCode(paramMap.get("factoryCode"));
				invStockModel.setLastModifiedUser(paramMap.get("creationUser"));
				this.updateByKey("updateFormalStock", invStockModel);
			}
		}
	}
	@Override
	public void updateTempImportStatus(Map<String, String> paramMap) throws Exception {
		this.updateByKey("updateStockImprtStatus", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockModel> exportImportData(Map<String, String> paramMap) throws Exception {
		return (List<InvStockModel>) this.getList("queryStockForImport", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockModel> calDiffFlag(Map<String, String> paramMap) throws Exception {
		return (List<InvStockModel>) this.getList("calDiffFlag", paramMap);
	}
	@Override
	public void insertToStockTakeHeader(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertToStockTakeHeader", paramMap);
	}
	@Override
	public void insertToStockTakeBody(List<InvStockModel> calList) throws Exception {
		this.insertBatchByKey("insertToStockTakeBody", calList);
	}
	@Override
	public void insertToInvOut(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertToInvOut", paramMap);
	}
	@Override
	public void insertToInvOutDetail(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertToInvOutDetail", paramMap);
	}
}
