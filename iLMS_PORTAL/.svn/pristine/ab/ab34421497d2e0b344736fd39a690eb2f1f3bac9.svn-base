package com.hanthink.sw.manager;



import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwMaterialOrderManager  extends Manager<String, SwMaterialOrderModel>{

	/**
	 * 资材订单分页查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<SwMaterialOrderModel> queryMaterialOrderPage(SwMaterialOrderModel model, DefaultPage p);

	/**
	 * 资材订单导出
	 * @param model
	 * @return
	 * Lxh
	 */
	List<SwMaterialOrderModel> downloadMaterialOrder(SwMaterialOrderModel model);

	/**
	 * 交货反馈修改
	 * @param model
	 * @return
	 * Lxh
	 */
	Boolean updateMaterialOrderPage(SwMaterialOrderModel model);

	/**
	 * 标签打印
	 * @param orderModel
	 * @return
	 * Lxh
	 */
	List<SwMaterialOrderModel> queryDemondOrderPrintLabelList(SwMaterialOrderModel orderModel);

	/**
	 * 交货反馈临时数据新增
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	Boolean insertMaterialOrderReturnTmp(SwMaterialOrderModel model);

	/**
	 * 交货反馈临时数据查询
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	PageList<SwMaterialOrderModel> queryMaterialOrderReturnTmp(SwMaterialOrderModel model, DefaultPage p);
	
    /**
     * 判断反馈数量汇总是否满足订单总数
     * @param model
     * @return
     * Lxh
     */
	Boolean checkMeetDelivery(SwMaterialOrderModel model);

	/**
	 * 获取反馈条数
	 * @param model
	 * @return
	 * Lxh
	 */
	int getMaterialOrderReturnTmpCount(SwMaterialOrderModel model);

	/**
	 * 更新反馈状态
	 * @param model
	 * @return
	 * Lxh
	 */
	Boolean updateReturnStatus(SwMaterialOrderModel model);

	/**
	 * 反馈临时数据提交到业务
	 * @param model
	 * @return
	 * Lxh
	 */
	Boolean MaterialOrderReturnTmpToOrder(SwMaterialOrderModel model);

	/**
	 * 交货反馈临时数据修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	Boolean updateMaterialOrderReturnTmp(SwMaterialOrderModel model);

	/**
	 * 交货反馈临时数据删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	void deleteMaterialOrderReturnTmp(SwMaterialOrderModel model);

	/**
	 * 根据uuid删除临时表数据
	 * @param uuid
	 * @throws Exception
	 *
	 */
	void deleteTempMaterialOrderByUUID(String uuid) throws Exception;

	/**
	 * 将Excel数据写入临时数据表
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * 
	 */
	Map<String, Object> importMaterialOrderModel(MultipartFile file, String uuid, String ipAddr);

	/**
	 * 查询分页导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * 
	 */
	PageList<SwMaterialOrderModel> queryImportForPage(String uuid, Page page);

	
	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * 
	 * 
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * 将临时数据表信息写入正式表
	 * @param paramMap
	 * @throws Exception
	 * 
	 */
	void insertTempToFormal(Map<String, Object> paramMap);

	/**
	 * 导入数据详情Excel导出
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * 
	 */
	List<SwMaterialOrderModel> exportForImport(String uuid);

	/**
	 * 修改打印状态
	 * <p>return: void</p>  
	 * <p>Description: SwMaterialOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月24日
	 * @version 1.0
	 */
	void updatePrintStatus(String ipAddr, SwMaterialOrderModel model);



}
