package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvStockTakModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

/**
 * <pre> 
 * 功能描述:盘点信息业务持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvStockTakDao extends Dao<String, InvStockTakModel>{
	/**
	 * 分页盘点信息查询持久层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryStockTakForPage(InvStockTakModel model, Page page)throws Exception;
	/**
	 * Excel数据导出数据查询持久层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryDataForExcelExport(InvStockTakModel model)throws Exception;
	/**
	 * 根据UUID删除临时表数据持久层接口
	 * @param importUuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void deleteTempStockTakByUUID(String importUuid)throws Exception;
	/**
	 * 导入数据到临时数据表持久层接口
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void importStockTakToTemp(List<InvStockTakModel> importList)throws Exception;
	/**
	 * 调用存储过程校验数据持久层接口
	 * @param ckParamMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void checkImportData(Map<String, String> ckParamMap)throws Exception;
	/**
	 * 修改库存数据为实际库存
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void updateLocalStock(Map<String, String> paramMap)throws Exception;
	/**
	 * Excel导入数据查询持久层接口
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryImportTempForPage(String uuid,Page page)throws Exception;
	/**
	 * 判断数据是否全部正确
	 * @param paramMap
	 * @return
	 * @author zmj
	 * @date 2018年12月19日
	 */
	Boolean getCorrectOrNot(Map<String, String> paramMap)throws Exception;
	/**
	 * 查询可导入的数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryForInsertList(Map<String, String> paramMap)throws Exception;
	/**
	 * 计算差异写到临时数据表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void calDiffStock(Map<String, String> paramMap)throws Exception;
	/**
	 * 获取导入数据详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> querImportStock(Map<String, String> paramMap)throws Exception;
	/**
	 * 插入盘赢头信息
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void insertPWHeader(Map<String, String> paramMap)throws Exception;
	/**
	 * 插入盘亏头信息
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void insertPLHeader(Map<String, String> paramMap)throws Exception;
	/**
	 * 将临时数据表数据写入正式表(已存在的数据修改，不存在的数据新增到正式表)
	 * @param list
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void insertStockTakToFormal(List<InvStockTakModel> list)throws Exception;
	/**
	 * 查询盘点时修改的库存数据的ID
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryUpdateIds(Map<String, String> paramMap)throws Exception;
	/**
	 * 修改数据的导入状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void updateImportStatus(Map<String, String> paramMap)throws Exception;
}
