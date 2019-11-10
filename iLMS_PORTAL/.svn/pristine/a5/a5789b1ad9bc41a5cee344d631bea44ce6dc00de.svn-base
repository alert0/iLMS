package com.hanthink.dpm.dao;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月13日 下午12:07:55
 * </pre>
 * @author luoxq
 */
public interface DpmDepartmentDao extends Dao<String, DpmDepartmentModel>{

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
	 * @Title: 分页查询  获取部门审核人
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
	 * @Title: 判断输入的部门审核人在用户表中是否存在 
	 * @Description:  
	 * @param @param depChecker
	 * @param @return    
	 * @return List<DpmDepartmentModel>     
	 * @time   2018年9月14日 上午11:35:39
	 * @throws
	 */
	public List<DpmDepartmentModel> isExistChecker(DpmDepartmentModel departmentModel);

	/**
	 * 
	 * @Title: 获取责任组维护新增界面，责任组下拉框值 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月14日 下午3:19:27
	 * @throws
	 */
	public List<DictVO> getDepUnloadCode();
}
