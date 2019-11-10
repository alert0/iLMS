package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupRouteMessageModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.SwSupplierGroupModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: SwSupplierGroupDao.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月11日
*/

public interface SwSupplierGroupDao extends Dao<String, SwSupplierGroupModel>{

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
	* @date   2018年10月11日 下午5:51:55
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
	* @date   2018年10月11日 下午6:20:04
	 */
	SwSupplierGroupModel curdGetGroup(String groupId);

	/**
	 * 
	* @Title: getByGroupName 
	* @Description: 检查分组名称是否已存在 
	* @param @param groupName
	* @param @return    
	* @return SwSupplierGroupModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午10:42:35
	 */
	SwSupplierGroupModel getByGroupName(String groupName);

	/**
	 * 
	* @Title: removeByGroupIds 
	* @Description: 删除分组信息 
	* @param @param groupIds    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午10:22:02
	 */
	void removeByGroupIds(String[] groupIds);

	/**
	 * 
	* @Title: updateGroup 
	* @Description: 修改供应商分组信息 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午10:33:37
	 */
	void updateGroup(SwSupplierGroupModel swSupplierGroupModel);

	/**
	 * 
	* @Title: insertGroup 
	* @Description: 新增供应商分组信息 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:07:49
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
	* @date   2018年10月12日 上午11:29:26
	 */
	PageList<SwSupplierGroupModel> queryJisoSupplierPage(SwSupplierGroupModel model, DefaultPage p);

	/**
	 * 
	* @Title: insertGroupId 
	* @Description: groupId插入从表 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:55:57
	 */
	void insertGroupId(SwSupplierGroupModel swSupplierGroupModel);

	/**
	 * 
	* @Title: getSupplierListUnGroup 
	* @Description: 分页查询未分组供应信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午3:36:30
	 */
	PageList<SwSupplierGroupModel> getSupplierListUnGroup(SwSupplierGroupModel model, DefaultPage p);

	/**
	 * 
	* @Title: removeSupplierByGroupIds 
	* @Description: 删除供应商信息 
	* @param @param groupId
	* @param @param supplierNos    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午5:46:00
	 */
	void removeSupplierByGroupIds(String groupId, String[] supplierNos);

	/**
	 * 
	* @Title: querySwSupplierGroupByKey 
	* @Description: 查询出需要导出的供应商分组数据 
	* @param @param model
	* @param @return    
	* @return List<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午9:47:19
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
	* @date   2018年10月13日 上午11:10:45
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
	* @date   2018年10月13日 上午11:26:47
	 */
	PageList<SwSupplierGroupModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	* @Title: insertImportTempData 
	* @Description: 数据写入供应商信息临时表 
	* @param @param importList    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:05:22
	 */
	void insertImportTempData(List<SwSupplierGroupModelImport> importList);

	/**
	 * 
	* @Title: checkImportData 
	* @Description: 调用存储过程等检查数据准确性  
	* @param @param ckParamMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:15:47
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * 
	* @Title: queryIsImportFlag 
	* @Description: 查询是否可以批量导入 
	* @param @param uuid
	* @param @return    
	* @return String 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:22:17
	 */
	String queryIsImportFlag(String uuid);

	/**
	 * 
	* @Title: insertImportData 
	* @Description: 临时供应商分组维护数据导入正式表 
	* @param @param paramMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午3:13:29
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * 
	* @Title: updateImportDataImpStatus 
	* @Description: 导入成功后更新临时表导入状态 
	* @param @param paramMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午4:00:12
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

}
