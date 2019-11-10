package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.pup.model.PupDcsModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * @Desc    : DCS管理业务处理
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PupDcsManager extends Manager<String, PupDcsModel>{

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
     * 生成DCS任务
     * @param pupDcsModel
     */
    String genDcs(PupDcsModel pupDcsModel);

    /**
     * 根据UUID删除临时表数据
     * @param uuid
     */
    void deleteDcsByUUID(String uuid);

    /**
     * 将DCS临时数据导入正式表
     * @param file
     * @param uuid
     * @param ipAddr
     * @param user
     * @return
     */
    Map<String, Object> importDcsToTempTable(MultipartFile file, String uuid, String ipAddr, IUser user);

    /**
     * 查询导入的临时数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<PupDcsModelImport> queryPupDcsImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 将导入的临时数据生成DCS
     * @param paramMap
     * @param ipAddr
     */
    String insertPupDcsImportData(Map<String, Object> paramMap, String ipAddr);

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
     * 查询所有未使用的封条号
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
     * 更新DCS订单的打印状态
     * @param pupDcsModel
     */
    void updateDcsPrintStatus(PupDcsModel pupDcsModel);

    /**
     * 更新DCS的取货车牌号
     * @param pupDcsModel
     * @param ipAddr
     */
    void updaetDcsPlateNum(PupDcsModel pupDcsModel, String ipAddr);

    /**
     * 判断该DCS任务号是否有取货车牌号
     * @param pupDcsModel
     * @return
     */
    String queryPlateNumByPlanSheetNo(PupDcsModel pupDcsModel);

    /**
     * 查询打印过的封条号
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
    
}
