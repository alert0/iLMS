package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupVersionGapDao;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.model.PupVersionPageModel;
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
	 * 订单数据差异分页查询持久层实现方法
	 * @param pageModel
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> queryVersionGapForPage(PupVersionPageModel pageModel, Page page) {
		return this.getByKey("queryVersionGap", pageModel, page);
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
	 * @param pageModel
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> exportVersionGapForQuery(PupVersionPageModel pageModel) throws Exception {
		return this.getByKey("queryVersionGap", pageModel);
	}
	@Override
	public void importVersion(List<PupLockPlanModel> importList) throws Exception {
		this.insertBatchByKey("importVesion", importList);
	}

}
