package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupVersionGapDao;
import com.hanthink.pup.model.PupVersionModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class PupVersionGapDaoImpl extends MyBatisDaoImpl<String, PupVersionModel>
					implements PupVersionGapDao{

	@Override
	public String getNamespace() {	
		return PupVersionModel.class.getName();
	}
	/**
	 *  检查是否生成物流计划业务实现
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public Integer checkIsPlan(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("checkIsPlan", factoryCode);
	}
	/**
	 * 检查是否导入上一版本物流计划持久层实现
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public Integer checkIsPrePlan(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("checkIsPrePlan",factoryCode);
	}
	/**
	 * 订单数据差异分页查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> queryVersionGapForPage(PupVersionModel model, Page page) {
		return this.getByKey("queryVersionGap", model, page);
	}
	/**
	 * 加载数据字典差异标识持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getDiffFlag(Map<String, Object> paramMap) throws Exception {
		return this.getList("getDiffFlag", paramMap);
	}
	/**
	 * 版本比对数据导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> exportVersionGapForQuery(PupVersionModel model) throws Exception {
		return this.getByKey("queryVersionGap", model);
	}
	@Override
	public void deleteOldVersion(String curFactoryCode) throws Exception {
		this.deleteByKey("deleteOldVersion", curFactoryCode);
	}
	@Override
	public void insertVersionToTable(List<PupVersionModel> importList) throws Exception {
		this.insertBatchByKey("insertVersionToTable", importList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PupVersionModel> queryforVesion(String factoryCode) throws Exception {
		return (List<PupVersionModel>) this.getList("queryforVesion", factoryCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PupVersionModel> queryOneVersion(PupVersionModel model, Page page) throws Exception {
		return this.getListByKey("queryOneVersion", model, page);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PupVersionModel> queryTwoVersion(Map<String, String> paramMap, Page page) throws Exception {
		return this.getListByKey("queryTwoVersion", paramMap, page);
	}
	@Override
	public void logVersionGap(List<PupVersionModel> list) throws Exception {
		this.insertBatchByKey("logVersionGap", list);
	}
}
