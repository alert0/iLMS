package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvEmptyOutModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:空容器出库指示持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyOutDao extends Dao<String, InvEmptyOutModel>{
	/**
	 * 空容器出库指示分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	List<InvEmptyOutModel> queryEmptyOutForPage(InvEmptyOutModel model, Page page)throws Exception;
	/**
	 * 出库指示数据Excel导出查询持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	List<InvEmptyOutModel> queryForExcelExport(InvEmptyOutModel model)throws Exception;
	/**
	 * 空容器生成
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	Integer makeEmptyContainer(Map<String, String> paramMap)throws Exception;

}
