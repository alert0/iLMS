package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwZCOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SwZCOrderManager
 * @Description: 资材订单manager
 * @author dtp
 * @date 2019年3月1日
 */
public interface SwZCOrderManager extends Manager<String, SwZCOrderModel>{

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
	 * @Description: 导入excel数据  
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	Map<String, Object> importZCOrder(MultipartFile file, String uuid, String ipAddr);

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
	 * @Description: 资材反馈  
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void insertZCOrderFeedBack(SwZCOrderModel model, String ipAddr);

	/**
	 * @Description:  修改反馈
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	void updateZCOrderFeedBack(SwZCOrderModel model, String ipAddr);

	/**
	 * @Description: 资材PC端反馈 
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月7日
	 */
	void zcPCFeedback(SwZCOrderModel[] models, String ipAddr);

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
	void updatePrintInfo(List<SwZCOrderModel> list_printInfo);

	/**
	 * @Description: 查询资材标签打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月14日
	 */
	List<SwZCOrderModel> queryZCOrderLabelDetailList(SwZCOrderModel model_q);

}
