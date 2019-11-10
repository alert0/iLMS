package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.SwSupplierGroupModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
* <p>Title: SwSupplierGroupManager.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月11日
*/

public interface SwSupplierGroupManager extends Manager<String, SwSupplierGroupModel>{

	/**
	 * 
	* @Title: queryJisoGroupPage 
	* @Description: 分页查询供应商分组信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午5:50:48
	 */
	PageList<SwSupplierGroupModel> queryJisoGroupPage(SwSupplierGroupModel model, DefaultPage p);

	/**
	 * 
	* @Title: curdGetGroup 
	* @Description: 获取供应商分组信息  
	* @param @param groupId
	* @param @return    
	* @return SwSupplierGroupModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午6:19:07
	 */
	SwSupplierGroupModel curdGetGroup(String groupId);

	/**
	 * 
	* @Title: updateAndLog 
	* @Description: 修改供应商分组信息，并记录日志 
	* @param @param swSupplierGroupModel
	* @param @param ipAddr    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午10:37:34
	 */
	void updateAndLog(SwSupplierGroupModel swSupplierGroupModel, String ipAddr);

	/**
	 * 
	* @Title: getByGroupName 
	* @Description: 检查分组名称是否已经存在 
	* @param @param groupName
	* @param @return    
	* @return SwSupplierGroupModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午10:38:10
	 */
	SwSupplierGroupModel getByGroupName(SwSupplierGroupModel swSupplierGroupModel);

	/**
	 * 
	* @Title: removeAndLogByIds 
	* @Description: 删除供应商分组信息，并记录日志 
	* @param @param groupIds
	* @param @param ipAddr    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午9:40:39
	 */
	void removeAndLogByIds(String[] groupIds, String ipAddr);

	/**
	 * 
	* @Title: insertGroup 
	* @Description: 新增供应商分组信息 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:06:56
	 */
	String insertGroup(SwSupplierGroupModel swSupplierGroupModel);

	/**
	 * 
	* @Title: queryJisoSupplierPage 
	* @Description: 分页查询供应商信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:28:36
	 */
	PageList<SwSupplierGroupModel> queryJisoSupplierPage(SwSupplierGroupModel model, DefaultPage p);

	/**
	 * 
	* @Title: insertGroupId 
	* @Description: 将groupId插入从表 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:55:04
	 */
	void insertGroupId(SwSupplierGroupModel swSupplierGroupModel);

	/**
	 * 
	* @Title: getSupplierListUnGroup 
	* @Description: 分页查询未分组供应商信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午3:35:33
	 */
	PageList<SwSupplierGroupModel> getSupplierListUnGroup(SwSupplierGroupModel model, DefaultPage p);

	/**
	 * 
	* @Title: removeSupplierAndLogByIds 
	* @Description: 删除供应商信息 
	* @param @param groupId
	* @param @param supplierNos
	* @param @param ipAddr    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午5:43:09
	 */
	void removeSupplierAndLogByIds(String groupId, String[] supplierNos, String ipAddr);

	/**
	 * 
	* @Title: querySwSupplierGroupByKey 
	* @Description: 查询需要导出的供应商分组数据 
	* @param @param model
	* @param @return    
	* @return List<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午9:46:14
	 */
	List<SwSupplierGroupModel> querySwSupplierGroupByKey(SwSupplierGroupModel model);

	/**
	 * 
	* @Title: deleteImportTempDataByUUID 
	* @Description: 根据IMP_UUID删除导入的临时供应商分组数据 
	* @param @param uuid    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午11:09:28
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	* @Title: queryImportTempData 
	* @Description: 查询导入的临时供应商分组信息订单用量数据 
	* @param @param paramMap
	* @param @param page
	* @param @return    
	* @return PageList<SwSupplierGroupModelImport> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午11:24:27
	 */
	PageList<SwSupplierGroupModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	* @Title: importModel 
	* @Description: 导入文件 
	* @param @param file
	* @param @param uuid
	* @param @param ipAddr
	* @param @param user
	* @param @return    
	* @return Map<String,Object> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午11:40:36
	 */
	Map<String, Object> importModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	* @Title: insertImportData 
	* @Description: 将临时供应商分组维护数据 导入正式表
	* @param @param paramMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午3:11:45
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 公告发布管理，新增界面供应商分组弹窗
	 * @param @return   
	 * @return List<SwSupplierGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午4:24:51
	 */
	List<SwSupplierGroupModel> getSupplierGroupList();

	/**
	 * 
	 * @Description: 导出在导入时需要修改的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwSupplierGroupModelImport>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月28日 上午11:19:06
	 */
	List<SwSupplierGroupModelImport> queryImportInformationForPage(Map<String, String> paramMap);

	/**
	 * @Description:  通UUID校验是否有校验结果不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: Integer   
	 * @author: dtp
	 * @date: 2019年1月13日
	 */
	Integer queryIsExistsNotOk(String uuid);

}
