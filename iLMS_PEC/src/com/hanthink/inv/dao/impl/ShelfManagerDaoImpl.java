package com.hanthink.inv.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.ShelfManagerDao;
import com.hanthink.inv.model.ShelfManager;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class ShelfManagerDaoImpl extends MyBatisDaoImpl<String, ShelfManager> implements ShelfManagerDao{

	/**
	 * 货架数据分页查询
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<ShelfManager> querySelfManagerForPage(
			ShelfManager model, DefaultPage p) {
		return (PageList<ShelfManager>) this.getList("queryShelfManagerForPage", model, p);
	}

	
	@Override
	public String getNamespace() {
		return ShelfManager.class.getName();
	}

	/**
	 * 货架数据查询导出
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShelfManager> querySelfManagerExport(ShelfManager model) {
		return (List<ShelfManager>) this.getList("queryShelfManagerxport", model);
	}

	/**
	 * 查询打印数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShelfManager> queryPrintModel(ShelfManager model) {
		return(List<ShelfManager>) this.getList("queryShelfManagerToPrint", model);
	}
	
	/**
	 * 新增货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean insertModel(ShelfManager model) {
		int insertByKey = this.insertByKey("createShelves", model);
		if (insertByKey > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 修改货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean updateShelvesManager(ShelfManager model) {
		int insertByKey = this.updateByKey("updateShelves", model);
		if (insertByKey > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean removeShelvesManager(ShelfManager model) {
		int deleteByKey = this.deleteByKey("removeShelves", model.getId());
		if (deleteByKey > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 分页查询导入的临时数据
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<ShelfManager> queryShelfManagerForPageTemp(ShelfManager model, DefaultPage p) {
		
		return (PageList<ShelfManager>) this.getList("queryShelfManagerForPageTemp", model, p);
	}

	/**
	 * excel导入临时数据
	 * @param file
	 * @return
	 * Lxh
	 */
	@Override
	public boolean insertShelfManagerTemp(List<ShelfManager> importList) {
		int insertByKey  = 0;
		for (ShelfManager shelfManagerTemp : importList) {
			insertByKey = this.insertByKey("createShelvesTemp", shelfManagerTemp);
			if (insertByKey > 0) {
				insertByKey ++;
			}
		}
		if (insertByKey == importList.size()) {
			return true;
		}
		return false;
		
	}

	/**
	 * 确认提交
	 * 
	 * Lxh
	 */
	@Override
	public void ImportData(Map<String, String> ckParamMap) {
		//this.sqlSessionTemplate.selectOne("importShelves");
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".importShelves", ckParamMap);
		
	}

	/**
	 * 查询是否存在导入但未提交的数据
	 * @param model
	 * @param page
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<ShelfManager> queryPartMaintenanceTemp(ShelfManager model, DefaultPage page) {
		// TODO Auto-generated method stub
		return (PageList<ShelfManager>) this.getList("queryPartMaintenanceTemp", model,page);
	}

	/**
	 * 导出临时数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShelfManager> exportTempData(ShelfManager model) {
		return (List<ShelfManager>) this.getList("exportTempData", model);
	}

	/**
	 * 查询零件号
	 * @param model
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShelfManager> queryPartNo(ShelfManager model) {
		return (List<ShelfManager>) this.getList("queryPartNo", model);
	}

	/**
	 * 查询供应商编号
	 * @param model
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShelfManager> querySupplierNoByPartNo(ShelfManager model) {
		return (List<ShelfManager>) this.getList("querySupplierNoByPartNo", model);
	}

	/**
	 * 查询供应商编号
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean checkPartNoAndSupplierNoIsMaintain(ShelfManager m) {
		 @SuppressWarnings("unchecked")
		List<ShelfManager> list = (List<ShelfManager>) this.getList("checkPartNoAndSupplierNoIsMaintain", m);
		 if (list != null && list.size() > 0) {
			 
			 return true;
		 }
		return false;
	}

/**
 * 临时数据删除
 */
	@Override
	public int removeShelvesManagerTemp() {
		int deleteByKey = this.deleteByKey("removeShelvesManagerTemp");
		return deleteByKey;
	}



}
