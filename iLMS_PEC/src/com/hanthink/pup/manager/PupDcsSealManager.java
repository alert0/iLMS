package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupDcsSealModel;
import com.hanthink.pup.model.PupDcsSealModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * @Desc    : DCS封条号管理业务处理
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PupDcsSealManager extends Manager<String, PupDcsSealModel>{

    /**
     * 分页查询DCS封条号数据
     * @param model
     * @param p
     * @return
     */
    PageList<PupDcsSealModel> queryPupDcsSealForPage(PupDcsSealModel model, DefaultPage p);

    /**
     * 查询DCS封条号数据用于导出
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
     * @param file
     * @param uuid
     * @param ipAddr
     * @return
     */
    Map<String, Object> importDcsSealToTempTable(MultipartFile file, String uuid, String ipAddr, IUser user);

    /**
     * 查询导入的临时表DCS封条号数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<PupDcsSealModelImport> queryPupDcsSealImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 确定导入,将临时表数据导入正式表
     * @param paramMap
     * @param ipAddr
     */
    void insertPupDcsSealImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;
    
}
