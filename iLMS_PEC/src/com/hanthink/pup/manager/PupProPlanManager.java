package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

public interface PupProPlanManager extends Manager<String, PupProPlanModel>{
	/**
	 *生产计划数据查询
	 *@author zmj
	 *@param plan 查询条件VO
	 *@param page 页面信息
	 *@return JSON格式计划订单详情
	 *@throws Exception
	 *@date 2018年9月15日 下午12:27:16
	 */
	PageList<PupProPlanModel> queryProPlanForPage(PupProPlanModel plan,Page page) throws Exception;
	/**
	 * 生产计划导出数据查询
	 *@author zmj
	 *@param plan 查询条件VO
	 *@return list
	 *@throws Exception
	 *@date 2018年9月15日 下午12:28:57
	 */
	List<PupProPlanModel> queryPupPlanBykey(PupProPlanModel plan) throws Exception;
	/**
	 * 通过UUID删除导入数据
	 *@author zmj
	 *@param uuid
	 *@throws Exception
	 *@date 2018年9月15日 下午12:30:07
	 */
	void deleteProPlanImportTempDataByUUID(String uuid) throws Exception;
	/** 
	 * 导入数据
	 *@author zmj
	 *@param file Excel文件
	 *@param uuid UUID
	 *@param ipAddr ip地址
	 *@param user 用户名称
	 *@return 数据集合
	 *@throws Exception
	 *@date 2018年9月15日 下午12:30:21
	 */
	Map<String, Object> importProPlanModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception;
	/**
	 * 功能描述:查询导入的数据信息
	 * 作者:zmj
	 * 日期:2018年9月15日 下午13:00:33
	 * 版权:汉思信息技术有限公司
	 */
	PageList<PupProPlanModel> queryImportInformationForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 确定导入，将临时表数据导入正式表
	 *@author zmj
	 *@param paramMap
	 *@param ipAdrr
	 *@throws Exception
	 *@date 2018年9月17日 上午9:46:19
	 */
	void insertTempDataToPlanTable(Map<String, String> paramMap) throws Exception;
	/**
	 * 导入数据导出
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月17日
	 */
	List<PupProPlanModel> queryImportInformationForExport(String uuid)throws Exception;
	/**
	 * 获取生产计划
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月17日
	 */
	void getProPlan(PupProPlanModel model)throws Exception;
	/**
	 * 查询时间输入框默认值
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月13日
	 */
	PupProPlanModel querySearchTimes()throws Exception;
}
