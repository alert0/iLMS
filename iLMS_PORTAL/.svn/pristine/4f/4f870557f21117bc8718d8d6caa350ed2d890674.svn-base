package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwVentureReceiveModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

public interface SwVentureReceiveManager extends Manager<SwVentureReceiveModel, String>{
	/**
	 * 合资车收货数据界面查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月21日
	 */
	PageList<SwVentureReceiveModel> querySwVentureReceiveForPage(SwVentureReceiveModel model, Page page);
	/**
	 * 合资车收货数据界面导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	List<SwVentureReceiveModel> exportForQueryPage(SwVentureReceiveModel model)throws Exception;
	/**
	 * 合资车收货数据导入删除上一次导入数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	void deleteLastTimeImportExcel(String uuid)throws Exception;
	/**
	 * 合资车收货数据导入
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @param user
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	Map<String, Object> importReceiveExcel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception;
	/**
	 * 导入数据查询
	 * @param uuid
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	PageList<SwVentureReceiveModel> queryImportForPage(String uuid, Page page);
	/**
	 * 检查导入数据是否全部校验通过
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	Boolean checkImportCount(String uuid);
	/**
	 * 确认导入数据
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月22日
	 */
	void isImportForRecNum(Map<String, Object> paramMap);
	/**
	 * 导出导入数据
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	List<SwVentureReceiveModel> queryRecImportForExport(String uuid);
	/**
	 * 收货数据审核
	 * @author zmj
	 * @param paramMap 
	 * @date 2019年8月22日
	 */
	void checkRecForUploadAll(Map<String, Object> paramMap);
}
