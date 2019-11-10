package com.hanthink.business.dpm.dao;

import java.util.Date;
import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月17日 下午6:37:25
 * </pre>
 * @author luoxq
 */
public interface DpmInsDao extends Dao<String, DpmInsModel>{

	List<DictVO> getUnloadDpmCode = null;

	/**
	 * 
	 * @Title: 分页查询不良品申请列表数据 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmInsModel>     
	 * @time   2018年9月18日 上午11:21:23
	 * @throws
	 */
	public PageList<DpmInsModel> queryDpmInsForPage(DpmInsModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 从系统参数中获取最大流水号 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @time   2018年9月18日 下午3:30:24
	 * @throws
	 */
	public Integer getSerialNum();

	/**
	 * 
	 * @Title: 新增界面输入零件号，带出相关信息 
	 * @Description:  
	 * @param @param partNo
	 * @param @return    
	 * @return DpmInsModel     
	 * @time   2018年9月18日 下午5:57:39
	 * @throws
	 */
	public DpmInsModel getMsgByPartNo(String partNo);

	/**
	 * 
	 * @Title: 获取新增界面处理结果下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:18:36
	 * @throws
	 */
	public List<DictVO> getUnloadDealResult();

	/**
	 * 
	 * @Title: 获取新增界面不良品项目下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:43:23
	 * @throws
	 */
	public List<DictVO> getUnloadDpmCode();

	/**
	 * 
	 * @Title: 修改系统参数流水号为最大值 
	 * @Description:  
	 * @param @param seriaNum    
	 * @return void     
	 * @time   2018年9月19日 下午2:25:44
	 * @throws
	 */
	public void updateSerialNum(Integer seriaNum);

	/**
	 * 
	 * @Title: 获取新增界面责任组下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 下午3:17:39
	 * @throws
	 */
	public List<DictVO> getUnloadRespDep();

	/**
	 * 
	 * @Title: 打开新增界面显示默认信息 
	 * @Description:  
	 * @param @param userId
	 * @param @return    
	 * @return DpmInsModel     
	 * @time   2018年9月19日 下午3:38:06
	 * @throws
	 */
	public DpmInsModel getDefaultMsg(String userId);

	/**
	 * @param checker 
	 * @param checker 
	 * 
	 * @Title: 修改不良品信息状态
	 * @Description:  
	 * @param @param submit    
	 * @return void     
	 * @time   2018年9月20日 下午3:22:42
	 * @throws
	 */
	public void updateInsStatus(String applyNo,String submit, String checker);

	/**
	 * 
	 * @Title: 查询指定集合导出 
	 * @Description:  
	 * @param @param model
	 * @param @return    
	 * @return List<DpmInsModel>     
	 * @time   2018年9月21日 上午9:58:06
	 * @throws
	 */
	public List<DpmInsModel> queryDpmInsByKey(DpmInsModel model);

	/**
	 * 
	* @Title: queryDpmInsDetailList 
	* @Description: 查询不良品信息明细，用于打印 
	* @param @param list
	* @param @return    
	* @return List<DpmInsModel> 
	* @throws 
	* @author luoxq
	* @date   2018年9月27日 下午1:53:51
	 */
	public List<DpmInsModel> queryDpmInsDetailList(DpmInsModel model_q);

	/**
	 * 
	* @Title: getExcepOrder 
	* @Description: 调用存储过程获取例外订单号 
	* @param @param applyNo
	* @param @param account
	* @param @param curFactoryCode
	* @param @return    
	* @return String 
	* @throws 
	* @author luoxq
	* @date   2018年9月28日 下午2:35:31
	 */
	public String getExcepOrderNo(java.sql.Date arrayDate, String orderType, String curFactoryCode);

	/**
	 * 
	* @Title: insertExcepOrder 
	* @Description: 将要生成例外订单的不良品数据插入例外订单表中 
	* @param @param dpmInsModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年9月28日 下午2:35:58
	 */
	public void insertExcepOrder(DpmInsModel dpmInsModel);

	/**
	 * 
	* @Title: insertExcepOrderDetail 
	* @Description: 将要生成例外订单的不良品数据插入例外订单明细表中 
	* @param @param dpmInsModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年9月28日 下午2:37:08
	 */
	public void insertExcepOrderDetail(DpmInsModel dpmInsModel);


}
