package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.MpSupplierSortModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
 * 
 * @Desc    : 例外订单接口
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface ExcepOrderManager extends Manager<String, ExcepOrderModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    PageList<ExcepOrderModel> queryExcepOrderForPage(ExcepOrderModel model, DefaultPage p);

    /**
     * 根据UUID删除导入临时表数据
     * @param uuid
     */
    void deleteImportTempDataByUUID(String uuid);

    /**
     * 导入临时表,导入校验
     * @param file
     * @param uuid
     * @param ipAddr
     * @return
     * @throws Exception 
     */
    Map<String, Object> importDemoModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception;

    /**
     * 查询导入的临时例外订单用量数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<ExcepOrderModelImport> queryImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 将临时例外订单需求数据导入正式表
     * @param paramMap
     * @throws Exception 
     */
    void insertImportData(Map<String, String> paramMap) throws Exception;

    /**
	 * 导出例外订单需求
	 * @param model
	 * @return
	 */
	List<ExcepOrderModel> queryExcepOrderByKey(ExcepOrderModel model);

	/**
	 * 导出临时数据信息
	 * <p>return: List<ExcepOrderModelImport></p>  
	 * <p>Description: ExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<ExcepOrderModelImport> queryExcepOrderModelImportTempDataForExport(Map<String, String> paramMap);
}
