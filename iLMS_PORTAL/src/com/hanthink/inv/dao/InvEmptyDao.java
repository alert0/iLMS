package com.hanthink.inv.dao;

import java.util.List;

import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:空容器查询业务持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyDao extends Dao<String, InvEmptyModel>{
	/**
	 * 空容器分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	List<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page)throws Exception;
	/**
	 * 修改空容器数量持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	void updateForEmpty(InvEmptyModel model)throws Exception;

}
