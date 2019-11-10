package com.hanthink.pup.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupDcsSealModel;
import com.hanthink.pup.model.PupDcsSealModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : DCS封条号处理DAO
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PupDcsSealDao extends Dao<String, PupDcsSealModel> {

    /**
     * 分页查询封条号数据
     * @param model
     * @param p
     * @return
     */
    PageList<PupDcsSealModel> queryPupDcsSealForPage(PupDcsSealModel model, DefaultPage p);

    /**
     * 查询DCS封条号用于导出
     * @param model
     * @return
     */
    List<PupDcsSealModel> queryPupDcsSealByKey(PupDcsSealModel model);

    /**
     * 删除所有未使用的封条号数据
     * @param curFactoryCode
     * @param ipAddr
     */
    void batchDelDcsSeal(String curFactoryCode);

    /**
     * 根据UUID删除临时DCS数据
     * @param uuid
     */
    void deleteDcsSealByUUID(String uuid);

    /**
     * 将EXCEL数据导入临时表
     * @param importList
     */
    void insertImportTempData(List<PupDcsSealModelImport> importList);

    /**
     * 校验导入的临时数据
     * @param ckParamMap
     */
    void checkImportData(Map<String, String> ckParamMap);

    /**
     * 查询是否可导入
     * @param uuid
     * @return
     */
    String queryIsImportFlag(String uuid);

    /**
     * 查询导入的临时表数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<PupDcsSealModelImport> queryPupDcsSealImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 查询新增的列表
     * @param paramMap
     * @return
     */
    List<PupDcsSealModelImport> queryForInsertList(Map<String, Object> paramMap);

    /**
     * 先删除该工厂的未使用的数据
     * @param paramMap
     */
    void deleteNotUseDcsSeal(Map<String, Object> paramMap);

    /**
     * 从临时表查询导入数据写入到正式表
     * @param paramMap
     */
    void insertPupDcsSealFromTemp(Map<String, Object> paramMap);

    /**
     * 导入成功，修改导入状态
     * @param paramMap
     */
    void updateImportStatus(Map<String, Object> paramMap);
	
}
