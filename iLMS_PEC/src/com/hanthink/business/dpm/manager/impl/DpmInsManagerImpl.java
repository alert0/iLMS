package com.hanthink.business.dpm.manager.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;import org.junit.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.business.dpm.dao.DpmInsDao;
import com.hanthink.business.dpm.manager.DpmInsManager;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

import bsh.util.Util;

/**
 * 
 * <pre>
 * Description: 不良品信息申请manager类DpmInsManager
 * Company: HanThink
 * Date: 2018年9月17日 下午6:37:06
 * </pre>
 * @author luoxq
 */
@Service("DpmInsManager")
public class DpmInsManagerImpl extends AbstractManagerImpl<String, DpmInsModel> implements DpmInsManager{

	@Resource
	private DpmInsDao dpmInsdao;
	@Override
	protected Dao<String, DpmInsModel> getDao() {
		
		return dpmInsdao;
	}
	
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
	@Override
	public PageList<DpmInsModel> queryDpmInsForPage(DpmInsModel model, DefaultPage p) {
		
		return dpmInsdao.queryDpmInsForPage(model,p);
	}
	
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
	@Override
	public void updateAndLog(DpmInsModel dpmInsModel, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("APPLY_NO");
		pkColumnVO.setColumnVal(dpmInsModel.getApplyNo());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dpmInsdao.update(dpmInsModel);
	}
	
	/**
	 * 
	 * @Title:从系统参数表中获取最大流水号
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @time   2018年9月18日 下午3:29:19
	 * @throws
	 */
	@Override
	public Integer getSerialNum() {
		
		return dpmInsdao.getSerialNum();
	}
	
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
	@Override
	public DpmInsModel getMsgByPartNo(Map<String, Object> map) {
		
		return dpmInsdao.getMsgByPartNo(map);
	}
	
	/**
	 * 
	 * @Title: 获取新增界面处理结果下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:17:47
	 * @throws
	 */
	@Override
	public List<DictVO> getUnloadDealResult() {
		
		return dpmInsdao.getUnloadDealResult();
	}
	
	/**
	 * 
	 * @Title: 获取新增界面不良品项目下拉框 
	 * @Description:  
	 * @param @return    
	 * @return List<DictVO>     
	 * @time   2018年9月19日 上午10:42:06
	 * @throws
	 */
	@Override
	public List<DpmItemModel> getUnloadDpmCode() {
		
		return dpmInsdao.getUnloadDpmCode();
	}
	
	/**
	 * 
	 * @Title: 修改系统参数流水号为最大值 
	 * @Description:  
	 * @param @param seriaNum    
	 * @return void     
	 * @time   2018年9月19日 下午2:24:47
	 * @throws
	 */
	@Override
	public void updateSerialNum(Integer seriaNum) {
		dpmInsdao.updateSerialNum(seriaNum);
	}
	
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
	@Override
	public List<DpmInsModel> getUnloadRespDep(Map<String, Object> map) {
		
		return dpmInsdao.getUnloadRespDep(map);
	}
	
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
	@Override
	public DpmInsModel getDefaultMsg(Map<String, Object> map) {
		
		return dpmInsdao.getDefaultMsg(map);
	}
	
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
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) {
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_DPM_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("APPLY_NO");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
		
	}
	
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
	@Override
	public ResultMessage updateInsStatus(String[] applyNos, String submit,String checker) {
		ResultMessage message = null;
		String FROM_NAME = null;
		if (applyNos!=null) {
			for (String applyNo : applyNos) {
				DpmInsModel dpmInsModel = dpmInsdao.get(applyNo);
				if ( Constant.SUBIMT_TYPE_STATUS.equals(submit) && (!Constant.UNSUBIMT.equals(dpmInsModel.getInsStatus())) ) {
					message = new ResultMessage(ResultMessage.FAIL, "包含已提交信息，提交失败");
					return message;
				}
				if ( Constant.CHECK_TYPE.equals(submit) && ((Constant.CHECK_TYPE.equals(dpmInsModel.getInsStatus()) || (Constant.UNSUBIMT.equals(dpmInsModel.getInsStatus())))) ) {
					message = new ResultMessage(ResultMessage.FAIL, "信息未提交或者信息已审核，审核失败");
					return message;
				}
				if (Constant.REJECT.equals(submit) && (!Constant.SUBIMT_TYPE_STATUS.equals(dpmInsModel.getInsStatus()))  ) {
					message = new ResultMessage(ResultMessage.FAIL, "包含未审核信息，驳回失败");
					return message;
				}
				if (Constant.TURN_MATERIAL.equals(submit) && ((Constant.SUBIMT_TYPE_STATUS.equals(dpmInsModel.getDpmType())) || 
						(!Constant.SUBIMT_TYPE_STATUS.equals(dpmInsModel.getInsStatus()))) ) {
					message = new ResultMessage(ResultMessage.FAIL, "只能转换提交且是加工不良数据信息，转为材不失败");
					return message;
				}
			}

			
		//修改不良品申请单状态
		for (String applyNo : applyNos) {
			dpmInsdao.updateInsStatus(applyNo,submit,checker);
		}
		
		if (Constant.SUBIMT_TYPE_STATUS.equals(submit)) {
			message = new ResultMessage(ResultMessage.SUCCESS, "提交成功");
			FROM_NAME = "不良品提交";
		}
		if (Constant.CHECK_TYPE.equals(submit)) {
			Map< String, Object> map = new HashMap<String, Object>();
			map.put("checker", checker);
			map.put("applyNos", applyNos);
			map.put("typeCB", DpmInsModel.DPM_TYPE_CB);
			map.put("typeJB", DpmInsModel.DPM_TYPE_JB);
			map.put("tranTypeCB", DpmInsModel.TRAN_TYPE_CB);
			map.put("tranTypeJB", DpmInsModel.TRAN_TYPE_JB);
			map.put("rowNo", DpmInsModel.ROW_NO_STRING);
			dpmInsdao.insertTranLms(map);
			dpmInsdao.insertTranLmsDetial(map);
			FROM_NAME = "不良品审核";
			message = new ResultMessage(ResultMessage.SUCCESS, "审核成功");
		}
		if (Constant.REJECT.equals(submit)) {
			FROM_NAME = "不良品驳回";
			message = new ResultMessage(ResultMessage.SUCCESS, "信息已驳回");
		}
		if (Constant.TURN_MATERIAL.equals(submit)) {
			FROM_NAME = "不良品转为材不";
			message = new ResultMessage(ResultMessage.SUCCESS, "转为材料不良成功");
		}
		return message;
	   }
		
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(checker); //记录修改人 == 审核人
		logVO.setOpeIp(""); 
		logVO.setFromName(FROM_NAME);
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("APPLY_NO");
		pkColumnVO.setColumnValArr(applyNos);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		return message;
	}
	
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
	@Override
	public List<DpmInsModel> queryDpmInsByKey(DpmInsModel model) {
		
		return dpmInsdao.queryDpmInsByKey(model);
	}
	
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
	@Override
	public List<DpmInsModel> queryDpmInsDetailList(DpmInsModel model_q) {
		
		return dpmInsdao.queryDpmInsDetailList(model_q);
	}
	
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
	@Override
	@Transactional
	public ResultMessage toExcepOrder(String applyNos, String account, String curFactoryCode,String ipAddr) {
		ResultMessage message = null;
		if (applyNos!=null) {
			String[] applyNoArr = applyNos.split(",");
			
			//日志记录
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("生成例外订单");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_DPM_INS");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("APPLY_NO");
			pkColumnVO.setColumnValArr(applyNoArr);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			for (String applyNo : applyNoArr) {
				DpmInsModel dpmInsModel = dpmInsdao.get(applyNo);
				//如果不良品信息未提交则无法生成例外订单
				if (Constant.UNSUBIMT.equals(dpmInsModel.getInsStatus())) {
					message = new ResultMessage(ResultMessage.FAIL, "订单未提交，无法生成例外订单");
					return message;
				}else {
//					String arrayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
					java.util.Date  date = new java.util.Date();
					java.sql.Date arrayDate =new java.sql.Date(date.getTime());
					//调用存储过程生成例外订单号
					String excepNo = dpmInsdao.getExcepOrderNo(arrayDate,Constant.EXC,curFactoryCode);
					dpmInsModel.setExcepOrderNo(excepNo);
					dpmInsModel.setRowNo(DpmInsModel.ROW_NO_STRING);
					dpmInsModel.setFlag(DpmInsModel.FALG);
				    String arrayDateStr = new SimpleDateFormat("yyyyMMdd").format(arrayDate);
					dpmInsModel.setArriveDate(arrayDateStr);
					dpmInsModel.setCreateBy(account);
					dpmInsModel.setAccount(account);
					dpmInsModel.setApplyNo(applyNo);
					dpmInsModel.setOrderType(DpmInsModel.ORDER_TYPE);
					String id = queryDao.getSequenceNextVal("SEQ_MM_SW_ORDER_DETAIL");
					dpmInsModel.setId(id);
					//将不良品信息插入订单采购表IF_LOGISTICS_ORDER
					dpmInsdao.insertExcepOrder(dpmInsModel);
					//将不良品信息插入订单明细表IF_LOGISTICS_ORDER_DETAIL
					dpmInsdao.insertExcepOrderDetail(dpmInsModel);
					
					//将不良品信息插入共享平台订单表
					dpmInsdao.insetExcepSwOrder(dpmInsModel);
					//插入共享平台订单明细表
					dpmInsdao.insertExcepSwOrderDetail(dpmInsModel);
					
					//修改不良品信息中手工例外生成订单状态
					dpmInsdao.updateInsStatus(applyNo,Constant.EXCEP_ORDER,account);
				}
			}
			message = new ResultMessage(ResultMessage.SUCCESS, "生成例外订单成功");
			return message;
		}
			message = new ResultMessage(ResultMessage.FAIL, "生成例外订单失败");
  		    return message;
	}
	
	/**
	 * 
	 * @Description: 不良品新增界面，查询零件号弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<DpmInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月30日 上午11:21:09
	 */
	@Override
	public PageList<DpmInsModel> getPartNo(DpmInsModel model,DefaultPage p) {
		
		return dpmInsdao.getPartNo(model,p);
	}
	
	/**
	 * 
	 * @Description: 责任反馈修改状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:30:05
	 */
	@Override
	public void repFeedback(DpmInsModel model) {
		dpmInsdao.repFeedback(model);
	}
	
	/**
	 * 
	 * @Description: 责任反馈插入责任反馈表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月31日 下午6:30:33
	 */
	@Override
	public void insertFeedback(DpmInsModel model) {
		dpmInsdao.insertFeedback(model);
	}
	
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
	@Override
	public List<DpmInsModel> feedbaceWriteback(String applyNo) {
		
		return dpmInsdao.feedbaceWriteback(applyNo);
	}
	
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
	@Override
	public List<DpmInsModel> getUnloadRespDepAll(String account) {
		
		return dpmInsdao.getUnloadRespDepAll(account);
	}
	
	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param list   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 上午10:05:56
	 */
	@Override
	public void updatePrintStatus(String[] applyNoArr) {
		IUser user = ContextUtil.getCurrentUser();
		DpmInsModel model = new DpmInsModel();
		for (String applyNo : applyNoArr) {
			model.setPrintStatus(DpmInsModel.PRINT_STATUS);
			model.setPrintUser(user.getAccount());
			model.setApplyNo(applyNo);
			dpmInsdao.updatePrintStatus(model);
		}
	}
	
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
	@Override
	public Boolean validateCarType(DpmInsModel model) {
		Integer count = dpmInsdao.validateCarType(model);
		if (null != count && count > 0) {
			return true;
		} 
		return false;
	}

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
	@Override
	public boolean isChecker(DpmInsModel model) {
		Integer count = dpmInsdao.isChecker(model);
		if (null != count && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void submitCheck(String[] applyNos, DpmInsModel model) {

		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(model.getCheckUser()); //记录修改人 == 审核人
		logVO.setOpeIp(""); 
		logVO.setFromName("不良品审核");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("APPLY_NO");
		pkColumnVO.setColumnValArr(applyNos);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
//		if (Constant.CHECK_TYPE.equals(submit)) {
			Map< String, Object> map = new HashMap<String, Object>();
			map.put("checker", model.getCheckUser());
			map.put("applyNos", applyNos);
			map.put("typeCB", DpmInsModel.DPM_TYPE_CB);
			map.put("typeJB", DpmInsModel.DPM_TYPE_JB);
			map.put("tranTypeCB", DpmInsModel.TRAN_TYPE_CB);
			map.put("tranTypeJB", DpmInsModel.TRAN_TYPE_JB);
			map.put("rowNo", DpmInsModel.ROW_NO_STRING);
			
			dpmInsdao.insertTranLms(map);			//数据写入iLMS零件仓库转移主表
			dpmInsdao.insertTranLmsDetial(map);		//数据写入iLMS零件仓库转义明细表
//			FROM_NAME = "不良品审核";
//			message = new ResultMessage(ResultMessage.SUCCESS, "审核成功");
//		}
		dpmInsdao.submitCheck(applyNos,model);
	}

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
	@Override
	public List<DpmInsModel> getListByApplyArr(String[] applyNoArr) {
		
		return dpmInsdao.getListByApplyArr(applyNoArr);
	}

	@Override
	public boolean isZCpart(DpmInsModel model) {
		Integer count =  dpmInsdao.getCountByPartNo(model);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getPartListByPartNo(String partNo) {
		
		return dpmInsdao.getPartListByPartNo(partNo);
	}

	@Override
	public String getDpmFactory(String curFactoryCode) {
		
		return dpmInsdao.getDpmFactory(curFactoryCode);
	}
}
