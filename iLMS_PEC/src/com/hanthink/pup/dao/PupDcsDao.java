package com.hanthink.pup.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.pup.model.PupDcsModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : DCS处理DAO
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PupDcsDao extends Dao<String, PupDcsModel> {

    /**
     * 查询DCS单数据
     * @param model
     * @param p
     * @return
     */
    PageList<PupDcsModel> queryPupDcsForPage(PupDcsModel model, DefaultPage p);

    /**
     * 查询DCS数据用于导出
     * @param model
     * @return
     */
    List<PupDcsModel> queryPupDcsByKey(PupDcsModel model);

    /**
     * 生成DCS
     * @param m
     * @return
     */
    String genDcs(Map<String, Object> m);

    /**
     * 删除临时导入的DCS数据
     * @param uuid
     */
    void deleteDcsByUUID(String uuid);

    /**
     * 将数据导入临时表
     * @param importList
     */
    void insertImportTempData(List<PupDcsModelImport> importList);

    /**
     * DCS导入校验
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
    PageList<PupDcsModelImport> queryPupDcsImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 生成DCS数据
     * @param paramMap
     */
    String insertPupDcsImportData(Map<String, Object> paramMap);

    /**
     * 查询DCS数据
     * @param pupDcsModel
     * @return
     */
    List<PupDcsModel> queryDcsForListToPrint(PupDcsModel pupDcsModel);

    /**
     * 查询DCS明细数据
     * @param pupDcsModel
     * @return
     */
    List<PupDcsModel> queryDcsDetailForList(PupDcsModel pupDcsModel);

    /**
     * 查询所有的未使用的封条号
     * @param pupDcsModel
     * @return
     */
    List<PupDcsModel> querySealForList(PupDcsModel pupDcsModel);

    /**
     * 查询订单信息
     * @param detailVo
     * @return
     */
    List<PupDcsModel> queryOrderForList(PupDcsModel detailVo);

    /**
     * 将封条号写入临时表
     * @param sealList
     */
    void insertSealTemp(List<PupDcsModel> sealList);

    /**
     * 更新封条号的状态
     * @param sealList
     */
    void updateSealStatus(List<PupDcsModel> sealList);

    /**
     * 更新DCS打印状态
     * @param pupDcsModel
     */
    void updateDcsPrintStatus(PupDcsModel pupDcsModel);

    /**
     * 查询该DCS任务的取货车牌号
     * @param pupDcsModel
     * @return
     */
    String queryPlateNumByPlanSheetNo(PupDcsModel pupDcsModel);

    /**
     * 更新DCS取货车牌号
     * @param pupDcsModel
     */
    void updaetDcsPlateNum(PupDcsModel pupDcsModel);

    /**
     * 查询已经打印过的DCS封条号
     * @param vo
     * @return
     */
    List<PupDcsModel> queryPrintedSeals(PupDcsModel vo);

    /**
     * 查询DCS明细数据于导出
     * @param model
     * @return
     */
    List<PupDcsModel> queryPupDcsDetailByKey(PupDcsModel model);

    /**
     * 更改DCS任务状态
     * @param pupDcsModel
     */
    void updateDcsExecuteStatus(PupDcsModel pupDcsModel);
	
}
