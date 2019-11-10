package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvDevelopModel;
import com.hanthink.inv.model.InvLockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:库存推移查询业务持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvDevelopDao extends Dao<String, InvDevelopModel>{
	/**
	 * 库存推移分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> queryDevelopForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 库存推移查询数据导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> queryForExcelExport(InvDevelopModel model)throws Exception;
	/**
	 * 零件消耗量查询业务持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> queryConsumptionForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 零件消耗量Excel导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> queryConsumptionForExcelExport(InvDevelopModel model)throws Exception;
	/**
	 * 库存推移数据分页查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> queryDevelopManagerForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 库存推移管理页面数据导出持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	List<InvDevelopModel> exportExcelForDevelopManager(InvDevelopModel model)throws Exception;
	/**
	 * 修改库存数据信息持久层接口
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	void updateStock(InvDevelopModel model)throws Exception;
	/**
	 * 获取库存数据持久层接口
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	void getStock(Map<String, String> paramMap)throws Exception;
	/**
	 * 根据uuid删除导入临时数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年11月6日
	 */
	void deleteImportByUUID(String uuid)throws Exception;
	/**
	 * Excel数据写入临时表
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	void insertExcelToTemp(List<InvDevelopModel> importList)throws Exception;
	/**
	 * 调用存储校验数据
	 * @param checkMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年11月6日
	 */
	void checkImportStock(Map<String, String> checkMap)throws Exception;
	/**
	 * 检查是否可以批量导入
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年11月6日
	 */
	Integer queryImportFlag(Map<String, Object> paramMap)throws Exception;
	
	List<InvDevelopModel> queryImportForPage(Page page,String uuid)throws Exception;
	
	void insertTempToFormal(Map<String, Object> paramMap)throws Exception;
	
	void updateImportStatus(Map<String, Object> paramMap)throws Exception;
	List<InvDevelopModel> exportExcelForImport(String uuid)throws Exception;
	InvLockModel queryRunStatus(String factoryCode)throws Exception;
	void elapseUpdate(InvLockModel model)throws Exception;
	void elapseUpdateFirst(InvLockModel model)throws Exception;
	/**
	 * 查询零件消耗日用量
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年1月11日
	 */
	List<InvDevelopModel> queryDailyConsumption(InvDevelopModel model, Page page);
	/**
	 * 查询零件消耗日用量详细查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年1月15日
	 */
	List<InvDevelopModel> queryDailyConsumptionDetail(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 获取车间下的仓库信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月29日
	 */
	List<String> queryWareForWorkcenter(Map<String, Object> paramMap)throws Exception;
	/**
	 * 获取仓库与零件的关系
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月29日
	 */
	InvDevelopModel queryWareListForStock(Map<String, Object> paramMap)throws Exception;
}
