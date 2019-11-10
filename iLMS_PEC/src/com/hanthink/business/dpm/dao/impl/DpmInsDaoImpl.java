package com.hanthink.business.dpm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.dao.DpmInsDao;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月17日 下午6:37:57
 * </pre>
 * @author luoxq
 */
@Repository
public class DpmInsDaoImpl extends MyBatisDaoImpl<String, DpmInsModel> implements DpmInsDao{

	@Override
	public String getNamespace() {
		
		return DpmInsModel.class.getName();
	}

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
	@Override
	public PageList<DpmInsModel> queryDpmInsForPage(DpmInsModel model, DefaultPage p) {
		
		return (PageList<DpmInsModel>) this.getByKey("queryDpmInsForPage", model, p);
	}

	/**
	 * 
	 * @Title: 从系统参数中获取最大流水号 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @time   2018年9月18日 下午3:30:24
	 * @throws
	 */
	@Override
	public Integer getSerialNum() {
		
		return (Integer) this.getOne("getSerialNum", null);
	}

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
	@Override
	public DpmInsModel getMsgByPartNo(Map<String, Object> map) {
		
		return this.getSqlSessionTemplate().selectOne("getMsgByPartNo", map);
	}

	/**
	 * 
	 * @Title: 获取新增界面处理结果下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:18:36
	 * @throws
	 */
	@Override
	public List<DictVO> getUnloadDealResult() {
		Map<String,Object> map=new HashMap();
		return (List<DictVO>) this.getList("getUnloadDealResult", map);
	}

	/**
	 * 
	 * @Title: 获取新增界面不良品项目下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:43:23
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DpmItemModel> getUnloadDpmCode() {
		IUser user = ContextUtil.getCurrentUser();
		Map<String,Object> map=new HashMap<>();
		map.put("factoryCode", user.getCurFactoryCode());
		return (List<DpmItemModel>) this.getList("getUnloadDpmCode", map);
	}

	/**
	 * 
	 * @Title: 修改系统参数流水号为最大值 
	 * @Description:  
	 * @param @param seriaNum    
	 * @return void     
	 * @time   2018年9月19日 下午2:25:44
	 * @throws
	 */
	@Override
	public void updateSerialNum(Integer seriaNum) {
		this.updateByKey("updateSerialNum", seriaNum);
	}

	/**
	 * 
	 * @Title: 获取新增界面责任组下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 下午3:17:39
	 * @throws
	 */
	@Override
	public List<DpmInsModel> getUnloadRespDep(Map<String, Object> map) {
		return (List<DpmInsModel>) this.getList("getUnloadRespDep", map);
	}

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
	@Override
	public DpmInsModel getDefaultMsg(Map<String, Object> map) {
		
		return this.getUnique("getDefaultMsg", map);
	}

	/**
	 * 
	 * @Title: 修改不良品信息状态
	 * @Description:  
	 * @param @param submit    
	 * @return void     
	 * @time   2018年9月20日 下午3:22:42
	 * @throws
	 */
	@Override
	public void updateInsStatus(String applyNo,String submit,String checker) {
		Map<String, Object> map = new HashMap<>();
		if (Constant.TURN_MATERIAL.equals(submit)) {
			map.put("dpmType", Constant.SUBIMT_TYPE_STATUS);
		}else if (Constant.EXCEP_ORDER.equals(submit)) {
			map.put("excepStatus", Constant.SUBIMT_TYPE_STATUS);
		}else if(Constant.CHECK_TYPE.equals(submit)){
			map.put("insStatus", submit);
			map.put("checker", checker);
		}else {
			map.put("insStatus", submit);
		}
		
	    map.put("applyNo", applyNo);
		this.updateByKey("updateInsStatus", map);
	}

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
	@Override
	public List<DpmInsModel> queryDpmInsByKey(DpmInsModel model) {
		
		return this.getByKey("queryDpmInsDownload", model);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<DpmInsModel> queryDpmInsDetailList(DpmInsModel model_q) {
		
		return  (List<DpmInsModel>) this.getList("queryDpmInsDetailList", model_q);
	}

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
	@Override
	public String getExcepOrderNo(java.sql.Date arrayDate, String orderType, String curFactoryCode) {
		Map<String, Object> map=new HashMap<>();
		map.put("arrayDate", arrayDate);
		map.put("orderType", orderType);
		map.put("curFactoryCode", curFactoryCode);
		map.put("excepNo", "");
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".getExcepOrderNo", map);
		return (String) map.get("excepNo");
	}

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
	@Override
	public void insertExcepOrder(DpmInsModel dpmInsModel) {
		this.insertByKey("insertExcepOrder", dpmInsModel);
	}

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
	@Override
	public void insertExcepOrderDetail(DpmInsModel dpmInsModel) {
		this.insertByKey("insertExcepOrderDetail", dpmInsModel);
	}

	/**
	 * 
	 * @Description: 不良品新增界面，查询零件号弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月30日 上午11:22:15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<DpmInsModel> getPartNo(DpmInsModel model, DefaultPage p) {
		
		return (PageList<DpmInsModel>) this.getByKey("getPartNo", model,p);
	}

	/**
	 * 
	 * @Description: 责任反馈，更新
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:32:09
	 */
	@Override
	public void repFeedback(DpmInsModel model) {
		this.updateByKey("repFeedback", model);
	}

	/**
	 * 
	 * @Description: 责任反馈插入
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:32:25
	 */
	@Override
	public void insertFeedback(DpmInsModel model) {
		this.insertByKey("insertFeedback", model);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<DpmInsModel> feedbaceWriteback(String applyNo) {
		
		return (List<DpmInsModel>) this.getList("feedbaceWriteback", applyNo);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<DpmInsModel> getUnloadRespDepAll(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		IUser user = ContextUtil.getCurrentUser();
		map.put("factoryCode", user.getCurFactoryCode());
		return this.getList("getUnloadRespDepAll", map);
	}

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 上午10:07:59
	 */
	@Override
	public void updatePrintStatus(DpmInsModel dpmInsModel) {
		this.updateByKey("updatePrintStatus", dpmInsModel);
	}

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
	@Override
	public Integer validateCarType(DpmInsModel model) {
		Integer count = this.sqlSessionTemplate.selectOne("validateCarType", model);
		return count;
	}

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
	@Override
	public Integer isChecker(DpmInsModel model) {
		
		return this.sqlSessionTemplate.selectOne("isChecker", model);
	}

	/**
	 * 
	 * @Description: 审核通过数据写到erp接口表中
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午6:27:16
	 */
	@Override
	public void insertTranLms(Map<String, Object> map) {
		this.insertByKey("insertTranLms", map);
	}

	/**
	 * 
	 * @Description: 审核通过数据写到erp接口表明细
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月23日 上午8:09:51
	 */
	@Override
	public void insertTranLmsDetial(Map<String, Object> map) {
		this.insertByKey("insertTranLmsDetial", map);
	}

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
	@Override
	public void submitCheck(String[] applyNos, DpmInsModel model) {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("applyNos", applyNos);
		map.put("model", model);
		this.updateByKey("submitCheck", map);
	}

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
	@Override
	public List<DpmInsModel> getListByApplyArr(String[] applyNoArr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applyNoArr", applyNoArr);
		return this.getByKey("getListByApplyArr", map);
	}

	/**
	 * 
	 * @Description: 插入共享平台订单表中
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月22日 上午11:58:08
	 */
	@Override
	public void insetExcepSwOrder(DpmInsModel dpmInsModel) {
		this.insertByKey("insetExcepSwOrder", dpmInsModel);
	}

	/**
	 * 
	 * @Description: 插入共享平台订单明细表中
	 * @param @param dpmInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月22日 上午11:58:31
	 */
	@Override
	public void insertExcepSwOrderDetail(DpmInsModel dpmInsModel) {
		
		this.insertByKey("insertExcepSwOrderDetail", dpmInsModel);
	}

	@Override
	public Integer getCountByPartNo(DpmInsModel model) {
		
		return this.sqlSessionTemplate.selectOne("getCountByPartNo", model);
	}

	@Override
	public List<String> getPartListByPartNo(String partNo) {
		
		return this.sqlSessionTemplate.selectList("getZCPartNoByPartNo", partNo);
	}

	@Override
	public String getDpmFactory(String curFactoryCode) {
		
		return this.sqlSessionTemplate.selectOne("getDpmFactory", curFactoryCode);
	}

}
