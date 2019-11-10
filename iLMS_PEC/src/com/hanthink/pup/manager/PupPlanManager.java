package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * <pre> 
 * 功能描述:取货计划查询业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupPlanManager extends Manager<String, PupPlanModel>{
	/**
	 * 取货计划查询业务接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	PageList<PupPlanModel> queryPlanForPage(PupPlanModel model, Page page)throws Exception;
	/**
	 * 数据字典项下载状态加载业务层接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<DictVO> getDownloadStatus()throws Exception;
	/**
	 * 单条/批量删除数据业务接口
	 * @param orderNos
	 * @param purchaseNos
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void deletePlans(String[] orderNos,String[] purchaseNos,String ipAddr)throws Exception;
	/**
	 * 根据导入UUID删除临时表已存在数据业务接口
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void deletePlanByUUID(String uuid)throws Exception;
	/**
	 * 将Excel数据写入临时表业务接口
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	Map<String, Object> importPlanToTempTable(MultipartFile file, String uuid, String ipAddr)throws Exception;
	/**
	 * 查询到诶数据详情业务接口
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	PageList<PupPlanModel> queryImportForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 确认导入功能接口
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	void insertTempToFormal(Map<String, Object> paramMap, String ipAddr)throws Exception;
	/**
	 * 临时数据Excel导出业务接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	List<PupPlanModel> queryImportForExport(Map<String, String> paramMap)throws Exception;
	/**
	 * 查询数据导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月14日
	 */
	List<PupPlanModel> queryPlanForExport(PupPlanModel model)throws Exception;
	/**
	 * 已发车查询
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	PageList<PupPlanModel> queryForDepEnq(PupPlanModel model, Page page)throws Exception;
	/**
	 * 发车状态导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<PupPlanModel> queryForExportDepEnq(PupPlanModel model)throws Exception;
	
}
