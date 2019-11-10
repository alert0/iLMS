package com.hanthink.sps.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hanthink.sps.model.SpsConfigItemModel;
import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsMouldDao
 * @Description: SPS票据模板管理
 * @author dtp
 * @date 2018年11月21日
 */
public interface SpsMouldDao extends Dao<String, SpsMouldModel>{

	/**
	 * @Description: 模板列表查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	PageList<SpsMouldModel> querySpsMouldPage(SpsMouldModel model, DefaultPage page);

	/**
	 * @Description: 修改模板列表
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	void updateSpsMould(SpsMouldModel model);

	/**
	 * @Description: 配置列表查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	PageList<SpsMouldConfigModel> querySpsMouldConfigPage(SpsMouldConfigModel model, DefaultPage page);

	/**
	 * @Description: 查询SPS配置信息 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	List<SpsMouldConfigModel> querySpsMouldConfigList(SpsMouldConfigModel model);

	/**
	 * @Description: 修改配置信息 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	void updateSpsMouldConfig(SpsMouldConfigModel model);

	/**
	 * @Description: SPS票据模板管理配置列表导入临时表 
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	void insertSpsMouldConfigTempData(List<SpsMouldConfigModel> importList);

	/**
	 * @Description: 调用存储过程等检查数据准确性  
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * @Description: SPS配置列表临时表导入到正式表  
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * @Description: 查询临时表数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	PageList<SpsMouldConfigModel> queryImportTempPage(SpsMouldConfigModel model, DefaultPage page);

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	List<SpsMouldConfigModel> queryImportTempList(SpsMouldConfigModel model);

	/**
	 * @Description: 更新临时表导入状态 
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

	/**
	 * @Description: 根据UUID删除临时表数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 上传图片后保存fileId
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月22日
	 */
	void updateSpsMouldConfigFileId(SpsMouldConfigModel model);

	/**
	 * @Description: 获取SPS配置项与ID转换
	 * @param: @return    
	 * @return: List<SpsConfigModel>   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	List<SpsConfigModel> querySpsConfigList(SpsConfigModel model);

	/**
	 * @Description: 查询校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * @Description: 新增票据模板  
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月12日
	 */
	void insertSpsMould(SpsMouldModel model);

	/**
	 * @Description: 配置列表删除   
	 * @param: @param spsMouldConfigModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月21日
	 */
	void deleteSpsMouldConfig(SpsMouldConfigModel spsMouldConfigModel);

	/**
	 * @Description:  新增票据模板配置列表 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月28日
	 */
	void insertSpsMouldConfig(SpsMouldConfigModel model);

	/**
	 * @Description:  查询业务主键是否冲突 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	List<SpsMouldConfigModel> queryIsExists(SpsMouldConfigModel model);

	
}
