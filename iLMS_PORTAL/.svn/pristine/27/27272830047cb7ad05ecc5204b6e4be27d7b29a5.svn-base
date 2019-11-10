package com.hanthink.inv.manager;

import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:空容器库存查询业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyManager extends Manager<String, InvEmptyModel>{
	/**
	 * 空容器分页查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	PageList<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page)throws Exception;
	/**
	 * 修改空容器数据信息业务接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	void updateForEmpty(InvEmptyModel model)throws Exception;
}
