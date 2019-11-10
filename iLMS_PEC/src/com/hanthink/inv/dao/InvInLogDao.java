package com.hanthink.inv.dao;

import java.util.List;

import com.hanthink.inv.model.InvInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:入库管理业务持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvInLogDao extends Dao<String, InvInLogModel>{
	/**
	 * 入库数据分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvInLogModel> queryInLogForPage(InvInLogModel model, Page page)throws Exception;
	/**
	 * 入库数据导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	List<InvInLogModel> queryInLogForExport(InvInLogModel model)throws Exception;

}
