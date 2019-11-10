package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:路线信息维护业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月21日下午11:08:56
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupRouteMessageManager extends Manager<String, PupRouteMessageModel>{
	/**
	 * 条件分页查询
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	PageList<PupRouteMessageModel> queryRouteMessageForPage(PupRouteMessageModel model,Page page)throws Exception;
	/**
	 * 加载数据字典选项
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<DictVO> getBatches()throws Exception;
	/**
	 * 导出数据查询
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	List<PupRouteMessageModel> queryRouteMessageForExport(PupRouteMessageModel model)throws Exception;
	/**
	 * 单个/多条记录删除
	 * @param ids
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj 
	 * @date 2018年9月21日
	 */
	void removeRouteMessagesByIds(String[] ids, String ipAddr)throws Exception;
	/**
	 * 根据UUID删除已存在数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void deleteTempRouteMessageByUUID(String uuid)throws Exception;
	/**
	 * 将数据导入临时数据表
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	Map<String, Object> importRouteMessageToTempTable(MultipartFile file,String uuid,String ipAddr)throws Exception;
	/**
	 * 将数据从临时表写入正式表
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void insertImportDataToFormalTable(Map<String, Object> paramMap,String ipAddr)throws Exception;
	/**
	 * 查询导入数据详情
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	PageList<PupRouteMessageModel> queryImportModelForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 导出导入数据详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月21日
	 */
	List<PupRouteMessageModel> queryImportModelForExport(Map<String, String> paramMap)throws Exception;
	/**
	 * 查询单条数据详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	PupRouteMessageModel queryRouteMessageById(String id)throws Exception;
	/**
	 * 修改数据
	 * @param id
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	void updateRouteMessageById(String id,PupRouteMessageModel model)throws Exception;

}
