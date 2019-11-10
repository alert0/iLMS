package com.hanthink.jit.dao;

import java.util.List;

import com.hanthink.jit.model.JitInsPrinterConfigModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInsPrinterConfigDao
 * @Description: 拣货工厂与打印机配置
 * @author dtp
 * @date 2018年10月25日
 */
public interface JitInsPrinterConfigDao extends Dao<String, JitInsPrinterConfigModel>{

	/**
	 * @Description: 拣货工厂与打印机关系查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsPrinterConfigModel>   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	PageList<JitInsPrinterConfigModel> queryJitInsPrinterConfigPage(JitInsPrinterConfigModel model, DefaultPage page);

	/**
	 * @Description: 新增拣货工程与打印机关系
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	void insertJitInsPrintConfig(JitInsPrinterConfigModel model);

	/**
	 * @Description: 更新拣货工程与打印机关系
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	void updateJitInsPrintConfig(JitInsPrinterConfigModel model);

	/**
	 * @Description: 拣货工程与打印机关系查询  excel导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsPrinterConfigModel>   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	List<JitInsPrinterConfigModel> queryJitInsPrinterConfigList(JitInsPrinterConfigModel model);

	/**
	 * @Description: 删除 拣货工程与打印机关系
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	void deleteJitInsPrintConfig(JitInsPrinterConfigModel jitInsPrinterConfigModel);

}
