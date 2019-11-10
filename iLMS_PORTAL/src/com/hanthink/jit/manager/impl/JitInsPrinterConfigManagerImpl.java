package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.JitInsPrinterConfigDao;
import com.hanthink.jit.manager.JitInsPrinterConfigManager;
import com.hanthink.jit.model.JitInsPrinterConfigModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JitInsPrinterConfigManagerImpl
 * @Description: 拣货工厂与打印机配置
 * @author dtp
 * @date 2018年10月25日
 */
@Service("jitInsPrinterConfigManager")
public class JitInsPrinterConfigManagerImpl extends AbstractManagerImpl<String, JitInsPrinterConfigModel> 
		implements JitInsPrinterConfigManager{

	@Resource 
	private JitInsPrinterConfigDao jitInsPrinterConfigDao;
	
	@Override
	protected Dao<String, JitInsPrinterConfigModel> getDao() {
		return jitInsPrinterConfigDao;
	}

	/**
	 * @Description: 拣货工厂与打印机关系查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsPrinterConfigModel>   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public PageList<JitInsPrinterConfigModel> queryJitInsPrinterConfigPage(JitInsPrinterConfigModel model,
			DefaultPage page) {
		return jitInsPrinterConfigDao.queryJitInsPrinterConfigPage(model, page);
	}

	/**
	 * @Description: 新增拣货工程与打印机关系
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public void insertJitInsPrintConfig(JitInsPrinterConfigModel model) {
		jitInsPrinterConfigDao.insertJitInsPrintConfig(model);
	}

	/**
	 * @Description: 更新拣货工程与打印机关系
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public void updateJitInsPrintConfig(JitInsPrinterConfigModel model) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(model.getLastModifiedIp()); 
		logVO.setFromName("配送单打印机配置");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JIT_INS_PRINTER_CONFIG");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		jitInsPrinterConfigDao.updateJitInsPrintConfig(model);
	}

	/**
	 * @Description: 拣货工程与打印机关系查询  excel导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsPrinterConfigModel>   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public List<JitInsPrinterConfigModel> queryJitInsPrinterConfigList(JitInsPrinterConfigModel model) {
		return jitInsPrinterConfigDao.queryJitInsPrinterConfigList(model);
	}

	/**
	 * @Description: 删除拣货工程与打印机关系
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public void deleteJitInsPrintConfig(JitInsPrinterConfigModel[] models, String ipAddr) {
		for (int i = 0; i < models.length; i++) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("配送单打印机配置");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_JIT_INS_PRINTER_CONFIG");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(models[i].getId());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			jitInsPrinterConfigDao.deleteJitInsPrintConfig(models[i]);
		}
	}

	

}
