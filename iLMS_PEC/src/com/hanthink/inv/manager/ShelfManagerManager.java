package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.PartMainTenanance;
import com.hanthink.inv.model.ShelfManager;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface ShelfManagerManager extends Manager<String, ShelfManager>{

	/**
	 * 货架数据分页查询
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	public PageList<ShelfManager> queryShelfManagerForPage(
			ShelfManager model, DefaultPage p);

	/**
	 * 货架数据查询导出
	 * @param model
	 * @return
	 * 李兴辉
	 */
	public List<ShelfManager> queryexportList(ShelfManager model)throws Exception;

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
	boolean insertShelvesManager(ShelfManager model);

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
	public PageList<ShelfManager> queryShelfManagerForPageTemp(ShelfManager model, DefaultPage p);

	/**
	 * excel导入临时数据
	 * @param file
	 * @return
	 * Lxh
	 */
	public Map<String, Object> importShelfManagerTemp(MultipartFile file);

	/**
	 * 确认提交
	 * 
	 * Lxh
	 */
	public Map<String,Object> ImportData();

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
	 * 导入临时数据删除
	 * @return
	 * Lxh
	 */
	public int removeShelvesManagerTemp();

	/**
	 * 新增时验证供应商和零件对应关系是否存在
	 * @param model
	 * @return
	 * Lxh
	 */
	boolean checkPartNoAndSupplierNoIsMaintain(ShelfManager model);
}
