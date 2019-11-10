package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.inv.dao.InvUnloadPortDao;
import com.hanthink.inv.manager.InvUnloadPortManager;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:卸货口管理业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("unloadPortManage")
public class InvUnloadPortManagerImpl extends AbstractManagerImpl<String, InvUnloadPortModel>
implements InvUnloadPortManager{

	@Resource
	private InvUnloadPortDao unloadPortDao;
	@Override
	protected Dao<String, InvUnloadPortModel> getDao() {
		return unloadPortDao;
	}
	/**
	 * 卸货口数据查询业务实现方法
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@Override
	public PageList<InvUnloadPortModel> queryUnloadPortForPage(InvUnloadPortModel model, Page page)
			throws Exception {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());

		return new PageList<InvUnloadPortModel>(unloadPortDao.queryUnloadPortForPage(model,page));
	}
	/**
	 * 查询数据Excel导出业务实现接口
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@Override
	public List<InvUnloadPortModel> queryUnloadPortForExport(InvUnloadPortModel model)throws Exception {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());

		List<InvUnloadPortModel> list = unloadPortDao.queryUnloadPortForExport(model);
		List<InvUnloadPortModel> wareUnload = unloadPortDao.queryLogicUnload(model.getFactoryCode());
		for (InvUnloadPortModel invUnloadPortModel : list) {
			if (StringUtil.isNotEmpty(invUnloadPortModel.getLogicUnload()) && invUnloadPortModel.getLogicUnload().equals("[]")) {
				invUnloadPortModel.setLogicUnload(null);
			}
			if (StringUtil.isNotEmpty(invUnloadPortModel.getLogicUnload())) {
				String[] logicUnloadPort = invUnloadPortModel.getLogicUnload().split("#");
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < logicUnloadPort.length; i++) {
					for(int j = 0; j < wareUnload.size(); j++) {
						if (logicUnloadPort[i].equals(wareUnload.get(j).getWareCodeValue())) {
							sb.append(wareUnload.get(j).getWareCodeLabel()+",");
						}
					}
				}
				invUnloadPortModel.setLogicUnload(sb.toString().substring(0, sb.lastIndexOf(",")));
			}
		}
		return unloadPortDao.queryUnloadPortForExport(model);
	}
	@Override
	public void removeUnloadPort(String[] ids, String ipAddr) throws Exception {
		if (ids.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		boolean isUsedInLocation = unloadPortDao.unloadPortIsUsedInLocation(ids);
		if (isUsedInLocation) {
			throw new Exception("该卸货口暂在属地使用中,不支持删除");
		}
		//记录修改日志
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("删除卸货口数据");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_UNLOAD");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(ids);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		try {
			unloadPortDao.removeUnloadPort(ids);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 卸货口新增/修改
	 * @param model
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveUnloadPortEdit(List<Map<String, String>> list, String ipAddr) throws Exception {
		InvUnloadPortModel model = new InvUnloadPortModel();
		String modelMap = list.get(0).get("addForm");
		String logicUnload = list.get(0).get("logicUnload");
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map = gson.fromJson(modelMap, map.getClass());
		model.setId(map.get("id"));
		model.setWareCode(map.get("wareCode"));
		model.setUnloadPort(map.get("unloadPort"));
		model.setWorkCenter(map.get("workCenter"));
		model.setPlFlag(map.get("plFlag"));
		model.setLogisticsMode(map.get("logisticsMode"));
		model.setUnloadType(map.get("unloadType"));
		model.setNote(map.get("note"));
		model.setInnerLogisticsMode(map.get("innerLogisticsMode"));
		//根据界面数据判断是否有id值,有则更新，无则新增
		String resultMsg = null;
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
		model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
		if (StringUtil.isEmpty(model.getId())) {
			logicUnload = logicUnload.replace("[\"", "");
			logicUnload = logicUnload.replace("\"]", "");
			logicUnload = logicUnload.replace("\",\"", "#");
			model.setLogicUnload(logicUnload);
			try {
				//判断卸货口是否存在
				boolean unloadPortIsExists = unloadPortDao.isExistsUnload(model);
				if (unloadPortIsExists) {
					resultMsg = "卸货口已存在";
					throw new Exception(resultMsg);
				}
				boolean wareCodeIsExists = unloadPortDao.isExistsWareCode(model);
				if (!wareCodeIsExists) {
					resultMsg = "仓库代码不存在";
					throw new Exception(resultMsg);
				}
				try {
					unloadPortDao.createUnloadport(model);
					resultMsg = "新增卸货口信息成功";
				} catch (Exception e) {
					e.printStackTrace();
					resultMsg = "系统错误,请联系管理员";
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg = e.getMessage();
				throw new Exception(resultMsg);
			}	
		}else {
			boolean wareCodeIsExists = unloadPortDao.isExistsWareCode(model);
			if (!wareCodeIsExists) {
				resultMsg = "仓库代码不存在";
				throw new Exception(resultMsg);
			}
			boolean unloadportIsUsedInPartLocation = unloadPortDao.isUsedInLocation(model);
			if (unloadportIsUsedInPartLocation) {
				throw new Exception("该卸货口暂在属地使用中,不支持修改");
			}
			//记录修改日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("修改卸货口数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_UNLOAD");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(model.getId());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			//修改数据
			try {
				logicUnload = logicUnload.replace("[\"", "");
				logicUnload = logicUnload.replace("\"]", "");
				logicUnload = logicUnload.replace("\",\"", "#");
				logicUnload = logicUnload.replace("null,", "");
				logicUnload = logicUnload.replace("\",\"", "#");
				if (logicUnload.startsWith("[")) {
					logicUnload = logicUnload.replace("[\"", "");
				}
				if (logicUnload.endsWith("]")) {
					logicUnload = logicUnload.replace("\",null]", "");					
				}
				model.setLogicUnload(logicUnload);
				unloadPortDao.updateUnloadPort(model);
				resultMsg = "修改卸货口数据成功";
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg = "系统错误,请联系管理员";
				throw new Exception(resultMsg);
			}
		}
		return resultMsg;
	}
	/**
	 * 查询仓库列表
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@Override
	public List<InvUnloadPortModel> queryWareCodeLsit() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return unloadPortDao.queryWareCodeLsit(factoryCode);
	}
	/**
	 * 查询物理卸货口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月18日
	 */
	@Override
	public List<InvUnloadPortModel> queryLogicUnload() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return unloadPortDao.queryLogicUnload(factoryCode);
	}
}
