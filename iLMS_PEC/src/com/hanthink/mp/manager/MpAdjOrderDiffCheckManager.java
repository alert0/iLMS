package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mp.model.MpAdjOrderDiffCheckModel;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * <pre> 
 * 描述：计划对比差异查询 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjOrderDiffCheckManager extends Manager<String, MpAdjOrderDiffCheckModel>{

	 /**
     * 分页查询计划对比差异查询
     * @param model
     * @param p
     * @return
     */
    PageList<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckForPage(MpAdjOrderDiffCheckModel model, DefaultPage p);
	
	/**
	 * 导出计划对比差异查询
	 * @param model
	 * @return
	 */
	List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckByKey(MpAdjOrderDiffCheckModel model);

	/**
	 * 生成USP_MP_ZSB_DIFF
	 * <p>return: Integer</p>  
	 * <p>Description: MpAdjOrderDiffCheckManager.java</p>  
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
     * 更新手工调整数量
     * @param adjOrderDiffCheckModel
     */
    void updateManuNum(MpAdjOrderDiffCheckModel adjOrderDiffCheckModel);

    /**
     * 删除临时表数据
     * @param uuid
     */
    void deleteMpAdjOrderDiffImportTempDataByUUID(String uuid);

    /**
     * 导入EXCEL数据到临时表
     * @param file
     * @param uuid
     * @param ipAddr
     * @param user
     * @return
     */
    Map<String, Object> importMpAdjOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception;

    /**
     * 查询导入的临时表数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 将临时表数据导入到正式表
     * @param paramMap
     * @param ipAddr
     */
    void insertMpAdjOrderImportData(Map<String, Object> paramMap, String ipAddr);
    
    /**
     * 查询导入的临时表数据
     * @param paramMap
     * @return
     */
    List<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempDataForExport(Map<String, String> paramMap);

}
