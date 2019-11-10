package com.hanthink.sps.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sps.dao.SpsConfigDetailDao;
import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:SPS配置明细维护持久实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年11月8日下午4:40:52
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SpsConfigDetailDaoImpl extends MyBatisDaoImpl<String, SpsConfigDetailModel>
											implements SpsConfigDetailDao{

	@Override
	public String getNamespace() {
		return SpsConfigDetailModel.class.getName();
	}

	@Override
	public List<SpsConfigDetailModel> queryConfigDetailsForPage(SpsConfigDetailModel model,Page page) throws Exception {
		return this.getByKey("queryConfigDetailsForPage", model, page);
	}
	
	@Override
	public Object isExist(String partNo) throws Exception {
		SpsConfigDetailModel model = new SpsConfigDetailModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setPartNo(partNo);
		return this.sqlSessionTemplate.selectOne("partNoIsExist", model);
	}
	
	@Override
	public boolean isExist(SpsConfigDetailModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("mainKeyIsExist", model);
	}
	
	@Override
	public String getConfigId(SpsConfigDetailModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("getConfigId",model);
	}
	
	@Override
	public void saveAdd(SpsConfigDetailModel model) throws Exception {
		this.insertByKey("saveConfigDetailAdd", model);
	}
	
	@Override
	public void updateConfigDetails(SpsConfigDetailModel model) throws Exception {
		this.updateByKey("updateConfigDetails", model);
	}

	@Override
	public void removeConfigDetailsByIds(String[] ids) throws Exception {
		this.deleteByKey("removeConfigDetailsByIds", ids);
	}

	@Override
	public List<SpsConfigDetailModel> exportQueryForExcel(SpsConfigDetailModel model) throws Exception {
		return this.getByKey("queryConfigDetailsForPage", model);
	}

	@Override
	public void deleteTempCongfigByUUID(String uuid) throws Exception {
		System.err.println(uuid);
		this.deleteByKey("deleteTempCongfigByUUID", uuid);
	}
	
	@Override
	public void insertConfigToTemp(List<SpsConfigDetailModel> importList) throws Exception {
		this.insertBatchByKey("insertConfigToTemp", importList);
	}
	
	@Override
	public void ckeckImportConfig(Map<String, String> checkMap) throws Exception {
		this.sqlSessionTemplate.selectOne("ckeckImportConfig", checkMap);
	}
	
	@Override
	public List<SpsConfigDetailModel> queryImportForPage(String uuid, Page page) throws Exception {
		return this.getByKey("queryImportForPage", uuid, page);
	}

	@Override
	public Integer getCountConfigImport(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCountConfigImport", paramMap);
	}

	@Override
	public void insertTempToFormal(Map<String, Object> paramMap) throws Exception {
		this.insertByKey("insertSPSConfigToFormal", paramMap);
	}

	@Override
	public void updateImportStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateImportStatus", paramMap);
	}
	
	@Override
	public List<SpsConfigDetailModel> exportForImport(String uuid) throws Exception {
		return this.getByKey("queryImportForPage", uuid);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	/**
	 * @Description: 将临时表数据更新到正式表  check_result = 2
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	@Override
	public void updateTempData(Map<String, Object> paramMap) {
		this.updateByKey("updateTempData", paramMap);
	}

	/**
	 * @Description: 查询更新数据  check_result = 2
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigDetailModel> queryUpdateList(Map<String, Object> paramMap) {
		return this.getList("queryUpdateList", paramMap);
	}

	/**
	 * @Description: 更新数据  check_result = 2
	 * @author: dtp
	 * @date: 2019年6月14日
	 * @param list_u
	 */
	@Override
	public void updateTempDataList(SpsConfigDetailModel model) {
		this.updateByKey("updateTempDataList", model);
	}

}
