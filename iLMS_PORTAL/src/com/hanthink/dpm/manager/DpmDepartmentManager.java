package com.hanthink.dpm.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月13日 下午12:01:46
 * </pre>
 * @author luoxq
 */
public interface DpmDepartmentManager extends Manager<String, DpmDepartmentModel>{
	
	/**
	 * 更新数据并记录日志
	 * @param dpmItemModel
	 * @param opeIp
	 * @author luoxq	
	 * @DATE	2018年9月10日  
	 */
	void updateAndLog(DpmDepartmentModel dpmDepartmentModel, String opeIp);
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author luoxq	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr);
	
	/**
	 * 
	 * @Title: 通过责任组编码判断是否重复 
	 * @Description:  
	 * @param @param itemCode
	 * @param @return    
	 * @return DpmItemModel     
	 * @time   2018年9月13日 下午12:21:33
	 * @throws
	 */
	public DpmDepartmentModel getByCode(String depCode);

	/**
	 * 
	 * @Title: 分页查询 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmItemModel>     
	 * @time   2018年9月13日 下午12:21:37
	 * @throws
	 */
	public PageList<DpmDepartmentModel> queryDpmDepartmentForPage(DpmDepartmentModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 分页查询 获取部门审核人
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmItemModel>     
	 * @time   2018年9月13日 下午12:21:37
	 * @throws
	 */
	public PageList<DpmDepartmentModel> getDepChecker(DpmDepartmentModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 判断输入的审核人是否在用户表中存在 
	 * @Description:  
	 * @param @param depChecker
	 * @param @return    
	 * @return List<DpmDepartmentModel>     
	 * @time   2018年9月14日 上午11:34:38
	 * @throws
	 */
	public List<DpmDepartmentModel> isExistChecker(DpmDepartmentModel dpmDepartmentModel);

	/**
	 * 
	 * @Title: 获取责任组维护新增界面，责任组下拉框值 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月14日 下午3:18:08
	 * @throws
	 */
	public List<DictVO> getDepUnloadCode();


}
