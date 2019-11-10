package com.hanthink.dpm.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.model.DpmAreaModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月11日 下午4:02:43
 * </pre>
 * @author luoxq
 */
public interface DpmAreaManager extends Manager<String, DpmAreaModel>{
	
	/**
	 * 更新数据并记录日志
	 * @param dpmAreaModel
	 * @param opeIp
	 * @author luoxq	
	 * @DATE	2018年9月10日  
	 */
	void updateAndLog(DpmAreaModel dpmAreaModel, String opeIp);
	
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
	 * @Title: getByCode 
	 * @Description:  
	 * @param @param areaCode
	 * @param @return    
	 * @return DpmAreaModel     
	 * @time   2018年9月12日 上午11:58:58
	 * @throws
	 */
	public DpmAreaModel getByCode(String areaCode);

	/**
	 * 
	 * @Title: 获取查询界面，发现区域代码下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List     
	 * @time   2018年9月12日 上午11:59:09
	 * @throws
	 */
	public List getUnloadCode();

	/**
	 * 
	 * @Title: queryDpmAreaForPage 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmAreaModel>     
	 * @time   2018年9月12日 上午11:59:13
	 * @throws
	 */
	public PageList<DpmAreaModel> queryDpmAreaForPage(DpmAreaModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 通过仓库代码带出仓库名称 
	 * @Description:  
	 * @param @param wareCode
	 * @param @return    
	 * @return DpmAreaModel     
	 * @time   2018年9月12日 下午3:48:56
	 * @throws
	 */
	public DpmAreaModel getWareNameByCode(String wareCode);

	/**
	 * 
	 * @Title: 新增时，获取仓库代码下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月12日 下午5:08:02
	 * @throws
	 */
	public List<DictVO> getUnloadWareCode();

	/**
	 * 
	 * @Title: 获取新增界面车间下拉框值 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月12日 下午6:03:54
	 * @throws
	 */
	public List<DictVO> getUnloadWorkcenter(String factoryCode);

}
