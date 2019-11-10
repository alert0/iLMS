package com.hanthink.sps.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SpsConfigItemManager extends
		Manager<String, SpsConfigItemModel> {
	/**
	 * 配置项代码导入分页查询
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<SpsConfigItemModel> querySpsConfigItemPage(
			SpsConfigItemModel model, DefaultPage page);

	/**
	 * 配置项代码更新
	 * 
	 * @param model
	 * @return
	 */
	int updateConfigItem(SpsConfigItemModel model, String ipAddr);
	/**
	 * 配置项代码批量删除
	 * @param arrayList
	 * @throws Exception 
	 */
	void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList) throws Exception;
	
	/**
	 * 配置项代码导出查询
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigItemList(SpsConfigItemModel model);
	
	/**
	 * 判断配置项代码是否唯一
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> queryConfigCode(SpsConfigItemModel model);
	/**
	 * 配置项代码新增或修改
	 * @param model
	 * @return
	 */
	int insertConfigItem(SpsConfigItemModel model);
	/**
	 * 配置项导入临时表
	 * @param file
	 * @param ipAddr
	 * @return
	 */
	Map<String, Object> importSpsConfigItemTemp(MultipartFile file,String uuid,
			String ipAddr);

	/**
	 * 配置项代码导入临时表数据查询
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model,
			DefaultPage page);

	/**
	 * 查询是否存在未提交的导入临时数据
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigNotImport(SpsConfigItemModel model);
	/**
	 * 配置项代码导入确认提交
	 * @return 提交结果
	 */

	Map<String, Object> spsConfigItemImportData();
	

	/**
	 * 配置项代码临时数据查询
	 * @param model
	 * @return
	 */
	List<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model);
	
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
	 * @date: 2018年12月27日
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * @Description: 通过uuid删除临时表数据
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 批量删除配置项 
	 * @param: @param arrayList
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年12月27日
	 */
	void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList, String ipAddr,String[] idArr) throws Exception;

	/**
	 * @Description: 删除前判断配置项是否维护明细  
	 * @param: @param arrayList
	 * @param: @return    
	 * @return: List<SpsConfigDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月28日
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
