package com.hanthink.demo.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.demo.model.DemoModel;
import com.hanthink.demo.model.DemoModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

public interface DemoDao extends Dao<String, DemoModel> {

	/**
	 * 导入数据写入到临时表
	 * @param importList
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午11:08:11
	 */
	void insertImportTempData(List<DemoModelImport> importList);

	/**
	 * 检查导入到临时表数据准确性
	 * @param ckParamMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午11:08:35
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:29:41
	 */
	PageList<DemoModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将导入的临时数据写入到正式表
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:30:07
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * 导入临时表导入状态
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 上午10:03:12
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

	/**
	 * 根据导入的UUID删除临时数据
	 * @param uuid
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午4:18:34
	 */
	void deleteImportTempDataByUUID(String uuid);

}
