package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.mp.model.MpTrialPlanModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * <pre> 
 * 描述：新车型计划维护 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpTrialPlanManager extends Manager<String, MpTrialPlanModel>{

	 /**
     * 分页查询零件新车型计划维护
     * @param model
     * @param p
     * @return
     */
    PageList<MpTrialPlanModel> queryMpTrialPlanForPage(MpTrialPlanModel model, DefaultPage p);
	
	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午10:27:10
	 */
	Map<String, Object> importMpTrialPlanModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception;

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:51:23
	 */
	PageList<MpTrialPlanModelImport> queryMpTrialPlanImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 下午12:20:34
	 */
	void insertMpTrialPlanImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:24
	 */
	void deleteMpTrialPlanImportTempDataByUUID(String uuid);

	/**
	 * 大数据量Excel导入Demo
	 * @param file
	 * @param uuid
	 * @param opeIp
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午9:36:10
	 */
	Map<String, Object> importMpTrialPlanModel2(MultipartFile file, String uuid, String opeIp);

	/**
	 * 导出数据指定集合
	 * @param model
	 * @return
	 */
	List<MpTrialPlanModel> queryMpTrialPlanByKey(MpTrialPlanModel model);
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	List<MpTrialPlanModelImport> queryMpTrialPlanImportTempDataForExport(Map<String, String> paramMap);

	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param model
	 * @return
	 */
	List<String> querySortIdAndLogByCalStatus();
	
	/**
	 * 直接删除未订购数据
	 * @param model
	 * @return
	 */
	void removeAndLogByCalStatus(List<String> listIds,String ipAddr);

	/**
	 * 获取新车型计划
	 * <p>return: void</p>  
	 * <p>Description: MpTrialPlanManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer getMpTrialPlan(String curFactoryCode);
	
}
