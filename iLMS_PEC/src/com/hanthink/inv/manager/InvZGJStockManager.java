package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.inv.model.InvZGJStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: InvZGJStockManager
 * @Description: 支给件库存管理
 * @author dtp
 * @date 2019年4月9日
 */
public interface InvZGJStockManager extends Manager<String, InvZGJStockModel>{
	
	/**
	 * @Description: 分页数据查询业务接口  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: PageList<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	PageList<InvZGJStockModel> queryStockForPage(InvZGJStockModel model, Page page)throws Exception;
	
	/**
	 * @Description:  加载单条数据详情业务接口
	 * @param: @param id
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: InvZGJStockModel   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	InvZGJStockModel queryStockById(String id)throws Exception;
	
	/**
	 * @Description: 修改安全库存数业务接口  
	 * @param: @param model
	 * @param: @param ipAddr
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	void updateForSafeStockNum(InvZGJStockModel model,String ipAddr)throws Exception;
	
	/**
	 * @Description:  库存管理查询数据导出业务接口 
	 * @param: @param model
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	List<InvZGJStockModel> queryExportDataForExcel(InvZGJStockModel model)throws Exception;
	
	/**
	 * @Description:  批量修改库存数据 
	 * @param: @param list
	 * @param: @param maxStock
	 * @param: @param minStock
	 * @param: @param ipAddr
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	void batchUpdateStock(List<InvZGJStockModel> list, String maxStock, String minStock, String stock,String ipAddr)throws Exception;

	/**
	 * @Description:  加载仓库名称下拉框 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvUnloadPortModel>   
	 * @author: dtp
	 * @date: 2019年4月10日
	 */
	List<InvUnloadPortModel> queryWareCodeLsit(InvUnloadPortModel model);

	
}
