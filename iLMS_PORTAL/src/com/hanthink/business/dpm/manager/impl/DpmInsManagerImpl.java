package com.hanthink.business.dpm.manager.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.business.dpm.dao.DpmInsDao;
import com.hanthink.business.dpm.manager.DpmInsManager;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
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
	@Override
	public PageList<DpmInsModel> queryDpmInsForPage(DpmInsModel model, DefaultPage p) {
		
		return dpmInsdao.queryDpmInsForPage(model,p);
	}
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
	@Override
	public Integer getSerialNum() {
		
		return dpmInsdao.getSerialNum();
	}
	@Override
	public DpmInsModel getMsgByPartNo(String partNo) {
		
		return dpmInsdao.getMsgByPartNo(partNo);
	}
	@Override
	public List<DictVO> getUnloadDealResult() {
		
		return dpmInsdao.getUnloadDealResult();
	}
	@Override
	public List<DictVO> getUnloadDpmCode() {
		
		return dpmInsdao.getUnloadDpmCode();
	}
	@Override
	public void updateSerialNum(Integer seriaNum) {
		dpmInsdao.updateSerialNum(seriaNum);
	}
	@Override
	public List<DictVO> getUnloadRespDep() {
		
		return dpmInsdao.getUnloadRespDep();
	}
	@Override
	public DpmInsModel getDefaultMsg(String userId) {
		
		return dpmInsdao.getDefaultMsg(userId);
	}
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) {
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_DPM_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("APPLY_NO");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
		
	}
	
	@Override
	public ResultMessage updateInsStatus(String[] applyNos, String submit,String checker) {
		ResultMessage message = null;
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
		}
		if (Constant.CHECK_TYPE.equals(submit)) {
			message = new ResultMessage(ResultMessage.SUCCESS, "审核成功");
		}
		if (Constant.REJECT.equals(submit)) {
			message = new ResultMessage(ResultMessage.SUCCESS, "信息已驳回");
		}
		if (Constant.TURN_MATERIAL.equals(submit)) {
			message = new ResultMessage(ResultMessage.SUCCESS, "转为材料不良成功");
		}
		return message;
	   }
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
	public ResultMessage toExcepOrder(String[] applyNos, String account, String curFactoryCode) {
		ResultMessage message = null;
		if (applyNos!=null) {
			for (String applyNo : applyNos) {
				DpmInsModel dpmInsModel = dpmInsdao.get(applyNo);
				//如果不良品信息未提则无法生成例外订单
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
				    String arrayDateStr = new SimpleDateFormat("yyyyMMdd").format(arrayDate);
					dpmInsModel.setArriveDate(arrayDateStr);
					dpmInsModel.setCreateBy(account);
					
					
					//将不良品信息插入订单采购表MM_SW_ORDER
					dpmInsdao.insertExcepOrder(dpmInsModel);
					//将不良品信息插入订单明细表MM_SW_ORDER_DETAIL
					dpmInsdao.insertExcepOrderDetail(dpmInsModel);
					//修改不良品信息中手工例外生成订单状态
					dpmInsdao.updateInsStatus(applyNo,Constant.EXCEP_ORDER,account);
					message = new ResultMessage(ResultMessage.SUCCESS, "生成例外订单成功");
					return message;
				}
			}
		}
			message = new ResultMessage(ResultMessage.FAIL, "生成例外订单失败");
  		    return message;
	}
}
