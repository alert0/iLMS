package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvOutLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:出库查询业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvOutLogManager extends Manager<String, InvOutLogModel>{
	/**
	 * 分页数据查询业务接口
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	PageList<InvOutLogModel> queryOutLogForPage(InvOutLogModel model,Page page)throws Exception;
	/**
	 * 查询数据导出业务接口
	 * @param model
	 * @param flag
	 * @return
	 * @author zmj
	 * @date 2018年10月11日
	 */
	List<InvOutLogModel> queryOutLogForExport(InvOutLogModel model);
	/**
	 * 加载出库类型数据下拉框
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月7日
	 */
	List<InvOutLogModel> queryOutType()throws Exception;
	
}
