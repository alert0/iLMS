package com.hanthink.sps.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SpsMouldManager
 * @Description: SPS票据模板管理
 * @author dtp
 * @date 2018年11月21日
 */
public interface SpsMouldManager  extends Manager<String, SpsMouldModel>{

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
	void updateSpsMould(SpsMouldModel model, String ipAddr);

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
	 * @Description: 配置列表导出excel  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	List<SpsMouldConfigModel> querySpsMouldConfigList(SpsMouldConfigModel model);

	/**
	 * @Description: 修改配置列表信息 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	void updateSpsMouldConfig(SpsMouldConfigModel model, String ipAddr);

	/**
	 * @Description: SPS票据模板配置导入  
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @param mouldId
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	Map<String, Object> importSpsMould(MultipartFile file, String uuid, String ipAddr, String mouldId);

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
	void updateSpsMouldConfigFileId(SpsMouldConfigModel model, String ipAddr);

	/**
	 * @Description: 加载SPS配置项列表下拉框
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsConfigModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	List<SpsConfigModel> querySpsConfigData(SpsConfigModel model);
	
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
	void insertSpsMould(SpsMouldModel model, String ipAddr);

	/**
	 * @Description: 配置列表删除
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月21日
	 */
	void deleteSpsMouldConfig(SpsMouldConfigModel[] models, String ipAddr);

	/**
	 * @Description: 新增票据模板配置列表
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月28日
	 */
	void insertSpsMouldConfig(SpsMouldConfigModel model, String ipAddr);

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
