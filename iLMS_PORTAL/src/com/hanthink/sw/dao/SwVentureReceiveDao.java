package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwVentureReceiveModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface SwVentureReceiveDao extends Dao<SwVentureReceiveModel, String>{
	/**
	 * 合资车收货数据界面查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月21日
	 */
	List<SwVentureReceiveModel> querySwVentureReceiveForPage(SwVentureReceiveModel model, Page page);
	/**
	 * 合资车收货数据界面导出
	 * @param model
	 * @param page
	 * @return
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
	 * @param importList
	 * @author zmj
	 * @date 2019年8月21日
	 */
	void importReceiveExcel(List<SwVentureReceiveModel> importList);
	/**
	 * 合资车收货数据导入校验
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月21日
	 */
	Map<String, Object> checkImportReceiveExcel(Map<String, Object> paramMap);
	/**
	 * 查询导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	List<SwVentureReceiveModel> queryImportForPage(String uuid, Page page);
	/**
	 * 确认导入数据
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月22日
	 */
	void isImportForRecNum(Map<String, Object> paramMap);
	/**
	 * 检查导入数据是否全部校验通过
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	Boolean checkImportCount(String uuid);
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
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月22日
	 */
	void checkRecForUploadAll(Map<String, Object> paramMap);
	List<String> selecteNeedCheck(Map<String, Object> paramMap);

}
