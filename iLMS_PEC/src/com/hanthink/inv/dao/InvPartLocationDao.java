package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvPartLocationModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: InvPartLocationDao
 * @Description: 零件属地维护
 * @author dtp
 * @date 2019年2月16日
 */
public interface InvPartLocationDao extends Dao<String, InvPartLocationModel>{

	/**
	 * @Description:  分页查询零件属地 数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	PageList<InvPartLocationModel> queryInvPartLocationPage(InvPartLocationModel model, DefaultPage page);

	/**
	 * @Description:  查询零件属地 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	List<InvPartLocationModel> queryInvPartLocationList(InvPartLocationModel model);

	/**
	 * @Description:  零件数据维护查询货架标签打印信息 
	 * @param: @param model
	 * @param: @return    
	 * @return: PartMainTenanance   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	InvPartLocationModel queryInvShelfPrintInfo(InvPartLocationModel model);

	/**
	 * @Description: 根据uuid删除导入临时数据   
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 根据卸货口获取仓库代码
	 * @param: @param m
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	List<InvPartLocationModel> queryWareCodeByOldUnloadPort(InvPartLocationModel m);

	/**
	 * @Description: 根据卸货口获取仓库代码
	 * @param: @param m
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	List<InvPartLocationModel> queryWareCodeByNewUnloadPort(InvPartLocationModel m);

	/**
	 * @Description: 导入数据到临时表
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void insertInvPartLocationTempData(List<InvPartLocationModel> importList);

	/**
	 * @Description: 调用存储过程校验导入数据 
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * @Description: 查询临时表数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	PageList<InvPartLocationModel> queryImportTempPage(InvPartLocationModel model, DefaultPage page);

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	List<InvPartLocationModel> queryImportTempList(InvPartLocationModel model);

	/**
	 * @Description: 查询导入校验结果
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * @Description: 临时表写入正式表(新增)
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void insertImportDataAdd(Map<String, String> paramMap);

	/**
	 * @Description:  更新临时表数据导入状态
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

	/**
	 * @Description:  临时表写入正式表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void updateImportData(Map<String, String> paramMap);

	/**
	 * @Description: 查询废止数据 
	 * @param: @param paramMap
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	List<InvPartLocationModel> queryFZList(Map<String, String> paramMap);

	/**
	 * @Description: 更新废止信息到正式表 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void updateImportDataFZ(InvPartLocationModel model);

	/**
	 * @Description: 查询临时表移动数据
	 * @param: @param paramMap
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	List<InvPartLocationModel> queryYDList(Map<String, String> paramMap);

	/**
	 * @Description: 更新移动数据到正式表  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void updateImportDataYD(InvPartLocationModel model);

	/**
	 * @Description: 零件属地维护日志查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	PageList<InvPartLocationModel> queryInvPartLocationLogPage(InvPartLocationModel model, DefaultPage page);

	/**
	 * @Description: 属地维护日志导入  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	List<InvPartLocationModel> queryInvPartLocationLogList(InvPartLocationModel model);

	

}
