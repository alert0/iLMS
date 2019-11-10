package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupManualOrderModel;
import com.hanthink.pup.model.PupManualOrderPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:手工调整订单持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupManualOrderDao extends Dao<String,PupManualOrderModel>{
	/**
	 * 手工调整订单查询业务持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	List<PupManualOrderModel> queryManualOrderForPage(PupManualOrderPageModel model,Page page)throws Exception;
	/**
	 * 手工调整订单删除数据持久层接口
	 * @param purchaseNo
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	void removeManualOders(String[] purchaseNo)throws Exception;
	/**
	 * 手工调整订单页面数据导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	List<PupManualOrderModel> queryManualOederForExport(PupManualOrderPageModel model)throws Exception;
	/**
	 * 根据UUID删除临时表数据持久层接口
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void deleteTempManualOrderByUUID(String uuid)throws Exception;
	/**
	 * 将Excel数据写入临时表持久层接口
	 * @param models
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void insertManualOrderToTempTable(List<PupManualOrderModel> models)throws Exception;
	/**
	 * 调用存储过程检查数据持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void checkImportManualOrder(Map<String, String> paramMap)throws Exception;
	/**
	 * 查询数据导入状态持久层接口
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	String queryImportFlag(String uuid)throws Exception;
	/**
	 * 导出导入数据的查询持久层接口
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<PupManualOrderModel> queryImportManualOrderForPage(Map<String, String> paramMap,Page page)throws Exception;
	/**
	 * 查询需要修改的数据持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap)throws Exception;
	/**
	 * 修改导入数据持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void updateManualOrderImportData(Map<String, Object> paramMap)throws Exception;
	/**
	 * 将临时表数据写入正式表持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void insertTempDataToRegula(Map<String, Object> paramMap)throws Exception;
	/**
	 * 修改临时表数据的导入状态持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void updateManualOrderImportDataImpStatus(Map<String, Object> paramMap)throws Exception;
	/**
	 * 导出导入数据的查询持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<PupManualOrderModel> queryManualOrderTempDataForExport(Map<String, String> paramMap)throws Exception;
}
