package com.hanthink.demo.manager;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.demo.model.DemoModel;
import com.hanthink.demo.model.DemoModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface DemoManager extends Manager<String, DemoModel>{

	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	Map<String, Object> importDemoModel(MultipartFile file, String uuid, String opeIp);

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午11:51:23
	 */
	PageList<DemoModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:20:34
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午4:17:24
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 大数据量Excel导入Demo
	 * @param file
	 * @param uuid
	 * @param opeIp
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午9:36:10
	 */
	Map<String, Object> importDemoModel2(MultipartFile file, String uuid, String opeIp);

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午10:54:40
	 */
	void updateAndLog(DemoModel demoModel, String opeIp);

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr);

}
