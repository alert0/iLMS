package com.hanthink.jisi.manager;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.jisi.model.JisiPartModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;
/**
 * 
 * ClassName: JisiPartManager 
 * @Description: 厂内同步零件组维护
 * @author yokoboy
 * @date 2018年11月9日
 */
public interface JisiPartManager extends Manager<String, JisiPartModel>{

	/**
	 * 
	 * @Description: 零件信息维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:47:40
	 */
	PageList<JisiPartModel> queryJisiPartForPage(JisiPartModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 判断零件组代码和零件号是否已经存在
	 * @param @param partGroupNo
	 * @param @param partNo
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:51:49
	 */
	boolean isExistByCode(String partGroupId, String partNo);

	/**
	 * 
	 * @Description: 零件信息新增界面，零件组下拉框
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午11:11:20
	 */
	List<JisiPartModel> getJisiPartGroupIdUnload(JisiPartModel model);

	/**
	 * 
	 * @Description: 修改零件信息维护
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午11:19:06
	 */
	void updateAndLog(JisiPartModel model, String ipAddr);

	/**
	 * 
	 * @Description: 删除零件信息维护
	 * @param @param idArr
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午11:29:09
	 */
	void removeAndLogByIds(String[] idArr, String ipAddr);

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午2:04:13
	 */
	List<JisiPartModel> queryJisiPartByKey(JisiPartModel model);

	/**
	 * 
	 * @Description: 导入之前从临时表删除上次导入的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:04:41
	 */
	void deleteJisiPartImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @param planCode 
	 * @Description: 文件导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:11:03
	 */
	Map<String, Object> importJisiPartModel(MultipartFile file, String uuid, String ipAddr, IUser user, String planCode) throws Exception;

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:39:36
	 */
	PageList<JisiPartModel> queryImportInformationForPage(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 将临时表数据写入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:02:27
	 */
	void insertTempDataToPartTable(Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Description: 导出Excel在导入时所有数据
	 * @param @param uuid
	 * @param @return   
	 * @return List<PupProPlanModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:45:41
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
	 * @date 2018年11月11日 下午9:41:52
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
