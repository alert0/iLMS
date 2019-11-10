package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvStockTakDao;
import com.hanthink.inv.model.InvStockTakModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * <pre> 
 * 功能描述:盘点信息业务持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvStockTakDaoImpl extends MyBatisDaoImpl<String, InvStockTakModel>
				implements InvStockTakDao{

	@Override
	public String getNamespace() {
		return InvStockTakModel.class.getName();
	}
	
	/**
	 * 分页盘点信息查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> queryStockTakForPage(InvStockTakModel model, Page page) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryStockTakForPage", model, page);
	}
	/**
	 * Excel数据导出数据查询持久实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> queryDataForExcelExport(InvStockTakModel model) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryStockTakForPage", model);
	}
	/**
	 * 根据UUID删除临时表数据持久层实现方法
	 * @param importUuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void deleteTempStockTakByUUID(String importUuid) throws Exception {
		this.deleteByKey("deleteTempStockTakByUUID", importUuid);
	}
	/**
	 * 导入数据到临时数据表持久层实现方法
	 * @param importList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void importStockTakToTemp(List<InvStockTakModel> importList) throws Exception {
		this.insertByKey("importStockTakToTemp", importList);
	}
	/**
	 * 调用存储过程校验数据持久层实现方法
	 * @param ckParamMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkImportStockTakData", ckParamMap);
	}

	/**
	 * 修改库存数据为实际库存
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void updateLocalStock(Map<String, String> paramMap) throws Exception {
		this.updateByKey("updateLocalStock", paramMap);
	}
	/**
	 * Excel导入数据查询持久层实现方法
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> queryImportTempForPage(String uuid,Page page) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryImportTempForPage", uuid, page);
	}
	@Override
	public Boolean getCorrectOrNot(Map<String, String> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCorrectOrNot", paramMap);
	}
	/**
	 * 查询可导入的数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> queryForInsertList(Map<String, String> paramMap) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryForInsertList", paramMap);
	}
	/**
	 * 将数据信息写入主表(盘点单号、操作人信息、时间)
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void insertPWHeader(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertPWHeader", paramMap);
	}
	@Override
	public void insertPLHeader(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertPLHeader", paramMap);
	}
	/**
	 * 计算差异写到临时数据表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void calDiffStock(Map<String, String> paramMap) throws Exception {
		this.updateByKey("calDiffStock", paramMap);
	}
	/**
	 * 获取导入数据详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> querImportStock(Map<String, String> paramMap) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryImportTempForPage", paramMap);
	}
	/**
	 * 将临时数据表数据写入正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void insertStockTakToFormal(List<InvStockTakModel> list) throws Exception {
		this.insertByKey("insertStockTakToFormal", list);
	}
	/**
	 * 查询盘点时修改的库存数据的ID
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvStockTakModel> queryUpdateIds(Map<String, String> paramMap) throws Exception {
		return (List<InvStockTakModel>) this.getList("queryUpdateIds", paramMap);
	}
	/**
	 * 修改数据的导入状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void updateImportStatus(Map<String, String> paramMap) throws Exception {
		this.updateByKey("updateImportStatus", paramMap);
	}
}
