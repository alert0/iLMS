package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hanthink.pup.model.PupRouteMessagePageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:路线信息维护持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月21日下午11:13:23
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupRouteMessageDao extends Dao<String, PupRouteMessageModel>{
	/**
	 * 条件分页查询数据
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<PupRouteMessageModel> queryRouteMessageForPage(PupRouteMessagePageModel model,Page page)throws Exception;
	/**
	 * 加载数据字典选项
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<DictVO> getBatches()throws Exception;
	/**
	 * 导出数据查询
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<PupRouteMessageModel> queryRouteMessageForExport(PupRouteMessagePageModel model)throws Exception;
	/**
	 * 单个/多条记录删除
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void removeRouteMessagesByIds(String[] ids)throws Exception;
	/**
	 * 根据UUID删除已存在数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void deleteTempRouteMessageByUUID(String uuid)throws Exception;
	/**
	 * 将Excel数据写入临时数据表
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void insertRouteMessageToTempTable(List<PupRouteMessageModel> importList)throws Exception;
	/**
	 * 调用存储过程检查数据
	 * @param map
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void checkImportRouteMessage(Map<String, String> map)throws Exception;
	/**
	 * 查询导入状态
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	String queryImportFlag(String uuid)throws Exception;
	/**
	 * 查询需要修改的数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap)throws Exception;
	/**
	 * 修改导入的数据
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void updateRouteMessageImportData(Map<String, Object> paramMap)throws Exception;
	/**
	 * 将临时表数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void insertTempDataToRegula(Map<String, Object> paramMap)throws Exception;
	/**
	 * 更新临时表数据的导入状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void updateRouteMessageImpStatus(Map<String, Object> paramMap)throws Exception;
	/**
	 * 查询导出数据
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<PupRouteMessageModel> queryImportModelForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 获取单条数据详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	PupRouteMessageModel queryRouteMessageById(String id)throws Exception;
	/**
	 * 修改单条数据
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void updateRouteMessageById(PupRouteMessageModel model)throws Exception;
}
