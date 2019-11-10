package com.hanthink.jit.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.JitInsDao;
import com.hanthink.jit.manager.JitInsManager;
import com.hanthink.jit.model.JitInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.mysql.jdbc.StringUtils;

/**
 * @ClassName: JitInsManagerImpl
 * @Description: 配送单查询
 * @author dtp
 * @date 2018年10月8日
 */
@Service("jitInsManager")
public class JitInsManagerImpl extends AbstractManagerImpl<String, JitInsModel> implements JitInsManager{

	@Resource 
	private JitInsDao jitInsDao;
	
	@Override
	protected Dao<String, JitInsModel> getDao() {
		return jitInsDao;
	}

	/**
	 * @Description: 配送单查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public PageList<JitInsModel> queryJitInsPage(JitInsModel model, DefaultPage page) {
		return jitInsDao.queryJitInsPage(model, page);
	}

	/**
	 * @Description: 配送单导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public List<JitInsModel> queryJitInsList(JitInsModel model) {
		return jitInsDao.queryJitInsList(model);
	}

	/**
	 * @Description: 配送单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public PageList<JitInsModel> queryJitInsDetailPage(JitInsModel model, DefaultPage page) {
		return jitInsDao.queryJitInsDetailPage(model, page);
	}

	/**
	 * @Description: 查询配送单明细(配送单打印)
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public List<JitInsModel> queryJitInsDetailList(JitInsModel model_q) {
		return jitInsDao.queryJitInsDetailList(model_q);
	}

	/**
	 * @Description: 更新配送单打印状态
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public void updatePrintInfo(List<JitInsModel> list_printInfo) {
		//jitInsDao.updatePrintInfo(list_printInfo);
		//更新明细备货数量,备货次数
		for (int i = 0; i < list_printInfo.size(); i++) {
			JitInsModel model = list_printInfo.get(i);
			//根据备件状态查询指示票明细,全部备件不执行后续操作
			List<JitInsModel> list_insDetail = jitInsDao.queryJitInsDetailListForUpdateInvOutLog(model);
			if(null != list_insDetail && list_insDetail.size() > 0) {
				if("2".equals(list_insDetail.get(0).getPrepareStatus())) {
					continue;
				} 
			}
			
			//记录拉动配送单备件出库日志
			//1.查询是否有出库记录
			List<JitInsModel> list_log = jitInsDao.queryInvOutLogList(model);
			//有备件出库记录,保存未出库数据
			if(null != list_log && list_log.size() > 0) {
				JitInsModel model_invOutLog = list_log.get(0);
				//已出库次数
				String outTimes = model_invOutLog.getOutTimes();
				//出库单号
				String invOutNo = model.getInsNo() + "_" + (Integer.valueOf(outTimes) + 1);
				//更新model_invOutLog
				model_invOutLog.setInvOutNo(invOutNo);
				model_invOutLog.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				model_invOutLog.setOutTimes((Integer.valueOf(outTimes) + 1)+"");
				model_invOutLog.setCreationUser(ContextUtil.getCurrentUser().getAccount());
				//insert mm_inv_out表
				jitInsDao.insertInvOutLog(model_invOutLog);
				//insert mm_inv_out_detail表
				List<JitInsModel> list_batchUpdateInvOutDetail = new ArrayList<JitInsModel>();
				for (JitInsModel model_detail : list_insDetail) {
					//根据INS_NO,LOCATION,PART_NO查询已出库记录
					model_detail.setInsNo(model_detail.getInsNo()+"_");
					List<JitInsModel> list_yb = jitInsDao.queryInvOutLogByModelDetail(model_detail);
					if(null != list_yb && list_yb.size() > 0) {
						//明细数据
						JitInsModel jim = list_yb.get(0);
						jim.setInvOutNo(invOutNo);
						jim.setLocation(model_detail.getLocation());
						jim.setOutTimes((Integer.valueOf(outTimes) + 1)+"");
						jim.setPartNo(model_detail.getPartNo());
						jim.setOrderQty(model_detail.getOrderQty());
						if(!StringUtils.isNullOrEmpty(jim.getRecQty())) {
							jim.setRecQty((Integer.valueOf(model_detail.getOrderQty()) - Integer.valueOf(jim.getRecQty())) + "");
							//如果,订购量>已出库数量,记录剩余出库日志
							if(Integer.valueOf(model_detail.getOrderQty()) > Integer.valueOf(jim.getRecQty())) {
								list_batchUpdateInvOutDetail.add(jim);
							}
						}else {
							JitInsModel jim_new = new JitInsModel();
							jim_new.setInvOutNo(invOutNo);
							jim_new.setLocation(model_detail.getLocation());
							jim_new.setOutTimes((Integer.valueOf(outTimes) + 1)+"");
							jim_new.setPartNo(model_detail.getPartNo());
							jim_new.setOrderQty(model_detail.getOrderQty());
							jim_new.setRecQty(model_detail.getOrderQty());
							list_batchUpdateInvOutDetail.add(jim_new);
						}
					}
				}
				//批量insert mm_inv_out_detail表
				jitInsDao.insertInvOutDetailLog(list_batchUpdateInvOutDetail);
			}else if(null != list_log && list_log.size() == 0){
			//无备件出库记录,获取指示票明细,未备件---->全部备件
				if(null != list_insDetail && list_insDetail.size() > 0) {
					//写入出库记录mm_inv_out,mm_inv_out_detail表
					JitInsModel model_invOut = list_insDetail.get(0);
					//出库单号
					String invOutNo = model_invOut.getInsNo() + "_1";
					model_invOut.setInvOutNo(invOutNo);
					model_invOut.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					model_invOut.setOutTimes("1");
					if("A1".equals(model_invOut.getWorkcenter())) {
						model_invOut.setFromDepotNo("AA11");
						model_invOut.setToDepotNo("AM11");
					}else {
						model_invOut.setFromDepotNo("WA11");
						model_invOut.setToDepotNo("WM11");
					}
					model_invOut.setCreationUser(ContextUtil.getCurrentUser().getAccount());
					//insert mm_inv_out表
					jitInsDao.insertInvOutLog(model_invOut);
					//insert mm_inv_out_detail表
					for (JitInsModel de : list_insDetail) {
						de.setInvOutNo(invOutNo);
						de.setOutTimes("1");
						de.setRecQty(de.getOrderQty());
					}
					jitInsDao.insertInvOutDetailLog(list_insDetail);
				}
			}
			
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(model.getPrintUserIp()); 
			logVO.setFromName("拉动配送单备件修改备件状态");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_JIT_INS_DETAIL");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("INS_NO");
			pkColumnVO.setColumnVal(model.getInsNo());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			jitInsDao.updateDetailPrepareStatus(model);
		}
		
		for (int i = 0; i < list_printInfo.size(); i++) {
			JitInsModel model = list_printInfo.get(i);
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(model.getPrintUserIp()); 
			logVO.setFromName("拉动配送单备件修改打印状态");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_SPS_INS");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("INS_NO");
			pkColumnVO.setColumnVal(model.getInsNo());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			jitInsDao.updatePrintState(model);
		}
		
	}

	/**
	 * @Description: 配送单明细导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月6日
	 */
	@Override
	public List<JitInsModel> downloadJitInsDetail(JitInsModel model) {
		return jitInsDao.downloadJitInsDetail(model);
	}

}
