package com.hanthink.pkg.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pkg.model.PkgBoxModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: PkgBoxDao.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/

public interface PkgBoxDao extends Dao<String, PkgBoxModel>{

	/**
	 * 
	* @Title: queryPkgBoxForPage 
	* @Description: 分页查询包装管理箱种信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<DpmItemModel> 
	* @throws
	 */
	public PageList<PkgBoxModel> queryPkgBoxForPage(PkgBoxModel model, DefaultPage p);

	/**
	 * 
	* @Title: getByCode 
	* @Description: 新增时判断箱种代码是否重复 
	* @param @param boxCode
	* @param @return    
	* @return PkgBoxModel 
	* @throws
	 */
	public PkgBoxModel getByCode(String boxCode);

	/**
	 * 加载可使用的箱CODE信息
	 * @param pkgBox
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午12:45:15
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
	 * @date 2019年1月7日 下午5:39:54
	 */
	public List<PkgBoxModel> loadBoxTypeData(Map<String, Object> map);

	/**
	 * 
	 * @Description: TODO
	 * @param @param model
	 * @param @return   
	 * @return List<PkgBoxModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月9日 上午9:55:12
	 */
	public List<PkgBoxModel> queryPkgBoxByKey(PkgBoxModel model);

}
