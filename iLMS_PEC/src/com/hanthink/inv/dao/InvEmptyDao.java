package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * <pre> 
 * 功能描述:空容器查询业务持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyDao extends Dao<String, InvEmptyModel>{
	/**
	 * 空容器分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	List<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page)throws Exception;
	/**
	 * 修改空容器数量持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	void updateForEmpty(InvEmptyModel model)throws Exception;
	/**
	 * 空容器库存导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	List<InvEmptyModel> exportForExcel(InvEmptyModel model)throws Exception;
	/**
	 * 根据UUID删除临时表数据
	 * @param uuid
	 */
	void deleteInvEmptyImportTempDataByUUID(String uuid);
	
	/**
	 * 插入临时表数据
	 * @param importList
	 */
	void insertInvEmptyImportTempData(List<InvEmptyModel> importList);
	/**
	 * 检查临时表数据
	 * @param ckParamMap
	 */
	void checkImportData(Map<String, String> ckParamMap);
	/**
	 * 查询是否可以批量导入
	 * @param uuid
	 * @return
	 */
	String queryInvEmptyIsImportFlag(String uuid);
	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 */
	PageList<InvEmptyModel> queryInvEmptyImportTempData(Map<String, String> paramMap, Page page);
	/**
	 * 查询可导入的数据
	 * @param paramMap
	 * @return
	 */
	List<InvEmptyModel> queryForInsertList(Map<String, Object> paramMap);
	/**
	 * 查询要修改的临时数据
	 * @param paramMap
	 * @return
	 */
	List<InvEmptyModel> queryUpdateDataFromInvEmptyImp(Map<String, Object> paramMap);
	/**
	 * 导入修改的方法
	 * @param ids
	 * @param paramMap 
	 */
	void updateInvEmptyImportData(Map<String, Object> paramMap);
	/**
	 * 导入新增的方法
	 * @param paramMap
	 */
	void insertInvEmptyImportData(Map<String, Object> paramMap);
	/**
	 * 更新临时表数据导入状态
	 * @param paramMap
	 */
	void updateInvEmptyImportDataImpStatus(Map<String, Object> paramMap);
	/**
	 * 导出临时表数据信息
	 * @param paramMap
	 * @return
	 */
	List<InvEmptyModel> queryInvEmptyImportTempDataForExport(Map<String, String> paramMap);
	
	/**
	 * 查询箱种
	 * @param boxType
	 * @return
	 */
	String selectBoxType(String boxType);
	
	/**
	 * 查询车间
	 * @param workCenter
	 * @return
	 */
	String selectWorkCenter(String workCenter);

}
