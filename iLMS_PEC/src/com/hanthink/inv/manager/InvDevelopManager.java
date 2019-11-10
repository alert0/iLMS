package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvDevelopModel;
import com.hanthink.inv.model.InvLockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:库存推移查询业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvDevelopManager extends Manager<String, InvDevelopModel>{
	/**
	 * 库存推移分页查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<InvDevelopModel> queryDevelopForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 库存推移查询数据导出业务接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvDevelopModel> queryForExcelExport(InvDevelopModel model)throws Exception;
	/**
	 * 零件消耗量查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<InvDevelopModel> queryConsumptionForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 零件消耗量Excel导出业务接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvDevelopModel> queryConsumptionForExcelExport(InvDevelopModel model)throws Exception;
	/**
	 * 库存推移数据分页查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<InvDevelopModel> queryDevelopManagerForPage(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 库存推移数据查询导出业务层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvDevelopModel> exportExcelForDevelopManager(InvDevelopModel model)throws Exception;
	/**
	 * 修改库存信息数据业务接口
	 * @param model
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 */
	void updateStock(InvDevelopModel model, String ipAddr)throws Exception;
	/**
	 * 获取库存数据业务接口
	 * @throws Exception
	 * @author zmj
	 */
	void getStock() throws Exception;
	/**
	 * 根据uuid删除临时表数据业务接口
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 */
	void deleteImportByUUID(String uuid)throws Exception;
	/**
	 * Excel数据导入临时表业务接口
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Map<String, Object> importStockToTempTable(MultipartFile file, String uuid, String ipAddr)throws Exception;
	/**
	 * Excel数据导入查询业务接口
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<InvDevelopModel> queryImportForPage(Page page,String uuid)throws Exception;
	/**
	 * 确认导入,将临时数据表数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 */
	void isImport(Map<String, Object> paramMap)throws Exception;
	/**
	 * Excel导入数据导出详情
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvDevelopModel> exportExcelForImport(String uuid)throws Exception;
	/**
	 *  查询推算服务的运行状态
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	InvLockModel queryRunStatus()throws Exception;
	/**
	 * 修改推算服务的运行状态
	 * @throws Exception
	 * @author zmj
	 */
	void elapseUpdate(Integer state)throws Exception;
	/**
	 * 查询零件日消耗量
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月11日
	 */
	PageList<InvDevelopModel> queryDailyConsumption(InvDevelopModel model, Page page)throws Exception;
	/**
	 * 查询零件日消耗量详情
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月15日
	 */
	PageList<InvDevelopModel> queryDailyConsumptionDetail(InvDevelopModel model, Page page)throws Exception;

}
