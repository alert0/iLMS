package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvStockTakModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:盘点信息管理业务层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvStockTakManager extends Manager<String, InvStockTakModel>{
	/**
	 * 分页数据查询业务层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	PageList<InvStockTakModel> queryStockTakForPage(InvStockTakModel model, Page page)throws Exception;
	/**
	 * Excel数据导出查询业务接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	List<InvStockTakModel> queryDataForExcelExport(InvStockTakModel model)throws Exception;
	/**
	 * 根据UUID删除临时表数据
	 * @param importUuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void deleteTempStockTakByUUID(String importUuid)throws Exception;
	/**
	 * 将数据导入临时数据表
	 * @param file
	 * @param importUuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	Map<String, Object> importStockTakToTemp(MultipartFile file, String importUuid, String ipAddr)throws Exception;
	/**
	 * Excel数据导入查询
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	PageList<InvStockTakModel> queryImportTempForPage(String uuid,Page page)throws Exception;
	/**
	 * 确定将临时表数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	void isImport(Map<String, String> paramMap)throws Exception;
	/**
	 * 导入数据详情Excel导出
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	PageList<InvStockTakModel> queryImportTempForExport(String uuid,Page page)throws Exception;

}
