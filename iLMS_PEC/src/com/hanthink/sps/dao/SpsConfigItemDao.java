package com.hanthink.sps.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SpsConfigItemDao extends Dao<String, SpsConfigItemModel>{
	
	/**
	 * 配置项代码数据分页查询
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<SpsConfigItemModel> querySpsConfigItemPage(SpsConfigItemModel model, DefaultPage page);
	/**
	 * 配置项代码更新(失效)
	 * @param model
	 * @return
	 */
	int updateConfigItem(SpsConfigItemModel model);
	/**
	 * 配置项代码批量删除
	 * @param arrayList
	 */
	void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList);
	/**
	 * 配置项代码导出数据查询
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigItemList(SpsConfigItemModel model);
	/**
	 * 查询配置项代码是否存在
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> queryConfigCode(SpsConfigItemModel model);
	/**
	 * 配置项代码新增和修改
	 * @param model
	 * @return
	 */
	int insertConfigItem(SpsConfigItemModel model);
	/**
	 * 配置项代码导入临时数据
	 * @param importList
	 */
	void insertSpsConfigItemTempData(List<SpsConfigItemModel> importList);
	/**
	 * 配置项代码导入数据查询
	 * @param ckParamMap
	 */
	void checkImportData(Map<String, String> ckParamMap);
	/**
	 * 配置项代码导入提交
	 * @param ckParamMap
	 */
	void spsConfigItemImportData(Map<String, String> ckParamMap);
	/**
	 * 配置项代码查询是否存在审核通过但未提交的数据
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigNotImport(SpsConfigItemModel model);
	/**
	 * 查询配置项代码导入临时数据
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model,
			DefaultPage page);
	/**
	 * 配置项代码导入临时数据查询导出
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigTempForExport(
			SpsConfigItemModel model);
	
	/**
	 * 临时表数据删除
	 * @return
	 * Lxh
	 */
	int removeSpsConfigItemTemp();
	
	/**
	 * @Description: SPS配置向导入临时表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月17日
	 */
	void insertImportData(Map<String, String> paramMap);
	
	/**
	 * @Description: SPS配置向导入临时表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月18日
	 */
	void updateImportStatus(Map<String, String> paramMap);
	
	/**
	 * @Description: 通过uuid删除临时表数据
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	void deleteImportTempDataByUUID(String uuid);
	
	/**
	 * @Description:  判断配置项代码是否有配置明细 
	 * @param: @param arrayList
	 * @param: @return    
	 * @return: List<SpsConfigDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	List<SpsConfigDetailModel> querySpsConfigDetailList(ArrayList<SpsConfigItemModel> arrayList);
	
	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	int queryIsExistsCheckResultFalse(String uuid);
	
}
