package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.inv.model.InvZGJStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

/**
 * @ClassName: InvZGJStockDao
 * @Description: 支给件库存管理Dao
 * @author dtp
 * @date 2019年4月9日
 */
public interface InvZGJStockDao extends Dao<String, InvZGJStockModel>{

	/**
	 * @Description: 分页查询业务持久层接口  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	List<InvZGJStockModel> queryStockForPage(InvZGJStockModel model, Page page)throws Exception;
	
	/**
	 * @Description: 查看明细
	 * @param: @param id
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: InvZGJStockModel   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	InvZGJStockModel queryStockById(String id)throws Exception;
	
	/**
	 * @Description: 获取零件收容数  
	 * @param: @param model
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: Integer   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	Integer queryStandPackageForPart(InvZGJStockModel model)throws Exception;
	
	/**
	 * @Description: 修改库存
	 * @param: @param model
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	void updateForSafeStockNum(InvZGJStockModel model)throws Exception;
	
	/**
	 * @Description: 查询导出数据  
	 * @param: @param model
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	List<InvZGJStockModel> queryExportDataForExcel(InvZGJStockModel model)throws Exception;
	
	/**
	 * @Description: 批量修改库存
	 * @param: @param paramMap
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	void batchUpdateStock(Map<String, Object> paramMap)throws Exception;

	/**
	 * @Description:  加载仓库名称下拉框 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvUnloadPortModel>   
	 * @author: dtp
	 * @date: 2019年4月10日
	 */
	List<InvUnloadPortModel> queryWareCodeLsit(InvUnloadPortModel model);

	/**
	 * @Description: 查询收容数
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月12日
	 */
	List<InvZGJStockModel> queryStandPackageForPartList(InvZGJStockModel model);
}
