package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:取货计划查询持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupPlanDao extends Dao<String, PupPlanModel>{
	/**
	 * 取货计划查询业务持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<PupPlanModel> queryPlanForPage(PupPlanModel model, Page page)throws Exception;
	/**
	 * 数据字典项下载状态加载持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<DictVO> getDownloadStatus(Map<String, Object> paramMap)throws Exception;
	/**
	 * 单条/批量删除数据业务持久层接口
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void deletePlans(List<PupPlanModel> list)throws Exception;
	/**
	 * 根据导入UUID删除临时表已存在数据业务持久层接口
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	void deletePlanByUUID(String uuid)throws Exception;
	/**
	 *  将Excel数据写入临时表持久层接口
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void insertToTempTable(List<PupPlanModel> importList)throws Exception;
	/**
	 * 导入数据调用存储过程校验数据接口
	 * @param checkMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void checkImportMessage(Map<String, String> checkMap)throws Exception;
	/**
	 * 查询导入状态接口
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	String queryImportFlag(String uuid)throws Exception;
	/**
	 * 查询到诶数据详情持久层接口
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<PupPlanModel> queryImportPlanForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 查询导入数据的正确条数
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	Integer getCountForImport(Map<String, Object> paramMap)throws Exception;
	/**
	 * 查询正确的可导入正式表的数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<PupPlanModel> queryForRightList(Map<String, Object> paramMap)throws Exception;
	/**
	 * 判断数据是否在正式表存在
	 * @param pupPlanModel
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	Integer judgeIsExistsInFormal(PupPlanModel pupPlanModel)throws Exception;
	/**
	 * 将数据写入正式表
	 * @param pupPlanModel
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void insertIntoFormal(PupPlanModel pupPlanModel)throws Exception;
	/**
	 * 修改正式表已存在的数据
	 * @param pupPlanModel
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void updateFormalData(PupPlanModel pupPlanModel)throws Exception;
	
	/**
	 * 查询需要修改的数据的id持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap)throws Exception;
	/**
	 * 修改导入状态业务持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void updateImportStatus(Map<String, Object> paramMap)throws Exception;
	/**
	 * 临时数据Excel导出持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<PupPlanModel> queryImportForExport(Map<String, String> paramMap)throws Exception;
	/**
	 * 将临时数据表的数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 */
	void insertAndUpdatePlan(Map<String, Object> paramMap)throws Exception;
	/**
	 * 获取导入的总数据条数
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月16日
	 */
	Integer getCountAllImport(Map<String, Object> paramMap)throws Exception;
	/**
	 * 查询数据导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月14日
	 */
	List<PupPlanModel> queryPlanForExport(PupPlanModel model)throws Exception;
	/**
	 * 已发车查询
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<PupPlanModel> queryForDepEnq(PupPlanModel model, Page page)throws Exception;
	/**
	 * 已发车查询导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<PupPlanModel> queryForExportDepEnq(PupPlanModel model)throws Exception;
	
}
