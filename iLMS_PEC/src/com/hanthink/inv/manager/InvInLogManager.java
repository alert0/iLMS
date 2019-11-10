package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:入库管理控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvInLogManager extends Manager<String, InvInLogModel>{
	/**
	 * 入库管理数据分页查询业务接口
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	PageList<InvInLogModel> queryInLogForPage(InvInLogModel model, Page page)throws Exception;
	/**
	 *  入库数据导出业务接口
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvInLogModel> queryInLogForExport(InvInLogModel model)throws Exception;

}
