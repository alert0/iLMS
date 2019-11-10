package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupManualOrderModel;
import com.hanthink.pup.model.PupManualOrderPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:手工调整订单业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupManualOrderManager extends Manager<String, PupManualOrderModel>{
	/**
	 * 手工调整订单查询业务业务层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	PageList<PupManualOrderModel> queryManualOrderForPage(PupManualOrderPageModel model,Page page)throws Exception;
	/**
	 * 手工调整订单删除数据业务层接口
	 * @param purchaseNo
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	void removeManualOders(String[] purchaseNo)throws Exception;
	/**
	 * 手工调整订单页面数据导出业务层接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	List<PupManualOrderModel> queryManualOederForExport(PupManualOrderPageModel model)throws Exception;
	/**
	 * 根据UUID删除临时表数据业务层接口
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void deleteTempManualOrderByUUID(String uuid)throws Exception;
	/**
	 * 将数据写入临时表业务层接口
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	Map<String, Object> importManualOrderToTempTable(MultipartFile file,String uuid,String ipAddr)throws Exception;
	/**
	 * 查询手工调整订单导入数据业务层接口
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	PageList<PupManualOrderModel> queryImportManualOrderForPage(Map<String, String> paramMap,Page page)throws Exception;
	/**
	 * 将临时表数据写入正式业务表业务层接口
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void insertImportDataToFormalTable(Map<String, Object> paramMap,String ipAddr)throws Exception;
	/**
	 * 将导入数据导出到Excel表格业务层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<PupManualOrderModel> queryManualOrderTempDataForExport(Map<String, String> paramMap)throws Exception;
}
