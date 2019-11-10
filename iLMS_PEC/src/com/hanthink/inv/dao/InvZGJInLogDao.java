package com.hanthink.inv.dao;

import java.util.List;

import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.inv.model.InvZGJInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

/**
 * @ClassName: InvZGJInLogDao
 * @Description: 支给件入库查询Dao
 * @author dtp
 * @date 2019年4月10日
 */
public interface InvZGJInLogDao extends Dao<String, InvZGJInLogModel>{

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
