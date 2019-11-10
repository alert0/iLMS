package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.inv.dao.InvZGJStockDao;
import com.hanthink.inv.manager.InvZGJStockManager;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.inv.model.InvZGJStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: InvZGJStockManagerImpl
 * @Description: 支给件库存管理ManagerImpl
 * @author dtp
 * @date 2019年4月9日
 */
@Service("InvZGJStockManager")
public class InvZGJStockManagerImpl extends AbstractManagerImpl<String, InvZGJStockModel> implements 
		InvZGJStockManager{

	@Resource
	private InvZGJStockDao invZGJStockDao; 
	
	@Override
	protected Dao<String, InvZGJStockModel> getDao() {
		return invZGJStockDao;
	}
	
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
	@Override
	public PageList<InvZGJStockModel> queryStockForPage(InvZGJStockModel model, Page page) throws Exception {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvZGJStockModel> list = invZGJStockDao.queryStockForPage(model,page);
			
		return new PageList<InvZGJStockModel>(list);
	}
	
	/**
	 * @Description:  加载单条数据详情业务接口
	 * @param: @param id
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: InvZGJStockModel   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	@Override
	public InvZGJStockModel queryStockById(String id) throws Exception {
		if(StringUtil.isEmpty(id)) {
			throw new Exception("未选择数据");
		}
		return invZGJStockDao.queryStockById(id);
	}
	
	/**
	 * @Description: 修改安全库存数业务接口  
	 * @param: @param model
	 * @param: @param ipAddr
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	@Override
	public void updateForSafeStockNum(InvZGJStockModel model,String ipAddr) throws Exception {
		if(StringUtil.isEmpty(model.getId())) {
			throw new Exception("获取参数失败,请联系管理人员");
		}
		//记录更新日志
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_STOCK");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		//(修改库存数量)
		invZGJStockDao.updateForSafeStockNum(model);
	}
	
	/**
	 * @Description:  库存管理查询数据导出业务接口 
	 * @param: @param model
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: List<InvZGJStockModel>   
	 * @author: dtp
	 * @date: 2019年4月9日
	 */
	@Override
	public List<InvZGJStockModel> queryExportDataForExcel(InvZGJStockModel model) throws Exception {		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvZGJStockModel> list = invZGJStockDao.queryExportDataForExcel(model);
		
		return list;
	}
	
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
	@Override
	public void batchUpdateStock(List<InvZGJStockModel> list, String maxStock, String minStock,
			 String stock, String ipAddr) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("maxStock", maxStock);
		paramMap.put("minStock", minStock);
		paramMap.put("stock", stock);
		paramMap.put("list", list);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			String[] ids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = list.get(i).getId();
			}
			//设置日志记录条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr);
			logVO.setFromName("批量修改");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_STOCK");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			//修改数据
			invZGJStockDao.batchUpdateStock(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @Description:  加载仓库名称下拉框 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvUnloadPortModel>   
	 * @author: dtp
	 * @date: 2019年4月10日
	 */
	@Override
	public List<InvUnloadPortModel> queryWareCodeLsit(InvUnloadPortModel model) {
		return invZGJStockDao.queryWareCodeLsit(model);
	}

}
