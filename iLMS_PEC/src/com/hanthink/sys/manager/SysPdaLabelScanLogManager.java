package com.hanthink.sys.manager;

import java.util.List;

import com.hanthink.sys.model.SysPdaLabelScanLogModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：PDA标签扫描日志 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysPdaLabelScanLogManager extends Manager<String, SysPdaLabelScanLogModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<SysPdaLabelScanLogModel></p>  
	 * <p>Description: SysPdaLabelScanLogManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<SysPdaLabelScanLogModel> querySysPdaLabelScanLogForPage(SysPdaLabelScanLogModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<SysPdaLabelScanLogModel> querySysPdaLabelScanLogByKey(SysPdaLabelScanLogModel model);
}
