package com.hanthink.jisi.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

public interface JisiPartGroupManager  extends Manager<String, JisiPartGroupModel>{

	/**
	 * 
	 * @Description: 厂内同步零件组维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月5日 下午9:24:07
	 */
	PageList<JisiPartGroupModel> queryJisiPartGroupForPage(JisiPartGroupModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 厂内同步零件组维护，批量删除
	 * @param @param idArr
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午3:20:24
	 */
	void removeAndLogByIds(String[] idArr, String ipAddr);

	/**
	 * 
	 * @Description: 根据零件组代码判断是否已经存在
	 * @param @param partGroup
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:15:25
	 */
	boolean isExistByCode(String partGroup);

	/**
	 * 
	 * @Description: 修改厂内同步零件组维护
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:23:08
	 */
	void updateAndLog(JisiPartGroupModel model, String ipAddr);

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:53:23
	 */
	List<JisiPartGroupModel> queryJisiPartGroupByKey(JisiPartGroupModel model);

	/**
	 * 
	 * @Description: 导入之前从临时表删除上次导入的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:04:41
	 */
	void deleteJisiPartGroupImportTempDataByUUID(String uuid);

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
	Map<String, Object> importJisiPartGroupModel(MultipartFile file, String uuid, String ipAddr, IUser user, String planCode) throws Exception;

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
	PageList<JisiPartGroupModel> queryImportInformationForPage(Map<String, String> paramMap, Page page);

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
	void insertTempDataToPartGroupTable(Map<String, String> paramMap) throws Exception;

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
	List<JisiPartGroupModel> queryImportInformationForExport(String uuid);

	/**
	 * 
	 * @Description: 通过工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午3:41:58
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
