package com.hanthink.mp.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface ExcepOrderDao extends Dao<String, ExcepOrderModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    List<ExcepOrderModel> queryExcepOrderForPage(ExcepOrderModel model, DefaultPage p);

    /**
     * 根据UUID删除临时表数据
     * @param uuid
     */
    void deleteImportTempDataByUUID(String uuid);

    /**
     * 将EXCEL数据导入到例外订单需求临时表
     * @param importList
     */
    void insertImportTempData(List<ExcepOrderModelImport> importList);

    /**
     * 校验例外需求导入数据
     * @param ckParamMap
     */
    void checkImportData(Map<String, String> ckParamMap);

    /**
     * 查询导入的临时例外订单用量数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<ExcepOrderModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 降临时表数据写入正式表
     * @param paramMap
     */
    void insertImportData(Map<String, String> paramMap);

    /**
     * 更新临时数据导入状态
     * @param paramMap
     */
    void updateImportDataImpStatus(Map<String, String> paramMap);

    /**
     * 导入之前先删除正式表里面处理标识为未处理的数据
     * @param paramMap
     */
    void deleteNotDealData(Map<String, String> paramMap);

    /**
     * 查询是否可以批量导入
     * @param uuid
     * @return
     */
    String queryIsImportFlag(String uuid);

    /**
     * 导出查询的方法
     * <p>return: List<ExcepOrderModel></p>  
     * <p>Description: ExcepOrderDao.java</p>  
     * @author linzhuo  
     * @date 2018年9月29日
     * @version 1.0
     */
	List<ExcepOrderModel> queryExcepOrderByKey(ExcepOrderModel model);

	/**
	 * 导出临时数据信息
	 * <p>return: List<ExcepOrderModelImport></p>  
	 * <p>Description: ExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<ExcepOrderModelImport> queryExcepOrderModelImportTempDataForExport(Map<String, String> paramMap);

	/**
	 * 查询可导入的数据
	 * <p>return: List<ExcepOrderModel></p>  
	 * <p>Description: ExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<ExcepOrderModelImport> queryForInsertList(Map<String, String> paramMap)throws Exception;
    
}
