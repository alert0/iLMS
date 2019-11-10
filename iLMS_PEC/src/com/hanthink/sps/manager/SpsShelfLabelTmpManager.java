package com.hanthink.sps.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sps.model.SpsShelfLabelTmpModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SpsShelfLabelTmpManager
 * @Description: 货架标签打印
 * @author dtp
 * @date 2018年10月31日
 */
public interface SpsShelfLabelTmpManager extends Manager<String, SpsShelfLabelTmpModel>{

	/**
	 * @Description: 获取导入excel数据   
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	Map<String, Object> importSpsShelfLabelTmpModel(MultipartFile file, String uuid, String ipAddr);

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
