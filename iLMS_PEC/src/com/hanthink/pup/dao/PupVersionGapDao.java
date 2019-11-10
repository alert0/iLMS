package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupVersionModel;
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
	 * 检查是否生成物流计划业务层接口
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	Integer checkIsPlan(String factoryCode)throws Exception;
	/**
	 * 检查是否导入上一版本物流计划持久层接口
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	Integer checkIsPrePlan(String factoryCode)throws Exception;
	/**
	 * 订单数据差异分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<PupVersionModel> queryVersionGapForPage(PupVersionModel model, Page page);
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
	List<PupVersionModel> exportVersionGapForQuery(PupVersionModel model)throws Exception;
	/**
	 * 删除上一版本的数据
	 * @param curFactoryCode
	 * @throws Exception
	 * @author zmj
	 */
	void deleteOldVersion(String curFactoryCode)throws Exception;
	/**
	 * 将数据写入数据表
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 */
	void insertVersionToTable(List<PupVersionModel> importList)throws Exception;
	
	List<PupVersionModel> queryforVesion(String factoryCode)throws Exception;
	List<PupVersionModel> queryOneVersion(PupVersionModel model, Page page)throws Exception;
	List<PupVersionModel> queryTwoVersion(Map<String, String> paramMap, Page page)throws Exception;
	void logVersionGap(List<PupVersionModel> importList)throws Exception;
}
