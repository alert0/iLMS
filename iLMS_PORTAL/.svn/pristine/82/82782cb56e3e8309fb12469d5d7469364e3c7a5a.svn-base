package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.model.PupVersionPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:版本比对业务持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日下午11:20:01
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupVersionGapDao extends Dao<String, PupVersionModel>{
	/**
	 * 订单数据差异分页查询持久层接口
	 * @param pageModel
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<PupVersionModel> queryVersionGapForPage(PupVersionPageModel pageModel, Page page);
	/**
	 * 加载数据字典差异标识持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<DictVO> getDiffFlag(Map<String, Object> paramMap)throws Exception;
	/**
	 * 版本比对数据导出持久层接口
	 * @param pageModel
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<PupVersionModel> exportVersionGapForQuery(PupVersionPageModel pageModel)throws Exception;
	/**
	 * 版本数据导入持久层接口
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	void importVersion(List<PupLockPlanModel> importList)throws Exception;

}
