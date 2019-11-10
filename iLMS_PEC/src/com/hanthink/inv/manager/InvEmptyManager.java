package com.hanthink.inv.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;
/**
 * <pre> 
 * 功能描述:空容器库存查询业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvEmptyManager extends Manager<String, InvEmptyModel>{
	/**
	 * 空容器分页查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	PageList<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page)throws Exception;
	/**
	 * 修改空容器数据信息业务接口
	 * @param model
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	void updateForEmpty(InvEmptyModel model, String ipAddr)throws Exception;
	/**
	 * 空容器库存查询导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	List<InvEmptyModel> exportForExcel(InvEmptyModel model) throws Exception;
	
	/**
	 * 根据UUID删除临时导入数据
	 * @param uuid
	 */
	void deleteInvEmptyImportTempDataByUUID(String uuid);
	
	/**
	 * 导入Excel数据
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @param user
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	Map<String, Object> importInvEmptyModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws IOException, Exception;
	
	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 */
	PageList<InvEmptyModel> queryInvEmptyImportTempData(Map<String, String> paramMap, Page page);
	
	/**
	 * 将临时数据写入正式表
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception 
	 */
	void insertInvEmptyImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 */
	List<InvEmptyModel> queryInvEmptyImportTempDataForExport(Map<String, String> paramMap);
}
