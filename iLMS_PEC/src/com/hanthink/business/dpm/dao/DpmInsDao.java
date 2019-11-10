package com.hanthink.business.dpm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.dpm.model.DpmItemModel;
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
	public DpmInsModel getMsgByPartNo(Map<String, Object> map);

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
	public List<DpmItemModel> getUnloadDpmCode();

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
	 * @param account 
	 * 
	 * @Title: 获取新增界面责任组下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 下午3:17:39
	 * @throws
	 */
	public List<DpmInsModel> getUnloadRespDep(Map<String, Object> map);

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
	public DpmInsModel getDefaultMsg(Map<String, Object> map);

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

	/**
	 * 
	 * @param p 
	 * @Description: 不良品新增界面，查询零件号弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月30日 上午11:22:15
	 */
	public PageList<DpmInsModel> getPartNo(DpmInsModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 责任反馈，更新
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:32:09
	 */
	public void repFeedback(DpmInsModel model);

	/**
	 * 
	 * @Description: 责任反馈插入
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:32:25
	 */
	public void insertFeedback(DpmInsModel model);

	/**
	 * 
	 * @Description: 责任反馈，回写多行文本框
	 * @param @param applyNo
	 * @param @return   
	 * @return DpmInsModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:55:47
	 */
	public List<DpmInsModel> feedbaceWriteback(String applyNo);

	/**
	 * 
	 * @Description: 责任反馈界面，责任组下拉框
	 * @param @param account
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午7:13:14
	 */
	public List<DpmInsModel> getUnloadRespDepAll(String account);

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 上午10:07:59
	 */
	public void updatePrintStatus(DpmInsModel dpmInsModel);

	/**
	 * 
	 * @Description: 校验车型是否存在
	 * @param @param modelCode
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月13日 上午10:56:14
	 */
	public Integer validateCarType(DpmInsModel model);

	/**
	 * 
	 * @Description: 判断当前登录用户是否是审核人
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月20日 下午5:07:14
	 */
	public Integer isChecker(DpmInsModel model);

	/**
	 * 
	 * @Description: 审核通过数据写到erp接口表中
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午6:27:16
	 */
	public void insertTranLms(Map<String, Object> map);

	/**
	 * 
	 * @Description: 审核通过数据写到erp接口表明细
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月23日 上午8:09:51
	 */
	public void insertTranLmsDetial(Map<String, Object> map);

	/**
	 * 
	 * @Description: 不良品审核
	 * @param @param applyNos
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月14日 下午12:32:35
	 */
	public void submitCheck(String[] applyNos, DpmInsModel model);

	/**
	 * 
	 * @Description: 查询导出勾选中的数据
	 * @param @param applyNoArr
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月16日 下午12:10:02
	 */
	public List<DpmInsModel> getListByApplyArr(String[] applyNoArr);

	/**
	 * 
	 * @Description: 插入共享平台订单表中
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月22日 上午11:58:08
	 */
	public void insetExcepSwOrder(DpmInsModel dpmInsModel);

	/**
	 * 
	 * @Description: 插入共享平台订单明细表中
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月22日 上午11:58:31
	 */
	public void insertExcepSwOrderDetail(DpmInsModel dpmInsModel);

	/**
	 * 
	 * @Description: 根据零件号查询是否是总成件
	 * @param @param factoryCode
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月25日 下午4:38:31
	 */
	public Integer getCountByPartNo(DpmInsModel model);

	/**
	 * 
	 * @Description: 查询总成零件号的子零件
	 * @param @param partNo
	 * @param @return   
	 * @return List<String>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月29日 上午11:46:29
	 */
	public List<String> getPartListByPartNo(String partNo);

	/**
	 * 
	 * @Description: 根据当前登录用户工厂获取工厂代码
	 * @param @param curFactoryCode
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 上午10:27:49
	 */
	public String getDpmFactory(String curFactoryCode);

}
