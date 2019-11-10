package com.hanthink.jisi.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jisi.dao.JisiInsDao;
import com.hanthink.jisi.manager.JisiInsManager;
import com.hanthink.jisi.model.JisiInsModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("JisiInsManager")
public class JisiInsManagerImpl extends AbstractManagerImpl<String, JisiInsModel> implements JisiInsManager{

	@Resource
	private JisiInsDao dao;
	@Override
	protected Dao<String, JisiInsModel> getDao() {
		
		return dao;
	}
	
	/**
	 * 
	 * @Description: 厂内同步票据查询，分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:17:43
	 */
	@Override
	public PageList<JisiInsModel> queryJisiInsForPage(JisiInsModel model, DefaultPage p) {
		
		return dao.queryJisiInsForPage(model, p);
	}

	/**
	 * 
	 * @Description: 厂内同步票据查询，明细查看
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:23:45
	 */
	@Override
	public PageList<JisiInsModel> queryJisiInsDetailForPage(JisiInsModel model, DefaultPage p) {
		
		return dao.queryJisiInsDetailForPage(model,p);
	}

	/**
	 * 
	 * @Description: 厂内同步票据查询，导出
	 * @param @param model
	 * @param @return   
	 * @return List<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:25:40
	 */
	@Override
	public List<JisiInsModel> queryJisiInsByKey(JisiInsModel model) {
		
		return dao.queryJisiInsByKey(model);
	}

	/**
	 * 
	 * @Description: 查询订单打印的数据
	 * @param @param model_q
	 * @param @return   
	 * @return List<JisoInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:49:02
	 */
	@Override
	public List<JisiInsModel> queryJisiInsDetailList(JisiInsModel model_q) {
		
		return dao.queryJisiInsDetailList(model_q);
	}

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param list_printInfo
	 * @param @param ipAddr
	 * @param @param insNoArr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:49:22
	 */
	@Override
	public void updatePrintStatus(List<JisiInsModel> list_printInfo, String ipAddr, String[] insNoArr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("更新厂内同步指示票打印状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JISI_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("INS_NO");
		pkColumnVO.setColumnValArr(insNoArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		for (JisiInsModel JisiInsModel : list_printInfo) {
			dao.updatePrintStatusModel(JisiInsModel);
		}
	}

}
