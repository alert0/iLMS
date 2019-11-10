package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.MpAdjOrderDiffCheckModel;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：计划对比差异查询  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjOrderDiffCheckDao extends Dao<String, MpAdjOrderDiffCheckModel> {
	
	 /**
     * 分页查询供应商分组
     * @param model
     * @param p
     * @return
     */
    List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckForPage(MpAdjOrderDiffCheckModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckByKey(MpAdjOrderDiffCheckModel model);

	/**
	 * 生成USP_MP_ZSB_DIFF
	 * <p>return: Integer</p>  
	 * <p>Description: MpAdjOrderDiffCheckDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月15日
	 * @version 1.0
	 */
	Integer getMpZsbDiff(String curFactoryCode);

	/**
     * 清除计划对比差异
     * @param curFactoryCode
     */
    void clearOrderDiffData(String curFactoryCode);

    /**
     * 更新手工调整数
     * @param adjOrderDiffCheckModel
     */
    void updateManuNum(MpAdjOrderDiffCheckModel adjOrderDiffCheckModel);

    /**
     * 删除导入临时表数据
     * @param uuid
     */
    void deleteMpAdjOrderDiffImportTempDataByUUID(String uuid);

    /**
     * 写入计划对比调整差异数据到临时表
     * @param importList
     */
    void insertMpAdjOrderDiffImportTempData(List<MpAdjOrderDiffCheckModelImport> importList);

    /**
     * 校验导入数据
     * @param ckParamMap
     */
    void checkMpAdjOrderDiffImportData(Map<String, String> ckParamMap);

    /**
     * 查询是否可导入的标识
     * @param uuid
     * @return
     */
    String queryMpaAdjOrderIsImportFlag(String uuid);

    /**
     * 查询导入的临时表数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 更新导入数据
     * @param paramMap
     */
    void updateMpAdjOrderImportDate(Map<String, Object> paramMap);

    /**
     * 更新导入状态
     * @param paramMap
     */
    void updateImportDataImpStatus(Map<String, Object> paramMap);
    
    /**
     * 查询计划对比调整差异数据
     * @param paramMap
     * @return
     */
    List<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempDataForExport(Map<String, String> paramMap);

}
