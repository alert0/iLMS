package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupPickTimeModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

/**
* <pre> 
* 描述：固定取货时间维护dao层接口类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17
* 版权：汉思信息技术有限公司
* </pre>
*/
public interface PupPickTimeDao extends Dao<String, PupPickTimeModel>{
	
	/**
	 * 查询固定取货时间维护信息详情
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 上午10:18:45
	 */
	List<PupPickTimeModel> queryPickupTimeForPage(PupPickTimeModel model,Page page)throws Exception;
	
	/**
	 * 根据集货路线、当日车次获取固定取货时间详情
	 *@param routeCode
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 上午11:43:29
	 */
	PupPickTimeModel getPickTime(PupPickTimeModel model)throws Exception;

	/**
	 * 新增固定取货时间信息
	 *@param model
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午2:02:47
	 */
	void createPickTime(PupPickTimeModel model)throws Exception;
	
	/**
	 * 修改固定取货时间信息
	 *@param model
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午2:03:10
	 */
	void updatePickTime(PupPickTimeModel model)throws Exception;
	/**
	 * 删除固定取货时间信息
	 *@param models
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月21日 上午11:14:56
	 */
	void removeByRouteCodes(List<PupPickTimeModel> models)throws Exception;
	
	/**
	 * 查询页面需要导出数据
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 上午10:42:11
	 */
	List<PupPickTimeModel> queryPickTimeForExport(PupPickTimeModel model)throws Exception;

	/**
	 * 查询临时表中现有的数据
	 *@param param
	 *@param page
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午12:05:16
	 */
	List<PupPickTimeModel> queryPickTimeForImport(Map<String, String> param,Page page)throws Exception;
	/**
	 * 删除临时表中已存在的数据
	 *@param uuid
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午12:59:52
	 */
	void deletePickTimeTempDataByUUID(String uuid)throws Exception;
	
	/**
	 * 导入数据到临时数据表
	 *@param param
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午1:01:38
	 */
	void inserDataToTempDataTable(List<PupPickTimeModel> list)throws Exception;
	/**
	 * 检查导入临时表的数据
	 *@param ckParamMap
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午1:28:14
	 */
	void checkImportData(Map<String, String> ckParamMap)throws Exception;
	/**
	 * 查询导入状态
	 *@param uuid
	 *@return String
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:15:05
	 */
	String queryImportFlag(String uuid)throws Exception;
	
	/**
	 * 查询导出临时数据
	 *@param param
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:15:28
	 */
	List<PupPickTimeModel> queryPickupTimeTempDataForExport(Map<String, String> param)throws Exception;
	
	/**
	 * 查询需要修改再导入的数据
	 *@param paramMap
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:17:54
	 */
	List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap)throws Exception;
	/**
	 * 修改导入的数据
	 *@param paramMap
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:19:22
	 */
	void updatePickTimeImportData(Map<String, Object> paramMap)throws Exception;
	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月23日 下午9:22:17
	 */
	Integer getCountForImport(Map<String, Object> paramMap)throws Exception;
	/**
	 * 获取需要更新导入的数据详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<String> getUpdateList(Map<String, Object> paramMap)throws Exception;
	/**
	 * 查询需要修改的数据的条数
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Integer getCountForUpdate(Map<String, Object> paramMap)throws Exception;
	/**
	 * 将数据写入正式表
	 *@param paramMap
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:24:42
	 */
	void insertTempDataToRegula(Map<String,Object> paramMap)throws Exception;
	/**
	 * 更新临时数据表数据的导入状态
	 *@param paramMap
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午9:24:11
	 */
	void updatePickTimeImportDataImpStatus(Map<String, Object> paramMap)throws Exception;

	
}
