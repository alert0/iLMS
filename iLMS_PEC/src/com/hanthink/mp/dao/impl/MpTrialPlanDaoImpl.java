package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.hanthink.mp.dao.MpTrialPlanDao;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.mp.model.MpTrialPlanModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：新车型计划维护 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpTrialPlanDaoImpl extends MyBatisDaoImpl<String, MpTrialPlanModel> implements MpTrialPlanDao{

    @Override
    public String getNamespace() {
        return MpTrialPlanModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpTrialPlanModel> queryMpTrialPlanForPage(MpTrialPlanModel model, DefaultPage p) {
		 return this.getByKey("queryMpTrialPlanForPage", model, p);
	}
    
	/**
	 * 写入导入数据到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:33
	 */
	@Override
	public void insertMpTrialPlanImportTempData(List<MpTrialPlanModelImport> importList) {
		this.insertBatchByKey("insertMpTrialPlanImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:12:37
	 */
	@Override
	public void checkMpTrialPlanImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkMpTrialPlanImportData", ckParamMap);
	}

	/**
	 * 查询导入的临时数据信息
	 * @param params
	 * @param page
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:30:46
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList<MpTrialPlanModelImport> queryMpTrialPlanImportTempData(Map<String, String> params,Page page) {
		return (PageList)this.getByKey("queryMpTrialPlanImportTempData", params,page);
	}

	/**
	 * 临时表数据信息写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:31:10
	 */
	@Override
	public void insertMpTrialPlanImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertMpTrialPlanImportData", paramMap);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:43
	 */
	@Override
	public void updateMpTrialPlanImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateMpTrialPlanImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void deleteMpTrialPlanImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteMpTrialPlanImportTempDataByUUID", uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpTrialPlanModel> queryMpTrialPlanByKey(MpTrialPlanModel model) {
		return this.getByKey("queryMpTrialPlanForPage", model);
	}
    
	/**
	 * 查询是否可以批量导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
    public String queryMpTrialPlanIsImportFlag(String uuid) {
	    return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpTrialPlanIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpTrialPlanModelImport> queryMpTrialPlanImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryMpTrialPlanImportTempData", paramMap);
	}
	
	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> querySortIdAndLogByCalStatus() {
		
		return (List<String>) this.getList("querySortIdAndLogByCalStatus", null);	
	}

	/**
	 * 直接删除未订购数据
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void removeAndLogByCalStatus(String[] aryIds, String ipAddr) {
		
		this.deleteByKey("removeAndLogByCalStatus");	
	}
	
	/**
	 * 获取新车型计划
	 */
	@Override
	public Integer getMpTrialPlan(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("getMpTrialPlan",map);	
		return   (Integer) map.get("outCode");
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MpTrialPlanModelImport> queryForInsertList(Map<String, Object> paramMap) {
		return (List<MpTrialPlanModelImport>) this.getList("queryForInsertList", paramMap);
	}

	/**
	 * 查询出最大的sortId
	 */
	@Override
	public Integer selectMaxSortId() {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectMaxSortId");
	}

}

