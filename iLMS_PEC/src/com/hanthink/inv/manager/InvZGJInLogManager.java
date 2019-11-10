package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.inv.model.InvZGJInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: InvZGJInLogManager
 * @Description: 支给件入库查询Manager
 * @author dtp
 * @date 2019年4月10日
 */
public interface InvZGJInLogManager extends Manager<String, InvZGJInLogModel>{

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
