package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpExcepOrderModel;
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
public interface MpExcepOrderManager extends Manager<String, MpExcepOrderModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    PageList<MpExcepOrderModel> queryMpExcepOrderForPage(MpExcepOrderModel model, DefaultPage p);

    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderManager.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
    Integer generateMpExcepOrder(String curFactoryCode);
    
	/**
	 * 例外订单发布
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpExcepOrder(String curFactoryCode,String opeId);

	/**
	 * 导出例外订单需求
	 * @param model
	 * @return
	 */
	List<MpExcepOrderModel> queryMpExcepOrderByKey(MpExcepOrderModel model);
	
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
    Map<String, Object> importExcepOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception;

    /**
     * 查询导入的临时例外订单用量数据
     * @param paramMap
     * @param page
     * @return
     */
    PageList<MpExcepOrderModel> queryImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 将临时例外订单需求数据导入正式表
     * @param paramMap
     * @throws Exception 
     */
    void insertImportData(Map<String, String> paramMap) throws Exception;

	/**
	 * 导出临时数据信息
	 * <p>return: List<MpExcepOrderModel></p>  
	 * <p>Description: ExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpExcepOrderModel> queryMpExcepOrderModelTempDataForExport(Map<String, String> paramMap);

	/**
	 * 根据车间取到货仓库
	 * <p>return: String</p>  
	 * <p>Description: MpExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月3日
	 * @version 1.0
	 */
	String selectStorageByWorkCenter(MpExcepOrderModel mpExcepOrderModel);

	/**
	 * 校验是否包含已订购数据
	 * <p>return: Integer</p>  
	 * <p>Description: MpExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月4日
	 * @version 1.0
	 */
	Integer queryMpExcepOrderCheck(List<String> listIds);

	/**
	 * 批量删除并记录日志
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月4日
	 * @version 1.0
	 * @throws Exception 
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;

	/**
	 * 获取到货仓库下拉框
	 * <p>return: List<DictVO></p>  
	 * <p>Description: MpExcepOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月22日
	 * @version 1.0
	 */
	List<DictVO> getInvWareHouse();
	
}
