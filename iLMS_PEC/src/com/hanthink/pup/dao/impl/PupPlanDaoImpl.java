package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupPlanDao;
import com.hanthink.pup.model.PupPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:取货计划查询持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PupPlanDaoImpl extends MyBatisDaoImpl<String, PupPlanModel>
						implements PupPlanDao{

	@Override
	public String getNamespace() {
		return PupPlanModel.class.getName();
	}
	/**
	 * 取货计划查询业务持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<PupPlanModel> queryPlanForPage(PupPlanModel model, Page page) throws Exception {
		return this.getByKey("queryPlanForPage", model, page);
	}
	/**
	 * 数据字典项下载状态加载持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getDownloadStatus(Map<String, Object> paramMap) throws Exception {
		return this.getList("queryDownloadStatus", paramMap);
	}
	/**
	 * 单条/批量删除数据业务持久层实现方法
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlans(List<PupPlanModel> list) throws Exception {
		this.deleteByKey("deletePlansById",list);
	}
	/**
	 * 根据导入UUID删除临时表已存在数据业务持久层实现方法
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlanByUUID(String uuid) throws Exception {
		this.deleteByKey("deletePlanByUUID",uuid);
	}
	/**
	 *  将Excel数据写入临时表持久层实现方法
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void insertToTempTable(List<PupPlanModel> importList) throws Exception {
		this.insertBatchByKey("insertPlanToTempTable", importList);
	}
	/**
	 * 导入数据调用存储过程校验数据实现方法
	 * @param checkMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void checkImportMessage(Map<String, String> checkMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkImportMessage", checkMap);
	}
	/**
	 * 查询导入状态实现方法
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public String queryImportFlag(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryImportFlag", uuid);
	}
	/**
	 * 查询到入数据详情持久层实现方法
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<PupPlanModel> queryImportPlanForPage(Map<String, String> paramMap, Page page) throws Exception {
		return this.getByKey("queryImportForPage", paramMap, page);
	}
	/**
	 * 查询导入数据的正确条数持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public Integer getCountForImport(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCountPlanForImport", paramMap);
	}
	/**
	 * 查询正确的可导入正式表的数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<PupPlanModel> queryForRightList(Map<String, Object> paramMap) throws Exception {
		return this.getByKey("queryForRightList", paramMap);
	}
	@Override
	public Integer judgeIsExistsInFormal(PupPlanModel pupPlanModel) throws Exception {
		return this.sqlSessionTemplate.selectOne("judgeIsExistsInFormal", pupPlanModel);
	}
	@Override
	public void insertIntoFormal(PupPlanModel pupPlanModel) throws Exception {
		this.insertByKey("insertIntoFormal", pupPlanModel);
	}
	@Override
	public void updateFormalData(PupPlanModel pupPlanModel) throws Exception {
		this.updateByKey("updateFormalData", pupPlanModel);
	}
	
	/**
	 * 查询需要修改的数据的id持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryUpdateFromTemp",paramMap);
	}
	/**
	 * 修改导入状态业务持久层实现方法
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void updateImportStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateImportStatus",paramMap);
	}
	@Override
	public List<PupPlanModel> queryImportForExport(Map<String, String> paramMap) throws Exception {
		return this.getByKey("queryImportForPage", paramMap);
	}

	@Override
	public void insertAndUpdatePlan(Map<String, Object> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("insertAndUpdatePlan", paramMap);
	}
	@Override
	public Integer getCountAllImport(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCountAllImport", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PupPlanModel> queryPlanForExport(PupPlanModel model) throws Exception {
		return (List<PupPlanModel>) this.getList("queryPlanForPage", model);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PupPlanModel> queryForDepEnq(PupPlanModel model, Page page) throws Exception {
		return this.getListByKey("queryForDepEnq", model, page);
	}
	/**
	 * 已发车查询导出
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PupPlanModel> queryForExportDepEnq(PupPlanModel model) throws Exception {
		return (List<PupPlanModel>) this.getList("queryForDepEnq", model);
	}

}
