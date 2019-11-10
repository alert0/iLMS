package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 
 * <pre> 
 * 描述：订单履历 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpOrderRecordHisManager extends Manager<String, MpOrderRecordHisModel>{

	 /**
     * 分页查询订单履历
     * @param model
     * @param p
     * @return
     */
    PageList<MpOrderRecordHisModel> queryMpOrderRecordHisForPage(MpOrderRecordHisModel model, DefaultPage p);
	
	/**
	 * 导出订单履历
	 * @param model
	 * @return
	 */
	List<MpOrderRecordHisModel> queryMpOrderRecordHisByKey(MpOrderRecordHisModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @return
	 */
	List<DictVO> getUnloadPort();
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(MpOrderRecordHisModel mpOrderRecordHisModel, String opeIp);

	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午10:27:10
	 */
	Map<String, Object> importMpOrderRecordHisModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception;

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:51:23
	 */
	PageList<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 下午12:20:34
	 */
	void insertMpOrderRecordHisImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:24
	 */
	void deleteMpOrderRecordHisImportTempDataByUUID(String uuid);

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	List<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempDataForExport(Map<String, String> paramMap);
	
	
}
