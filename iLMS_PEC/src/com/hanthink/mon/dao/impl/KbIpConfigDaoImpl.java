package com.hanthink.mon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.KbIpConfigDao;
import com.hanthink.mon.model.KbIpConfigModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：看板IP配置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class KbIpConfigDaoImpl extends MyBatisDaoImpl<String, KbIpConfigModel> implements KbIpConfigDao{

    @Override
    public String getNamespace() {
        return KbIpConfigModel.class.getName();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KbIpConfigModel> queryKbIpConfigForPage(KbIpConfigModel model, Page page) {
		 return (List<KbIpConfigModel>) this.getList("queryKbIpConfigForPage", model, page);
	}
    @Override
    public List<KbIpConfigModel> queryKbList(String factoryCode) throws Exception {
    	return this.getByKey("queryKbList", factoryCode);
    }

	/**
	 * 写入导入数据到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:33
	 */
	@Override
	public void insertKbIpConfigImportTempData(List<KbIpConfigModel> importList) {
		this.insertByKey("insertKbIpConfigImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:12:37
	 */
	@Override
	public void checkKbIpConfigImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkKbIpConfigImportData", ckParamMap);
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
	public PageList<KbIpConfigModel> queryKbIpConfigImportTempData(Map<String, String> params,Page page) {
		return (PageList)this.getByKey("queryKbIpConfigImportTempData", params,page);
	}

	/**
	 * 临时表数据信息写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:31:10
	 */
	@Override
	public void insertKbIpConfigImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertKbIpConfigImportData", paramMap);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:43
	 */
	@Override
	public void updateKbIpConfigImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateKbIpConfigImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void deleteKbIpConfigImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteKbIpConfigImportTempDataByUUID", uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<KbIpConfigModel> queryKbIpConfigByKey(KbIpConfigModel model) {
		return this.getByKey("queryKbIpConfigForPage", model);
	}
    
	/**
	 * 根据ID查询出哪些数据要更新导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> queryUpdateDataFromKbIpConfigImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromKbIpConfigImp", paramMap);
	}

	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void updateKbIpConfigImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateKbIpConfigImportData", paramMap);
	}
 
	/**
	 * 查询是否可以批量导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
    public String queryKbIpConfigIsImportFlag(String uuid) {
	    return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryKbIpConfigIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<KbIpConfigModel> queryKbIpConfigImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryKbIpConfigImportTempData", paramMap);
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KbIpConfigModel> queryForInsertList(Map<String, Object> paramMap) {
		return (List<KbIpConfigModel>) this.getList("queryForInsertList", paramMap);
	}

    /**
     * 批量删除数据
     */
	@Override
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}

	/**
	 * 判断主表主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(KbIpConfigModel KbIpConfigModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKey", KbIpConfigModel);
	}

	/**
	 * 判断明细主键冲突
	 */
	@Override
	public Integer selectPrimaryKeyDetail(KbIpConfigModel kbIpConfigModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKeyDetail", kbIpConfigModel);
	}

	/**
	 * 更新明细
	 */
	@Override
	public void updateDetail(KbIpConfigModel kbIpConfigModel) {
		this.updateByKey("updateDetail", kbIpConfigModel);
	}

	/**
	 * 新增明细
	 */
	@Override
	public void createDetail(KbIpConfigModel kbIpConfigModel) {
		this.insertByKey("createDetail", kbIpConfigModel);
	}

	/**
	 * 删除明细
	 */
	@Override
	public void deleteByIdsDetail(String[] aryIds) {
		this.deleteByKey("deleteByIdsDetail", aryIds);
	}

	@Override
	public Integer getCurrBussId() throws Exception {
		return this.sqlSessionTemplate.selectOne("getCurrBussId");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KbIpConfigModel> queryKbTypeForPage(KbIpConfigModel model, Page page) throws Exception {
		return (List<KbIpConfigModel>) this.getList("queryKbTypeForPage", model, page);
	}

	@Override
	public boolean queryForMaxSortNum(KbIpConfigModel kbIpConfigModel) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryForMaxSortNum", kbIpConfigModel);
	}

	@Override
	public void saveBasicKbInfo(KbIpConfigModel kbIpConfigModel) throws Exception {
		this.insertByKey("saveBasicKbInfo", kbIpConfigModel);
	}
	
	@Override
	public void saveDetailInfo(KbIpConfigModel kbIpConfigModel) throws Exception {
		this.insertByKey("saveDetailInfo", kbIpConfigModel);
	}

	@Override
	public void updateKbType(KbIpConfigModel kbIpConfigModel) throws Exception {
		this.updateByKey("update", kbIpConfigModel);
	}

	@Override
	public String getNextIdNum(String seqName) throws Exception {
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("seqName", seqName);
		return this.sqlSessionTemplate.selectOne("getNextIdNum",reqMap);
	}

	@Override
	public boolean combIpIsInusedJudge(KbIpConfigModel kbIpConfigModel) throws Exception {
		return this.sqlSessionTemplate.selectOne("combIpIsInusedJudge", kbIpConfigModel);
	}

	@Override
	public void updateKbConfigData(Map<String, String> paramMap) throws Exception {
		this.updateByKey("updateKbConfigData", paramMap);
	}

}

