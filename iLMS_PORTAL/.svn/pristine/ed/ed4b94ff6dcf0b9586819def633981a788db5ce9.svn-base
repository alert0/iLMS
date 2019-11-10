package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupPickTimeModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
* <pre> 
* 描述：固定取货时间维护服务层接口类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17
* 版权：汉思信息技术有限公司
* </pre>
*/
public interface PupPickTimeManager extends Manager<String,PupPickTimeModel>{
	/**
	 * 查询固定取货时间维护详细信息
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 上午10:07:12
	 */
	PageList<PupPickTimeModel> queryPickupTimeForPage(PupPickTimeModel model,Page page) throws Exception;
	
	/**
	 * 根据集货路线获取固定取货时间详情
	 *@param routeCode
	 *@return model
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 上午11:40:20
	 */
	PupPickTimeModel getPickTimeByRouteCode(String routeCode)throws Exception;
	/**
	 * 新增固定取货时间信息
	 *@param model
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午12:00:45
	 */
	void savePickTime(String flag,PupPickTimeModel model)throws Exception;
	/**
	 * 删除固定取货事件信息
	 *@param routeCodes
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月21日 上午11:12:33
	 */
	void removeByRouteCodes(String[] routeCodes)throws Exception;
	/**
	 * 为导出功能查询页面数据
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 上午10:37:01
	 */
	List<PupPickTimeModel> queryPickupTimeForExport(PupPickTimeModel model)throws Exception;
	/**
	 * 查询导入的临时数据
	 *@param param
	 *@param page
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午12:08:26
	 */
	PageList<PupPickTimeModel> queryPickTimeForImport(Map<String, String> param,Page page)throws Exception;
	/**
	 * 根据UUID删除临时表已存在的数据
	 *@param uuid
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午12:26:13
	 */
	void deletePickTimeTempDataByUUID(String uuid)throws Exception;
	/**
	 * 将数据导入临时表
	 *@param file
	 *@param uuid
	 *@param ipAddr
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午12:28:12
	 */
	Map<String, Object> importDataToTempTable(MultipartFile file,String uuid,String ipAddr,IUser user)throws Exception;
	/**
	 * 导出查询页面数据
	 * @param param
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	List<PupPickTimeModel> queryPickupTimeTempDataForExport(Map<String, String> param)throws Exception;
	/**
	 * 将临时表数据写入正式表
	 * @param param
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	void insertImportDataToFormalTable(Map<String, Object> param,String ipAddr)throws Exception;
}
