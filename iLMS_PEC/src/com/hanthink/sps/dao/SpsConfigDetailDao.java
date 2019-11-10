package com.hanthink.sps.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:SPS配置项明细持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年11月8日下午4:41:55
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface SpsConfigDetailDao extends Dao<String, SpsConfigDetailModel>{
	/**
	 * 条件分页数据查询
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> queryConfigDetailsForPage(SpsConfigDetailModel model,Page page)throws Exception;
	/**
	 * 查看零件号在系统是否存在
	 * @param partNo
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Object isExist(String partNo)throws Exception;
	/**
	 * 判断业务主键是否重复
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	boolean isExist(SpsConfigDetailModel model)throws Exception;
	/**
	 * 保存新增数据信息
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	void saveAdd(SpsConfigDetailModel model)throws Exception;
	/**
	 * 获取主键id
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	String getConfigId(SpsConfigDetailModel model)throws Exception;
	/**
	 * 修改配置明细数据信息
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	void updateConfigDetails(SpsConfigDetailModel model)throws Exception;
	/**
	 * 逐条/批量删除数据
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 */
	void removeConfigDetailsByIds(String[] ids)throws Exception;
	/**
	 * 页面数据Excel导出查询
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> exportQueryForExcel(SpsConfigDetailModel model)throws Exception;
	/**
	 * 根据当前uuid删除导入Excel的临时数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 */
	void deleteTempCongfigByUUID(String uuid)throws Exception;
	/**
	 * 将Excel数据写入临时数据表
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 */
	void insertConfigToTemp(List<SpsConfigDetailModel> importList)throws Exception;
	/**
	 * 调用存储过程校验数据正确性
	 * @param checkMap
	 * @throws Exception
	 * @author zmj
	 */
	void ckeckImportConfig(Map<String, String> checkMap)throws Exception;
	/**
	 * Excel数据导入查询
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> queryImportForPage(String uuid, Page page)throws Exception;
	/**
	 * 获取导入的正确数据的条数
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Integer getCountConfigImport(Map<String, Object> paramMap)throws Exception;
	/**
	 * 将临时数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 */
	void insertTempToFormal(Map<String, Object> paramMap)throws Exception;
	/**
	 * 更新数据的导入状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 */
	void updateImportStatus(Map<String, Object> paramMap)throws Exception;
	/**
	 * 将导入的临时数据详情导出至Excel
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> exportForImport(String uuid)throws Exception;
	
	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	int queryIsExistsCheckResultFalse(String uuid);
	
	/**
	 * @Description: 将临时表数据更新到正式表  check_result = 2
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	void updateTempData(Map<String, Object> paramMap);
	
	/**
	 * @Description: 查询更新数据  check_result = 2
	 * @param paramMap
	 * @return
	 */
	List<SpsConfigDetailModel> queryUpdateList(Map<String, Object> paramMap);
	
	/**
	 * @Description: 更新数据  check_result = 2
	 * @author: dtp
	 * @date: 2019年6月14日
	 * @param list_u
	 */
	void updateTempDataList(SpsConfigDetailModel model);
	
}
