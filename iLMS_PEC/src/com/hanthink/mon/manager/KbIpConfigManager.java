package com.hanthink.mon.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mon.model.KbIpConfigModel;
import com.hotent.base.api.Page;
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
public interface KbIpConfigManager extends Manager<String, KbIpConfigModel>{

	 /**
     * 分页查询零件剩余量主数据
     * @param model
     * @param page
     * @return
     */
    PageList<KbIpConfigModel> queryKbIpConfigForPage(KbIpConfigModel model, Page page)throws Exception;

	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午10:27:10
	 */
	Map<String, Object> importKbIpConfigModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception;

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:51:23
	 */
	PageList<KbIpConfigModel> queryKbIpConfigImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 下午12:20:34
	 */
	void insertKbIpConfigImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:24
	 */
	void deleteKbIpConfigImportTempDataByUUID(String uuid);

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(KbIpConfigModel KbIpConfigModel, String opeIp)throws Exception;

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;
	/**
	 * 导出数据指定集合
	 * @param model
	 * @return
	 */
	List<KbIpConfigModel> queryKbIpConfigByKey(KbIpConfigModel model);
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	List<KbIpConfigModel> queryKbIpConfigImportTempDataForExport(Map<String, String> paramMap);
	
	/**
	 * 判断主表主键是否冲突
	 * @param KbIpConfigModel
	 * @return 
	 */
	Integer selectPrimaryKey(KbIpConfigModel KbIpConfigModel);
	
	/**
	 * 判断明细主键是否冲突
	 * <p>return: Integer</p>  
	 * <p>Description: KbIpConfigManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	Integer selectPrimaryKeyDetail(KbIpConfigModel kbIpConfigModel);
	
	/**
	 * 新增明细
	 * <p>return: void</p>  
	 * <p>Description: KbIpConfigManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	void createDetail(KbIpConfigModel kbIpConfigModel);
	
	List<KbIpConfigModel> queryKbList()throws Exception;
	Integer getCurrBussId() throws Exception;
	PageList<KbIpConfigModel> queryKbTypeForPage(KbIpConfigModel model, Page page)throws Exception;
	void saveKbType(KbIpConfigModel kbIpConfigModel, String ipAddr)throws Exception;
}
