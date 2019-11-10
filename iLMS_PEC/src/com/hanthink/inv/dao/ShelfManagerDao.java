package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.PartMainTenanance;
import com.hanthink.inv.model.ShelfManager;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface ShelfManagerDao extends Dao<String, ShelfManager>{

	/**
	 * 货架数据分页查询
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	public PageList<ShelfManager> querySelfManagerForPage(
			ShelfManager model, DefaultPage p);

	/**
	 * 货架数据查询导出
	 * @param model
	 * @return
	 * 李兴辉
	 */
	public List<ShelfManager> querySelfManagerExport(ShelfManager model);
	
	/**
	 * 查询打印数据
	 * @param model
	 * @return
	 * Lxh
	 */
	public List<ShelfManager> queryPrintModel(ShelfManager model);

	/**
	 * 新增货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	public boolean insertModel(ShelfManager model);

	/**
	 * 修改货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	public boolean updateShelvesManager(ShelfManager model);

	/**
	 * 删除货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	public boolean removeShelvesManager(ShelfManager model);
	
	/**
	 * 分页查询导入的临时数据
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<ShelfManager> queryShelfManagerForPageTemp(ShelfManager model, DefaultPage p);

	/**
	 * excel导入临时数据
	 * @param file
	 * @return
	 * Lxh
	 */
	public boolean insertShelfManagerTemp(List<ShelfManager> importList);
	
	/**
	 * 确认提交
	 * 
	 * Lxh
	 */
	public void ImportData(Map<String, String> ckParamMap);

	/**
	 * 查询是否存在导入但未提交的数据
	 * @param model
	 * @param page
	 * @return
	 * Lxh
	 */
	public PageList<ShelfManager> queryPartMaintenanceTemp(ShelfManager model, DefaultPage page);

	/**
	 * 导出临时数据
	 * @param model
	 * @return
	 * Lxh
	 */
	public List<ShelfManager> exportTempData(ShelfManager model);

	/**
	 * 查询零件号
	 * @param model
	 * @return
	 * Lxh
	 */
	public List<ShelfManager> queryPartNo(ShelfManager model);

	/**
	 * 查询供应商编号
	 * @param model
	 * @return
	 * Lxh
	 */
	public List<ShelfManager> querySupplierNoByPartNo(ShelfManager model);

	/**
	 * 查询供应商编号
	 * @param model
	 * @return
	 * Lxh
	 */
	public Boolean checkPartNoAndSupplierNoIsMaintain(ShelfManager m);

	/**
	 * 删除导入临时数据
	 * @return
	 * Lxh
	 */
	public int removeShelvesManagerTemp();

}
