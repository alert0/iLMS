package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwZCOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SwZCOrderDao
 * @Description: 资材订单
 * @author dtp
 * @date 2019年3月1日
 */
public interface SwZCOrderDao extends Dao<String, SwZCOrderModel>{

	/**
	 * @Description:  资材订单查询    
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月1日
	 */
	PageList<SwZCOrderModel> queryZCOrderPage(SwZCOrderModel model, DefaultPage page);

	/**
	 * @Description: 资材订单导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月1日
	 */
	List<SwZCOrderModel> queryZCOrderList(SwZCOrderModel model);

	/**
	 * 删除导入数据
	 * @Description:   
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 写入临时表   
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void insertSwZCOrderTempData(List<SwZCOrderModel> importList);

	/**
	 * @Description: 查询导入校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * @Description:  写入反馈表 
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * @Description:  查询临时表数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	PageList<SwZCOrderModel> queryImportTempPage(SwZCOrderModel model, DefaultPage page);

	/**
	 * @Description:  资材反馈导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	List<SwZCOrderModel> queryImportTempList(SwZCOrderModel model);

	/**
	 * @Description:  资材反馈
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void insertZCOrderFeedBack(SwZCOrderModel model);

	/**
	 * @Description: 修改反馈  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void updateZCOrderFeedBack(SwZCOrderModel model);

	/**
	 * @Description: 资材PC端反馈  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月7日
	 */
	void zcPCFeedback(SwZCOrderModel model);

	/**
	 * @Description: 查询资材订单打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月10日
	 */
	List<SwZCOrderModel> queryZCOrderPrintDetailList(SwZCOrderModel model_q);

	/**
	 * @Description: 更新资材订单打印状态 
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月10日
	 */
	void updatePrintInfo(SwZCOrderModel model);

	/**
	 * @Description: 调用存储校验临时表数据  
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月12日
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * @Description: 更新临时表导入状态  
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月12日
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

	/**
	 * @Description: 查询资材标签打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月14日
	 */
	List<SwZCOrderModel> queryZCOrderLabelDetailList(SwZCOrderModel model_q);

	/**
	 * @Description: 更新mm_sw_order表打印状态  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月9日
	 */
	void updateSwOrderPrintInfo(SwZCOrderModel model);

}
