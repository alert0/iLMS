package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvDevelopDao;
import com.hanthink.inv.model.InvDevelopModel;
import com.hanthink.inv.model.InvLockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:库存推移查询业务持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvDevelopDaoImpl extends MyBatisDaoImpl<String, InvDevelopModel>
					implements InvDevelopDao{
	
	@Override
	public String getNamespace() {
		return InvDevelopModel.class.getName();
	}
	/**
	 * 库存推移分页查询持久实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryDevelopForPage(InvDevelopModel model, Page page) throws Exception {
		return (List<InvDevelopModel>) this.getList("queryForPage", model,page);
	}
	/**
	 * 库存推移查询数据导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryForExcelExport(InvDevelopModel model) throws Exception {
		return (List<InvDevelopModel>) this.getList("queryForPage", model);
	}
	/**
	 * 零件消耗量查询业务持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryConsumptionForPage(InvDevelopModel model, Page page) throws Exception {
		return (List<InvDevelopModel>) this.getList("queryConsumptionForPage", model,page);
	}
	/**
	 * 零件消耗量Excel导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryConsumptionForExcelExport(InvDevelopModel model) throws Exception {
		return (List<InvDevelopModel>) this.getList("queryConsumptionForPage", model);
	}
	/**
	 * 库存推移数据分页查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public List<InvDevelopModel> queryDevelopManagerForPage(InvDevelopModel model, Page page) throws Exception {
		return this.getByKey("queryDevelopManagerForPage", model, page);
	}
	/**
	 * 库存推移管理页面数据导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年11月6日
	 */
	@Override
	public List<InvDevelopModel> exportExcelForDevelopManager(InvDevelopModel model) throws Exception {
		return this.getByKey("queryDevelopManagerForPage", model);
	}
	/**
	 * 修改库存数据信息持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public void updateStock(InvDevelopModel model) throws Exception {
		this.updateByKey("updateStock", model);
	}
	/**
	 * 获取库存数据持久层实现方法
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public void getStock(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("getStock",paramMap);
		if (!"0".equals(paramMap.get("resultFlag"))) {
			throw new Exception("获取库存数据失败,请稍后再试");
		}
	}
	@Override
	public void deleteImportByUUID(String uuid) throws Exception {
		this.deleteByKey("deleteImportByUUID", uuid);
	}
	@Override
	public void insertExcelToTemp(List<InvDevelopModel> importList) throws Exception {
		this.insertBatchByKey("insertExcelToTemp", importList);
	}
	@Override
	public void checkImportStock(Map<String, String> checkMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkStockImport",checkMap);
	}
	@Override
	public Integer queryImportFlag(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryImportCounts", paramMap);
	}
	
	@Override
	public List<InvDevelopModel> queryImportForPage(Page page,String uuid) throws Exception {
		return this.getByKey("queryImportForPage", uuid, page);
	}
	
	@Override
	public void insertTempToFormal(Map<String, Object> paramMap) throws Exception {
		this.insertByKey("insertTempToFormal", paramMap);
	}
	
	@Override
	public void updateImportStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateImportStatus", paramMap);
	}
	@Override
	public List<InvDevelopModel> exportExcelForImport(String uuid) throws Exception {
		return this.getByKey("queryImportForPage", uuid);
	}
	@Override
	public InvLockModel queryRunStatus(String factoryCode) throws Exception {
		return (InvLockModel) this.getOne("queryRunStatus", factoryCode);
	}
	@Override
	public void elapseUpdate(InvLockModel model) throws Exception {
		this.updateByKey("elapseUpdate", model);
	}
	@Override
	public void elapseUpdateFirst(InvLockModel model) throws Exception {
		this.updateByKey("elapseUpdateFirst", model);
	}
	/**
	 * 查询零件消耗日用量
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年1月11日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryDailyConsumption(InvDevelopModel model, Page page) {
		return (List<InvDevelopModel>) this.getList("queryDailyConsumption", model, page);
	}
	/**
	 * 查询零件消耗日用量详细查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年1月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvDevelopModel> queryDailyConsumptionDetail(InvDevelopModel model, Page page) throws Exception {
		return (List<InvDevelopModel>) this.getList("queryDailyConsumptionDetail", model, page);
	}
	/**
	 * 获取车间下的仓库信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月29日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWareForWorkcenter(Map<String, Object> paramMap) throws Exception {
		return (List<String>) this.getList("queryWareForWorkcenter", paramMap);
	}

	@Override
	public InvDevelopModel queryWareListForStock(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryWareListForStock", paramMap);
	}
}
