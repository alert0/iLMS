package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvEmptyOutModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:空容器出库指示业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyOutManager extends Manager<String, InvEmptyOutModel>{
	/**
	 * 空容器出库指示分页查询业务层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	PageList<InvEmptyOutModel> queryEmptyOutForPage(InvEmptyOutModel model, Page page)throws Exception;
	/**
	 * 空容器出库指示数据Excel导出业务接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	List<InvEmptyOutModel> queryForExcelExport(InvEmptyOutModel model)throws Exception;
	
	/**
	 * 生成空容器
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	Integer makeEmptyContainer()throws Exception;

}
