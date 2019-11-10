package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface InvStockManager extends Manager<String, InvStockModel>{
	/**
	 * 分页数据查询业务接口
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	PageList<InvStockModel> queryStockForPage(InvStockModel model, Page page)throws Exception;
	/**
	 * 加载单条数据详情业务接口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	InvStockModel queryStockById(String id)throws Exception;
	/**
	 * 修改安全库存数业务接口
	 * @param model
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	void updateForSafeStockNum(InvStockModel model,String ipAddr)throws Exception;
	/**
	 * 库存管理查询数据导出业务接口
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	List<InvStockModel> queryExportDataForExcel(InvStockModel model)throws Exception;
	/**
	 * 批量修改库存数据
	 * @param list
	 * @param maxStock
	 * @param minStock
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月18日
	 */
	void batchUpdateStock(List<InvStockModel> list, String maxStock, String minStock, String ipAddr)throws Exception;
	Map<String, Object> importStock(MultipartFile file, String uuid, String ipAddr)throws Exception;
	void delTempDataByUUID(String uuid)throws Exception;
	PageList<InvStockModel> queryImportTemp(Map<String, String> paramMap, Page page)throws Exception;
	void insertTempDataTpFromal(Map<String, String> paramMap)throws Exception;
	List<InvStockModel> exportImportData(Map<String, String> paramMap)throws Exception;

}
