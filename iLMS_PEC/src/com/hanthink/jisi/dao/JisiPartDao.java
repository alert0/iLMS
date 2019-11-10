package com.hanthink.jisi.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.jisi.model.JisiPartModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface JisiPartDao extends Dao<String, JisiPartModel>{

	/**
	 * 
	 * @Description: 零件信息维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:48:30
	 */
	PageList<JisiPartModel> queryJisiPartForPage(JisiPartModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 判断零件组代码和零件号是否已经存在
	 * @param @param partGroupNo
	 * @param @param partNo
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:55:30
	 */
	List<JisiPartModel> getJisiPartListByCode(String partGroupId, String partNo);

	/**
	 * 
	 * @Description: 获取零件信息新增界面，零件组下拉框
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午11:12:21
	 */
	List<JisiPartModel> getJisiPartGroupIdUnload(JisiPartModel model);

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午2:05:10
	 */
	List<JisiPartModel> queryJisiPartByKey(JisiPartModel model);

	/**
	 * 
	 * @Description: 导入之前删除上次导入到临时表的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:05:55
	 */
	void deleteJisiPartImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: 数据写入临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:55:49
	 */
	void insertJisiPartIntoTempData(List<JisiPartModel> importList);

	/**
	 * 
	 * @Description: 调用存储检验数据准确性
	 * @param @param checkMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午3:52:39
	 */
	void checkJisiPartImportDataInformation(Map<String, String> checkMap);

	/**
	 * 
	 * @Description: 检查是否可以批量导入
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:33:23
	 */
	String queryJisiPartImportFlag(String uuid);

	/**
	 * 
	 * @Description: 分页查询导入到临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:41:12
	 */
	PageList<JisiPartModel> queryImportInformationForPage(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 查询正确数据条数
	 * @param @param paramMap
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:27:30
	 */
	Integer getCountForImport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 删除之前数据表数据
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:30:48
	 */
	void deleteRegulaData(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 临时表数据写入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:33:34
	 */
	void insertTempDataToRegula(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 更新临时表中导入状态
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:40:46
	 */
	void updateImportStatus(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 导出Excel 在导入时候的所有数据
	 * @param @param uuid
	 * @param @return   
	 * @return List<PupProPlanModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:49:49
	 */
	List<JisiPartModel> queryImportInformationForExport(String uuid);

	/**
	 * 
	 * @Description: 根据工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午9:42:38
	 */
	String getPlanCode(JisiPartModel model);

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月27日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * @Description: 判断零件号是否存在  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisiPartModel>   
	 * @author: dtp
	 * @date: 2019年3月28日
	 */
	List<JisiPartModel> queryPartNoIsExists(JisiPartModel model);

}
