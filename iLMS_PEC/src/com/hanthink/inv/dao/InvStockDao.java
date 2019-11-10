package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface InvStockDao extends Dao<String, InvStockModel>{
	/**
	 * 分页查询业务持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	List<InvStockModel> queryStockForPage(InvStockModel model, Page page)throws Exception;
	/**
	 * 单条数据详情查询持久层接口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	InvStockModel queryStockById(String id)throws Exception;
	/**
	 * 获取零件收容数
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月28日
	 */
	Integer queryStandPackageForPart(InvStockModel model)throws Exception;
	/**
	 * 修改安全库存数持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	void updateForSafeStockNum(InvStockModel model)throws Exception;
	/**
	 * 库存管理查询数据导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	List<InvStockModel> queryExportDataForExcel(InvStockModel model)throws Exception;
	/**
	 *  批量修改库存数据
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月18日
	 */
	void batchUpdateStock(Map<String, Object> paramMap)throws Exception;
	void insertToTempStock(List<InvStockModel> importList)throws Exception;
	void checkImportDataInformation(Map<String, String> checkMap)throws Exception;
	boolean queryStockImportFlag(String uuid)throws Exception;
	List<InvStockModel> queryImportTemp(Map<String, String> paramMap, Page page)throws Exception;
	void delTempDataByUUID(String uuid)throws Exception;
	void insertTempDataToFormal(Map<String, String> paramMap)throws Exception;
	void updateTempImportStatus(Map<String, String> paramMap)throws Exception;
	List<InvStockModel> exportImportData(Map<String, String> paramMap)throws Exception;
	List<InvStockModel> calDiffFlag(Map<String, String> paramMap)throws Exception;
	void insertToStockTakeHeader(Map<String, String> paramMap)throws Exception;
	void insertToStockTakeBody(List<InvStockModel> calList)throws Exception;
	/**
	 * 将数据写入头表
	 * @param paramMap
	 * @throws Exception
	 * @author zfz
	 * @date 2019年6月19日
	 */
	void insertToInvOut(Map<String, String> paramMap)throws Exception;
	/**
	 * 将数据写入明细表
	 * @param paramMap
	 * @throws Exception
	 * @author zfz
	 * @date 2019年6月19日
	 */
	void insertToInvOutDetail(Map<String, String> paramMap)throws Exception;
}
