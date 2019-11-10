package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mp.model.MpTrialDemandModel;
import com.hanthink.mp.model.MpTrialDemandModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpTrialDemandManager extends Manager<String, MpTrialDemandModel>{

	 /**
     * 分页查询零件剩余量主数据
     * @param model
     * @param p
     * @return
     */
    PageList<MpTrialDemandModel> queryMpTrialDemandForPage(MpTrialDemandModel model, DefaultPage p);
	
	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午10:27:10
	 */
	Map<String, Object> importMpTrialDemandModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception;

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:51:23
	 */
	PageList<MpTrialDemandModelImport> queryMpTrialDemandImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 下午12:20:34
	 */
	void insertMpTrialDemandImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:24
	 */
	void deleteMpTrialDemandImportTempDataByUUID(String uuid);

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(MpTrialDemandModel MpTrialDemandModel, String opeIp);

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr);
	/**
	 * 导出数据指定集合
	 * @param model
	 * @return
	 */
	List<MpTrialDemandModel> queryMpTrialDemandByKey(MpTrialDemandModel model);
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	List<MpTrialDemandModelImport> queryMpTrialDemandImportTempDataForExport(Map<String, String> paramMap);

	/**
	 * 需求计算生成
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer generateMpTrialDemand(String curFactoryCode);

	/**
	 * 需求计算发布
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpTrialDemand(String curFactoryCode);

}
