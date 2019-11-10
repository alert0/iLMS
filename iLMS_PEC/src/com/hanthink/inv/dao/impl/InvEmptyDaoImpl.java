package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvEmptyDao;
import com.hanthink.inv.model.InvEmptyModel;
import com.hanthink.mp.model.MpResidualModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * <pre> 
 * 功能描述:空容器查询业务持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvEmptyDaoImpl extends MyBatisDaoImpl<String, InvEmptyModel>
						implements InvEmptyDao{

	@Override
	public String getNamespace() {
		return InvEmptyModel.class.getName();
	}
	/**
	 * 空容器分页查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page) throws Exception {
		return (List<InvEmptyModel>) this.getList("queryEmptyForPage", model,page);
	}
	/**
	 * 修改空容器数量持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public void updateForEmpty(InvEmptyModel model) throws Exception {
		this.updateByKey("updateForEmpty", model);
	}
	/**
	 * 空容器库存导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvEmptyModel> exportForExcel(InvEmptyModel model) throws Exception {
		return (List<InvEmptyModel>) this.getList("queryEmptyForPage", model);
	}
	
	/**
	 * 根据UUID删除临时表数据
	 */
	@Override
	public void deleteInvEmptyImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteInvEmptyImportTempDataByUUID", uuid);
	}
	
	/**
	 * 插入临时表数据
	 */
	@Override
	public void insertInvEmptyImportTempData(List<InvEmptyModel> importList) {
		this.insertByKey("insertInvEmptyImportTempData", importList);
	}
	
	/**
	 * 调用存储校验数据
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkInvEmptyImportData", ckParamMap);
	}
	
	/**
	 * 查询临时表中的数据是否可以导入
	 */
	@Override
	public String queryInvEmptyIsImportFlag(String uuid) {
		 return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryInvEmptyIsImportFlag", uuid);
	}
	
	/**
	 * 查询导入的临时数据信息
	 */
	@Override
	public PageList<InvEmptyModel> queryInvEmptyImportTempData(Map<String, String> paramMap, Page page) {
		return (PageList)this.getByKey("queryInvEmptyImportTempData", paramMap,page);
	}
	/**
	 * 查询可导入的数据
	 */
	@Override
	public List<InvEmptyModel> queryForInsertList(Map<String, Object> paramMap) {
		return (List<InvEmptyModel>) this.getList("queryForInsertList", paramMap);
	}
	/**
	 * 根据ID查询出哪些数据要更新导入
	 */
	@Override
	public List<InvEmptyModel> queryUpdateDataFromInvEmptyImp(Map<String, Object> paramMap) {
		return (List<InvEmptyModel>) this.getList("queryUpdateDataFromInvEmptyImp", paramMap);
	}
	/**
	 * 更新导入的方法
	 */
	@Override
	public void updateInvEmptyImportData(Map<String, Object> paramMap) {
			this.updateByKey("updateInvEmptyImportData", paramMap);
	}
	/**
	 * 临时表数据信息写入到正式表
	 */
	@Override
	public void insertInvEmptyImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertInvEmptyImportData", paramMap);
	}
	/**
	 * 更新临时表导入状态
	 */
	@Override
	public void updateInvEmptyImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateInvEmptyImportDataImpStatus", paramMap);
	}
	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<InvEmptyModel> queryInvEmptyImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryInvEmptyImportTempData", paramMap);
	}
	/**
	 * 查询箱种
	 */
	@Override
	public String selectBoxType(String boxType) {
		 return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectBoxType", boxType);
	}
	/**
	 * 查询车间
	 */
	@Override
	public String selectWorkCenter(String workCenter) {
		 return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectWorkCenter", workCenter);
	}

}
