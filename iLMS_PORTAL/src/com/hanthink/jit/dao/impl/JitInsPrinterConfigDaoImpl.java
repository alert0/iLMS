package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitInsPrinterConfigDao;
import com.hanthink.jit.model.JitInsPrinterConfigModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInsPrinterConfigDaoImpl
 * @Description: 拣货工厂与打印机配置
 * @author dtp
 * @date 2018年10月25日
 */
@Repository
public class JitInsPrinterConfigDaoImpl extends MyBatisDaoImpl<String, JitInsPrinterConfigModel> implements JitInsPrinterConfigDao{

	@Override
	public String getNamespace() {
		return JitInsPrinterConfigModel.class.getName();
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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitInsPrinterConfigModel> queryJitInsPrinterConfigPage(JitInsPrinterConfigModel model,
			DefaultPage page) {
		return (PageList<JitInsPrinterConfigModel>) this.getList("queryJitInsPrinterConfigPage", model, page);
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
		this.insertByKey("insertJitInsPrintConfig", model);
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
		this.updateByKey("updateJitInsPrintConfig", model);
	}

	/**
	 * @Description: 拣货工程与打印机关系查询  excel导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsPrinterConfigModel>   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsPrinterConfigModel> queryJitInsPrinterConfigList(JitInsPrinterConfigModel model) {
		return (List<JitInsPrinterConfigModel>) this.getList("queryJitInsPrinterConfigPage", model);
	}

	/**
	 * @Description: 删除 拣货工程与打印机关系
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@Override
	public void deleteJitInsPrintConfig(JitInsPrinterConfigModel jitInsPrinterConfigModel) {
		this.deleteByKey("deleteJitInsPrintConfig", jitInsPrinterConfigModel);
	}

}
