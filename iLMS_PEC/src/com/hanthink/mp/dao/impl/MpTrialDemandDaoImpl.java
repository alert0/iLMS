package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.hanthink.mp.dao.MpTrialDemandDao;
import com.hanthink.mp.model.MpResidualModelImport;
import com.hanthink.mp.model.MpTrialDemandModel;
import com.hanthink.mp.model.MpTrialDemandModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：新车型需求计算 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpTrialDemandDaoImpl extends MyBatisDaoImpl<String, MpTrialDemandModel> implements MpTrialDemandDao{

    @Override
    public String getNamespace() {
        return MpTrialDemandModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpTrialDemandModel> queryMpTrialDemandForPage(MpTrialDemandModel model, DefaultPage p) {
		 return this.getByKey("queryMpTrialDemandForPage", model, p);
	}
    
	/**
	 * 写入导入数据到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:33
	 */
	@Override
	public void insertMpTrialDemandImportTempData(List<MpTrialDemandModelImport> importList) {
		this.insertBatchByKey("insertMpTrialDemandImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:12:37
	 */
	@Override
	public void checkMpTrialDemandImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkMpTrialDemandImportData", ckParamMap);
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
	public PageList<MpTrialDemandModelImport> queryMpTrialDemandImportTempData(Map<String, String> params,Page page) {
		return (PageList)this.getByKey("queryMpTrialDemandImportTempData", params,page);
	}

	/**
	 * 临时表数据信息写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:31:10
	 */
	@Override
	public void insertMpTrialDemandImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertMpTrialDemandImportData", paramMap);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:43
	 */
	@Override
	public void updateMpTrialDemandImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateMpTrialDemandImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void deleteMpTrialDemandImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteMpTrialDemandImportTempDataByUUID", uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpTrialDemandModel> queryMpTrialDemandByKey(MpTrialDemandModel model) {
		return this.getByKey("queryMpTrialDemandForPage", model);
	}
    
	/**
	 * 根据ID查询出哪些数据要更新导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> queryUpdateDataFromMpTrialDemandImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromMpTrialDemandImp", paramMap);
	}

	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void updateMpTrialDemandImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateMpTrialDemandImportData", paramMap);
	}
 
	/**
	 * 查询是否可以批量导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
    public String queryMpTrialDemandIsImportFlag(String uuid) {
	    return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpTrialDemandIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpTrialDemandModelImport> queryMpTrialDemandImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryMpTrialDemandImportTempData", paramMap);
	}

	/**
	 * 需求计算生成
	 * @return 
	 */
	@Override
	public Integer generateMpTrialDemand(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("generateMpTrialDemand",map);	
		return   (Integer) map.get("outCode");
	}

	/**
	 * 需求计算发布
	 */
	@Override
	public Integer releaseMpTrialDemand(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("releaseMpTrialDemand",map);	
		return   (Integer) map.get("outCode");
	}

	/**
	 * 需求计算导入
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MpTrialDemandModelImport> queryForInsertList(Map<String, Object> paramMap) {
		return (List<MpTrialDemandModelImport>) this.getList("queryForInsertList", paramMap);
	}
}

