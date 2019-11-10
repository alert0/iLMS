package com.hanthink.pup.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupPlanModel;
import com.hanthink.pup.model.PupPlanPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:取货计划查询业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupPlanManager extends Manager<String, PupPlanModel>{
	/**
	 * 取货计划查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	PageList<PupPlanModel> queryPlanForPage(PupPlanPageModel model, Page page)throws Exception;
	/**
	 * 数据字典项下载状态加载业务层接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<DictVO> getDownloadStatus()throws Exception;
	/**
	 * 单条/批量删除数据业务接口
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void deletePlansById(String[] ids)throws Exception;
	
}
