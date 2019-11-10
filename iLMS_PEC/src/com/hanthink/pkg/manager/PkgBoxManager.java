package com.hanthink.pkg.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.pkg.model.PkgBoxModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
* <p>Title: PkgBoxManager.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/

public interface PkgBoxManager extends Manager<String, PkgBoxModel>{

	/**
	 * 
	* @Title: queryPkgBoxForPage 
	* @Description: 分页查询出包装管理箱种信息
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<DpmItemModel> 
	* @throws
	 */
	public PageList<PkgBoxModel> queryPkgBoxForPage(PkgBoxModel model, DefaultPage p);

	/***
	 * 
	* @Title: getByCode 
	* @Description: 新增箱种信息是判断箱code是否重复 
	* @param @param boxCode
	* @param @return    
	* @return PkgBoxModel 
	* @throws
	 */
	public PkgBoxModel getByCode(String boxCode);

	/**
	 * 
	* @Title: updateAndLog 
	* @Description: 修改箱种管理信息并记录日志 
	* @param @param pkgBoxModel
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	public void updateAndLog(PkgBoxModel pkgBoxModel, String ipAddr);

	/**
	 * 
	* @Title: removeAndLogByIds 
	* @Description: 删除箱种管理信息并记录日志 
	* @param @param aryIds
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	public void removeAndLogByIds(String[] aryIds, String ipAddr);

	/**
	 * 加载可使用的箱CODE信息
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午12:40:36
	 */
	public List<PkgBoxModel> loadBoxCodeData(PkgBoxModel pkgBox);

	/**
	 * 
	 * @Description: 获取需要维护的箱种下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 下午5:39:02
	 */
	public List<PkgBoxModel> loadBoxTypeData(Map<String, Object> map);

	/**
	 * 
	 * @Description: 箱种信息导出
	 * @param @param model
	 * @param @return   
	 * @return List<PkgBoxModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月9日 上午9:53:11
	 */
	public List<PkgBoxModel> queryPkgBoxByKey(PkgBoxModel model);

}
