package com.hanthink.sps.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * <pre> 
 * 功能描述:SPS配置明细维护业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年11月8日下午4:43:41
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface SpsConfigDetailManager extends Manager<String, SpsConfigDetailModel>{
	/**
	 * 配置明细数据分页条件查询
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<SpsConfigDetailModel> queryConfigDetailsForPage(SpsConfigDetailModel model, Page page)throws Exception;
	/**
	 * 新增配置项数据信息
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	void createConfigDetails(SpsConfigDetailModel model)throws Exception;
	/**
	 * 修改配置项数据信息
	 * @param model
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 */
	void updateConfigDetails(SpsConfigDetailModel model, String ipAddr)throws Exception;
	/**
	 * 通过id逐条/批量删除配置项数据信息
	 * @param ids
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 */
	void removeConfigDetailsByIds(String[] ids,String ipAddr)throws Exception;
	/**
	 * 页面查询数据导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> exportQueryForExcel(SpsConfigDetailModel model)throws Exception;
	/**
	 * 根据uuid删除临时表数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 */
	void deleteTempCongfigByUUID(String uuid)throws Exception;
	/**
	 * 将Excel数据写入临时数据表
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Map<String, Object> importConfigDetailModel(MultipartFile file, String uuid, String ipAddr)throws Exception;
	/**
	 * 查询分页导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	PageList<SpsConfigDetailModel> queryImportForPage(String uuid, Page page)throws Exception;
	/**
	 * 将临时数据表信息写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 */
	void insertTempToFormal(Map<String, Object> paramMap)throws Exception;
	/**
	 * 导入数据详情Excel导出
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<SpsConfigDetailModel> exportForImport(String uuid)throws Exception;
	
	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

}
