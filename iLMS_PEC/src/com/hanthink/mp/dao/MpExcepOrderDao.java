package com.hanthink.mp.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpExcepOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface MpExcepOrderDao extends Dao<String, MpExcepOrderModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    List<MpExcepOrderModel> queryMpExcepOrderForPage(MpExcepOrderModel model, DefaultPage p);

    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderDao.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
	Integer generateMpExcepOrder(String curFactoryCode);

	/**
	 * 例外订单发布
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpExcepOrder(String curFactoryCode,String opeId);

	/**
	 * 导出查询的方法
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0
	 */
	List<MpExcepOrderModel> queryMpExcepOrderByKey(MpExcepOrderModel model);
    
	/**
     * 根据UUID删除临时表数据
     * @param uuid
     */
    void deleteImportTempDataByUUID(String uuid);

    /**
     * 将EXCEL数据导入到例外订单需求临时表
     * @param importList
     */
    void insertImportTempData(List<MpExcepOrderModel> importList);

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
    PageList<MpExcepOrderModel> queryImportTempData(Map<String, String> paramMap, Page page);

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
	 * 导出临时数据信息
	 * <p>return: List<MpExcepOrderModel></p>  
	 * <p>Description: ExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpExcepOrderModel> queryMpExcepOrderModelTempDataForExport(Map<String, String> paramMap);

	/**
	 * 查询可导入的数据
	 * <p>return: List<ExcepOrderModel></p>  
	 * <p>Description: ExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpExcepOrderModel> queryForInsertList(Map<String, String> paramMap)throws Exception;

	/**
	 * 根据车间取仓库代码
	 * <p>return: String</p>  
	 * <p>Description: MpExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月3日
	 * @version 1.0
	 */
	String selectStorageByWorkCenter(MpExcepOrderModel mpExcepOrderModel);

	/**
	 * 批量删除数据
	 * <p>return: void</p>  
	 * <p>Description: MpVehPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月30日
	 * @version 1.0
	 * @throws Exception 
	 */
	void deleteByIds(String[] aryIds) throws Exception;

	/**
	 * 查询是否有已订购数据
	 * <p>return: Integer</p>  
	 * <p>Description: MpExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月4日
	 * @version 1.0
	 */
	Integer queryMpExcepOrderCheck(List<String> listIds);

	/**
	 * 获取到货仓库填充下拉框
	 * <p>return: List<DictVO></p>  
	 * <p>Description: MpExcepOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月22日
	 * @version 1.0
	 */
	List<DictVO> getInvWareHouse();
	
}
