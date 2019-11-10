package com.hanthink.dpm.dao;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.model.DpmAreaModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月11日 下午4:05:50
 * </pre>
 * @author luoxq
 */
public interface DpmAreaDao extends Dao<String, DpmAreaModel>{

	/**
	 * 
	 * @Title: 通过发现区域代码查询数据是否重复 
	 * @Description:  
	 * @param @param areaCode
	 * @param @return    
	 * @return DpmAreaModel     
	 * @time   2018年9月12日 下午5:10:27
	 * @throws
	 */
	DpmAreaModel getByCode(String areaCode);

	/**
	 * 
	 * @Title: 获取查询界面发现区域下拉框值 
	 * @Description:  
	 * @param @return    
	 * @return List     
	 * @time   2018年9月12日 下午5:11:28
	 * @throws
	 */
	List getUnloadCode();

	/**
	 * 
	 * @Title: 查询界面列表数据（分页） 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmAreaModel>     
	 * @time   2018年9月12日 下午5:11:56
	 * @throws
	 */
	PageList<DpmAreaModel> queryDpmAreaForPage(DpmAreaModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 新增或修改时，通过仓库代码带出仓库名称 
	 * @Description:  
	 * @param @param wareCode
	 * @param @return    
	 * @return DpmAreaModel     
	 * @time   2018年9月12日 下午5:12:24
	 * @throws
	 */
	DpmAreaModel getWareNameByCode(String wareCode);

	/**
	 * 
	 * @Title: 新增时获取仓库代码下拉框值
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月12日 下午5:13:14
	 * @throws
	 */
	List<DictVO> getUnloadWareCode();

	/**
	 * 
	 * @Title: 新增界面车间下拉框值 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月12日 下午6:06:25
	 * @throws
	 */
	List<DictVO> getUnloadWorkcenter(String factoryCode);

}
