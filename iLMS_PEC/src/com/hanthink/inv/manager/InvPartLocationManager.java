package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvPartLocationModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: InvPartLocationManager
 * @Description: 零件属地维护
 * @author dtp
 * @date 2019年2月16日
 */
public interface InvPartLocationManager extends Manager<String, InvPartLocationModel>{

	/**
	 * @Description: 分页查询零件属地  
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
	 * @return: InvPartLocationModel   
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
	 * @Description:  excel批量导入 
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	Map<String, Object> importInvPartLocation(MultipartFile file, String uuid, String ipAddr);

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
	 * @Description: 确定导入，将临时表数据写入到正式业务表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	void insertImportData(Map<String, String> paramMap);

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
