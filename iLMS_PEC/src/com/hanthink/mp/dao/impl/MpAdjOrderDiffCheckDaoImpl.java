package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpAdjOrderDiffCheckDao;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModel;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：计划对比差异查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpAdjOrderDiffCheckDaoImpl extends MyBatisDaoImpl<String, MpAdjOrderDiffCheckModel> implements MpAdjOrderDiffCheckDao{

    @Override
    public String getNamespace() {
        return MpAdjOrderDiffCheckModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckForPage(MpAdjOrderDiffCheckModel model, DefaultPage p) {
		 return this.getByKey("queryMpAdjOrderDiffCheckForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckByKey(MpAdjOrderDiffCheckModel model) {
		return this.getByKey("queryMpAdjOrderDiffCheckForPage", model);
	}

	/**
	 * 生成USP_MP_ZSB_DIFF
	 */
	@Override
	public Integer getMpZsbDiff(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("in_arr_factory", curFactoryCode);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("getMpZsbDiff",map);	
		return   (Integer) map.get("outCode");
	}

    @Override
    public void clearOrderDiffData(String curFactoryCode) {
        this.deleteByKey("clearOrderDiffData", curFactoryCode);
    }

    @Override
    public void updateManuNum(MpAdjOrderDiffCheckModel adjOrderDiffCheckModel) {
        this.updateByKey("updateManuNum", adjOrderDiffCheckModel);
    }

    @Override
    public void deleteMpAdjOrderDiffImportTempDataByUUID(String uuid) {
        this.deleteByKey("deleteMpAdjOrderDiffImportTempDataByUUID", uuid);
    }

    @Override
    public void insertMpAdjOrderDiffImportTempData(List<MpAdjOrderDiffCheckModelImport> importList) {
        this.insertBatchByKey("insertMpAdjOrderDiffImportTempData", importList);
    }

    @Override
    public void checkMpAdjOrderDiffImportData(Map<String, String> ckParamMap) {
        this.sqlSessionTemplate.selectOne("checkMpAdjOrderDiffImportData", ckParamMap);
    }

    @Override
    public String queryMpaAdjOrderIsImportFlag(String uuid) {
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpaAdjOrderIsImportFlag", uuid);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public PageList<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList)this.getByKey("queryMpAdjOrderImportTempData", paramMap, page);
    }

    @Override
    public void updateMpAdjOrderImportDate(Map<String, Object> paramMap) {
        this.updateByKey("updateMpAdjOrderImportDate", paramMap);
    }

    @Override
    public void updateImportDataImpStatus(Map<String, Object> paramMap) {
        this.updateByKey("updateAdjOrderImportDataImpStatus");
    }

    @Override
    public List<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempDataForExport(Map<String, String> paramMap) {
        return this.sqlSessionTemplate.selectList("queryMpAdjOrderImportTempData", paramMap);
    }

}

