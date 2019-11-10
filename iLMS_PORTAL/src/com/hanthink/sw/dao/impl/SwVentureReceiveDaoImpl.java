package com.hanthink.sw.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwVentureReceiveDao;
import com.hanthink.sw.model.SwVentureReceiveModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class SwVentureReceiveDaoImpl extends MyBatisDaoImpl<SwVentureReceiveModel, String>
				implements SwVentureReceiveDao{

	@Override
	public String getNamespace() {
		return SwVentureReceiveModel.class.getName();
	}
	/**
	 * 合资车收货数据界面查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwVentureReceiveModel> querySwVentureReceiveForPage(SwVentureReceiveModel model, Page page) {
		return this.getListByKey("querySwVentureReceiveForPage", model, page);
	}
	/**
	 * 合资车收货数据界面导出
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwVentureReceiveModel> exportForQueryPage(SwVentureReceiveModel model) throws Exception {
		return this.getListByKey("querySwVentureReceiveForPage", model);
	}
	/**
	 * 合资车收货数据导入删除上一次导入数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public void deleteLastTimeImportExcel(String uuid) throws Exception {
		this.deleteByKey("deleteLastTimeImportExcel", uuid);
	}
	/**
	 * 合资车收货数据导入
	 * @param importList
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public void importReceiveExcel(List<SwVentureReceiveModel> importList) {
		this.insertBatchByKey("importReceiveExcel", importList);
	}
	
	/**
	 * 合资车收货数据导入校验
	 * @param importList
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public Map<String, Object> checkImportReceiveExcel(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", null);
		resultMap.put("console", null);
		try {
			this.sqlSessionTemplate.selectOne("checkImportReceiveExcel", paramMap);
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			resultMap.put("console", "系统错误,请联系管理员");
		}
		return resultMap;
	}
	/**
	 * 查询导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwVentureReceiveModel> queryImportForPage(String uuid, Page page) {
		return this.getListByKey("queryVentureImportForPage", uuid, page);
	}
	/**
	 * 确认导入数据
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@Override
	public void isImportForRecNum(Map<String, Object> paramMap) {
		this.sqlSessionTemplate.selectOne("isImportForRecNum", paramMap);
	}
	/**
	 * 检查导入数据是否全部校验通过
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@Override
	public Boolean checkImportCount(String uuid) {
		return this.sqlSessionTemplate.selectOne("checkImportCount", uuid);
	}
	/**
	 * 导出导入数据
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwVentureReceiveModel> queryRecImportForExport(String uuid) {
		return this.getListByKey("queryVentureImportForPage", uuid);
	}
	@Override
	public void checkRecForUploadAll(Map<String, Object> paramMap) {
		this.sqlSessionTemplate.selectOne("checkRecForUploadAll", paramMap);
	}
	@Override
	public List<String> selecteNeedCheck(Map<String, Object> paramMap) {
		return this.getByKey("selecteNeedCheck", paramMap);
	}

}
