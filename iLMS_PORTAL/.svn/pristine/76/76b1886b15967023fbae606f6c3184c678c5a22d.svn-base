package com.hanthink.jiso.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jiso.dao.JisoInsDao;
import com.hanthink.jiso.manager.JisoInsManager;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JisoInsManagerImpl
 * @Description: 厂外同步指示票managerImpl
 * @author dtp
 * @date 2018年9月18日
 */
@Service("jisoInsManager")
public class JisoInsManagerImpl extends AbstractManagerImpl<String, JisoInsModel> implements JisoInsManager{

	@Resource 
	private JisoInsDao jisoInsDao;
	
	@Override
	protected Dao<String, JisoInsModel> getDao() {
		return jisoInsDao;
	}

	/**
	 * @Description: 查询厂外同步指示票  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	@Override
	public PageList<JisoInsModel> queryJisoInsPage(JisoInsModel model, DefaultPage page) {
		return jisoInsDao.queryJisoInsPage(model, page);
	}

	/**
	 * @Description: 根据指示票号查询厂外同步指示票明细   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@Override
	public PageList<JisoInsModel> queryJisoInsDetailPageByInsNo(JisoInsModel model, DefaultPage page) {
		return jisoInsDao.queryJisoInsDetailPageByInsNo(model, page);
	}

	/**
	 * @Description: 导出厂外同步指示票excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@Override
	public List<JisoInsModel> queryJisoInsList(JisoInsModel model) {
		return jisoInsDao.queryJisoInsList(model);
	}

	/**
	 * @Description: 导出厂外同步指示票明细excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@Override
	public List<JisoInsModel> downloadJisoInsDetail(JisoInsModel model) {
		return jisoInsDao.downloadJisoInsDetail(model);
	}

	/**
	 * @Description:  查询厂外同步指示票明细 
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月20日
	 */
	@Override
	public List<JisoInsModel> queryJisoInsDetailList(List<JisoInsModel> list) {
		return jisoInsDao.queryJisoInsDetailList(list);
	}

	/**
	 * @Description: 查询厂外同步指示票明细(指示票打印) 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	@Override
	public List<JisoInsModel> queryJisoInsDetailList(JisoInsModel model_q) {
		return jisoInsDao.queryJisoInsDetailList(model_q);
	}

	/**
	 * @Description: 厂外同步指示票更新打印状态 
	 * @param: @param list_printInfo
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月29日
	 */
	@Override
	public void updatePrintStatus(List<JisoInsModel> list_printInfo, String ipAddr, String[] insNoArr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("更新厂外同步指示票打印状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JISO_INS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("INS_NO");
		pkColumnVO.setColumnValArr(insNoArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//jisoInsDao.updatePrintStatus(list_printInfo);
		for (JisoInsModel JisoInsModel : list_printInfo) {
			jisoInsDao.updatePrintStatusModel(JisoInsModel);
		}
		
	}

}
