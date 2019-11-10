package com.hanthink.business.dpm.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.dpm.model.DpmAreaModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月17日 下午6:36:35
 * </pre>
 * @author luoxq
 */
public interface DpmInsManager extends Manager<String, DpmInsModel>{

	/**
	 * 
	 * @Title: 分页查询不良品申请列表数据
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmAreaModel>     
	 * @time   2018年9月18日 上午11:18:35
	 * @throws
	 */
	public PageList<DpmInsModel> queryDpmInsForPage(DpmInsModel model, DefaultPage p);

	/**
	 * @return 
	 * 
	 * @Title: 修改数据时将修改信息写入日志 
	 * @Description:  
	 * @param @param dpmInsModel
	 * @param @param ipAddr    
	 * @return void     
	 * @time   2018年9月18日 下午2:30:16
	 * @throws
	 */
	public void updateAndLog(DpmInsModel dpmInsModel, String ipAddr);

	/**
	 * 
	 * @Title:从系统参数表中获取最大流水号
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @time   2018年9月18日 下午3:29:19
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
     * @time   2018年9月18日 下午5:56:11
     * @throws
     */
	public DpmInsModel getMsgByPartNo(Map<String, Object> map);

	/**
	 * 
	 * @Title: 获取新增界面处理结果下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:17:47
	 * @throws
	 */
	public List<DictVO> getUnloadDealResult();

	/**
	 * 
	 * @Title: 获取新增界面不良品项目下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:42:06
	 * @throws
	 */
	public List<DpmItemModel> getUnloadDpmCode();

	/**
	 * 
	 * @Title: 修改系统参数流水号为最大值 
	 * @Description:  
	 * @param @param seriaNum    
	 * @return void     
	 * @time   2018年9月19日 下午2:24:47
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
	 * @time   2018年9月19日 下午3:16:27
	 * @throws
	 */
	public List<DpmInsModel> getUnloadRespDep(Map<String, Object> map);

	/**
	 * @param map 
	 * 
	 * @Title: 打开新增界面时显示默认信息 
	 * @Description:  
	 * @param @param userId
	 * @param @return    
	 * @return DpmInsModel     
	 * @time   2018年9月19日 下午3:37:19
	 * @throws
	 */
	public DpmInsModel getDefaultMsg(Map<String, Object> map);

	/**
	 * @return 
	 * 
	 * @Title: 删除并记录日志
	 * @Description:  
	 * @param @param aryIds
	 * @param @param ipAddr    
	 * @return ResultMessage     
	 * @time   2018年9月20日 上午10:04:21
	 * @throws
	 */
	public void removeAndLogByIds(String[] aryIds, String ipAddr);

	/**
	 * @param checker 
	 * 
	 * @Title: 修改不良品申请信息状态 
	 * @Description:  
	 * @param @param aryIds    
	 * @return void     
	 * @time   2018年9月20日 下午2:30:16
	 * @throws
	 */
	public ResultMessage updateInsStatus(String[] aryIds, String submit, String checker);

	/**
	 * 
	 * @Title: 导出数据指定集合 
	 * @Description:  
	 * @param @param model
	 * @param @return    
	 * @return List<DpmInsModel>     
	 * @time   2018年9月21日 上午9:55:49
	 * @throws
	 */
	public List<DpmInsModel> queryDpmInsByKey(DpmInsModel model);

	/**
	 * 
	* @Title: queryDpmInsDetailList 
	* @Description: 参数为list查询出不良品明细，用于打印 
	* @param @param list
	* @param @return    
	* @return List<DpmInsModel> 
	* @throws 
	* @author luoxq
	* @date   2018年9月27日 下午1:51:57
	 */
	public List<DpmInsModel> queryDpmInsDetailList(DpmInsModel model_q);

	/**
	 * 
	* @Title: toExcepOrder 
	* @Description: 手工生成例外订单 
	* @param @param applyNos
	* @param @param account
	* @param @param curFactoryCode
	* @param @return    
	* @return ResultMessage 
	* @throws 
	* @author luoxq
	* @date   2018年9月28日 下午12:21:37
	 */
	public ResultMessage toExcepOrder(String applyNos, String account, String curFactoryCode, String ipAddr);

	/**
	 * 
	 * @param p 
	 * @Description: 不良品新增界面，查询零件号弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月30日 上午11:21:09
	 */
	public List<DpmInsModel> getPartNo(DpmInsModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 责任反馈修改状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:30:05
	 */
	public void repFeedback(DpmInsModel model);

	/**
	 * 
	 * @Description: 责任反馈插入责任反馈表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:30:33
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
	 * @date 2018年10月31日 下午6:54:30
	 */
	public List<DpmInsModel> feedbaceWriteback(String applyNo);

	/**
	 * 
	 * @Description: 责任反馈界面下拉框
	 * @param @param account
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午7:12:22
	 */
	public List<DpmInsModel> getUnloadRespDepAll(String account);

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param list   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 上午10:05:56
	 */
	public void updatePrintStatus(String[] applyNoArr);

	/**
	 * 
	 * @Description: 校验车型是否存在
	 * @param @param modelCode
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月13日 上午10:55:10
	 */
	public Boolean validateCarType(DpmInsModel dpmInsModel);

	/**
	 * 
	 * @Description: 判断当前登录用户是否是审核人
	 * @param @param model
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月20日 下午5:05:55
	 */
	public boolean isChecker(DpmInsModel model);

	/**
	 * 
	 * @Description: 不良品审核
	 * @param @param applyNos
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月14日 下午12:27:41
	 */
	public void submitCheck(String[] applyNos, DpmInsModel model);

	/**
	 * 
	 * @Description: 查询勾选中需要导出的数据
	 * @param @param applyNoArr
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月16日 下午12:09:08
	 */
	public List<DpmInsModel> getListByApplyArr(String[] applyNoArr);

	/**
	 * 
	 * @Description: 判断是否是总成件
	 * @param @param model
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月29日 上午11:45:03
	 */
	public boolean isZCpart(DpmInsModel model);

	/**
	 * 
	 * @Description: 查询出总成件的子零件号
	 * @param @param partNo
	 * @param @return   
	 * @return List<String>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月29日 上午11:45:14
	 */
	public List<String> getPartListByPartNo(String partNo);

	/**
	 * 
	 * @Description: TODO
	 * @param @param curFactoryCode
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 上午10:26:00
	 */
	public String getDpmFactory(String curFactoryCode);

}
