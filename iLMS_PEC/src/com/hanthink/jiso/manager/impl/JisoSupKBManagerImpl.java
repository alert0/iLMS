package com.hanthink.jiso.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.dao.TableDataLogDao;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jiso.dao.JisoSupKBDao;
import com.hanthink.jiso.manager.JisoSupKBManager;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * @Desc		: 厂外同步出货地切换看板
 * @FileName	: JisoSupKBManagerImpl.java
 * @CreateOn	: 2018年11月21日 上午9:48:45
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@Service("JisoSupKBManager")
public class JisoSupKBManagerImpl implements JisoSupKBManager {
	
	@Resource
	protected TableDataLogDao tableDataLogDao;
	
	@Resource
	private JisoSupKBDao dao;

	/**
	 * 查询厂外同步出货地切换看板数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:11:18
	 */
	@Override
	public PageList<Map<String, Object>> queryJisoSupKB(Map<String, Object> param, Page page) {
		return dao.queryJisoSupKB(param, page);
	}

	/**
	 * 更新派车处理信息
	 * @param param
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:23:16
	 */
	@Override
	public int updateDealData(Map<String, Object> param) {
		return dao.updateDealData(param);
	}

	/**
	 * 查询出货地切换派车汇总
	 * @param model
	 * @param page
	 * @return
	 */
	@Override
	public PageList<JisoOrderModel> queryJisoSupCarPage(JisoOrderModel model, DefaultPage page) {
		return dao.queryJisoSupCarPage(model, page);
	}

	/**
	 * @Description:  更新车牌 
	 * @param: @param List    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月20日
	 */
	@Override
	public void updateJisoSupCar(List<JisoOrderModel> list, String ipAddr) {
		for (JisoOrderModel jisoOrderModel : list) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("厂外同步出货地切换派车处理");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_JISO_ORDER_SUP_KB_DEAL");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ORDER_NO");
			pkColumnVO.setColumnVal(jisoOrderModel.getOrderNo());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			dao.updateJisoSupCar(jisoOrderModel);
		}
	}

	/**
	 * @Description: 派车处理 
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月21日
	 */
	@Override
	public void updateCarNo(JisoOrderModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("厂外同步出货地切换派车处理");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JISO_ORDER_SUP_KB_DEAL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ORDER_NO");
		pkColumnVO.setColumnVal(model.getOrderNo());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		dao.updateJisoSupCar(model);
	}

}
