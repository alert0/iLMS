package com.hanthink.jisi.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface JisiPartGroupDao extends Dao<String, JisiPartGroupModel>{

	/**
	 * 
	 * @Description: 厂内同步零件组维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月5日 下午9:25:04
	 */
	PageList<JisiPartGroupModel> queryJisiPartGroupForPage(JisiPartGroupModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 判断零件组代码是否已经存在
	 * @param @param partGroup
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:17:28
	 */
	List<JisiPartGroupModel> getPartGroupByCode(String partGroup);

	/**
	 * 
	 * @Description: 查询出需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:54:03
	 */
	List<JisiPartGroupModel> queryJisiPartGroupByKey(JisiPartGroupModel model);

	/**
	 * 
	 * @Description: 导入之前删除上次导入到临时表的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:05:55
	 */
	void deleteJisiPartGroupImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @param planCode 
	 * @Description: 数据写入临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:55:49
	 */
	void insertJisiPartGroupIntoTempData(List<JisiPartGroupModel> importList, String planCode);

	/**
	 * 
	 * @Description: 调用存储检验数据准确性
	 * @param @param checkMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午3:52:39
	 */
	void checkJisiPartGroupImportDataInformation(Map<String, String> checkMap);

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
	String queryJisiPartGroupImportFlag(String uuid);

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
	PageList<JisiPartGroupModel> queryImportInformationForPage(Map<String, String> paramMap, Page page);

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
	List<JisiPartGroupModel> queryImportInformationForExport(String uuid);

	/**
	 * 
	 * @Description: 修改导入临时表的打印机字段为打印机id
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午12:03:30
	 */
	void updateJisiPartGroupPrintId(List<JisiPartGroupModel> importList);

	/**
	 * 
	 * @Description: 通过工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午3:42:43
	 */
	String getPlanCode(JisiPartGroupModel model);

	/**
	 * @Description: 查询是否有校验结果不通过数据  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月27日
	 */
	int queryIsExistsCheckResultFalse(String uuid);


}
