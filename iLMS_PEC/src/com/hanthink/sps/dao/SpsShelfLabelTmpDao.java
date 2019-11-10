package com.hanthink.sps.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sps.model.SpsShelfLabelTmpModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsShelfLabelTmpDao
 * @Description: 货架标签打印
 * @author dtp
 * @date 2018年10月31日
 */
public interface SpsShelfLabelTmpDao extends Dao<String, SpsShelfLabelTmpModel>{

	/**
	 * @Description: 保存数据到MM_SPS_SHELF_LABLE_IMP表  
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	void insertSpsShelfLabelTmp(List<SpsShelfLabelTmpModel> importList);

	/**
	 * @Description: 调用存储过程校验数据
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * @Description: 查询导入数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	PageList<SpsShelfLabelTmpModel> querySpsShelfLabelPage(SpsShelfLabelTmpModel model, DefaultPage page);

	/**
	 * @Description: 获取货架标签打印信息    
	 * @param: @param idArr
	 * @param: @return    
	 * @return: List<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	List<SpsShelfLabelTmpModel> querySpsShelfLabelList(String[] idArr);

	/**
	 * @Description: 根据uuid删除导入临时表数据
	 * @param: @param uuid
	 * @param: @return    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 货架标签打印导入信息导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	List<SpsShelfLabelTmpModel> querySpsShelfLabelList(SpsShelfLabelTmpModel model);

}
