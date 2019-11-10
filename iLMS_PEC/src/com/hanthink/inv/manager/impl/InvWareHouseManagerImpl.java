package com.hanthink.inv.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.inv.dao.InvWareHouseDao;
import com.hanthink.inv.manager.InvWareHouseManager;
import com.hanthink.inv.model.InvWareHouseModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:仓库管理业务实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("wareHouse")
public class InvWareHouseManagerImpl extends AbstractManagerImpl<String, InvWareHouseModel>
				implements InvWareHouseManager{
	@Resource
	private InvWareHouseDao wareHouseDao;
	
	@Override
	protected Dao<String, InvWareHouseModel> getDao() {
		return wareHouseDao;
	}
	/**
	 * 仓库信息分页查询业务接口实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public PageList<InvWareHouseModel> queryWareHouseForPage(InvWareHouseModel model, Page page) throws Exception {
	
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());		
		
		List<InvWareHouseModel> list = wareHouseDao.queryWareHouseForPage(model,page);
		
		return new PageList<InvWareHouseModel>(list);
	}
	/**
	 * 获取仓库类型业务实现方法
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public List<DictVO> getWareType() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		return wareHouseDao.getWareType(paramMap);
	}
	/**
	 * 根据ID查询仓库数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public InvWareHouseModel getWareHouseById(String id) throws Exception {
		return wareHouseDao.getWareHouseById(id);
	}
	@Override
	public void createWareHouse(InvWareHouseModel model) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setCreationUser(user.getAccount());
		model.setLastModifiedUser(user.getAccount());
		
		if(StringUtil.isEmpty(model.getWareCode())) {
			throw new Exception("仓库代码不能为空");
		}else {
			List<InvWareHouseModel> list = wareHouseDao.queryWareHouseByWareCode(model);			
			
			if (list.size() > 0) {
				throw new Exception("仓库代码已存在");
			}
			
			List<InvWareHouseModel> nameList = wareHouseDao.queryWareHosueName(model);
			if (nameList.size() > 0) {
				throw new Exception("仓库名称已存在");
			}
		}
		if(StringUtil.isEmpty(model.getWareName())) {
			throw new Exception("仓库名称不能为空");
		}
		if(StringUtil.isEmpty(model.getWareType())) {
			throw new Exception("仓库类型不能为空");
		}
		if(StringUtil.isEmpty(model.getErpWareCode())) {
			throw new Exception("ERP仓库代码不能为空");
		}
		try {
			wareHouseDao.createWareHouse(model);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 修改仓库信息业务实现方法
	 * @param model
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void updateWareHouse(InvWareHouseModel model,String ipAddr) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setLastModifiedUser(user.getAccount());
		try {
			//记录修改日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("修改仓库数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_WAREHOUSE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(model.getId());
			//记录日志
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			wareHouseDao.updateWareHouse(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 单条/批量数据删除业务实现方法
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void deleteWareHouseByIds(String[] ids,String ipAddr) throws Exception {
		int total = ids.length;
		//将id数字转为list
		List<String> list = new ArrayList<String>(ids.length);
		Collections.addAll(list,ids);
		//将list转为set去重(id是唯一标识,除去有数据的undefined后不会重复)
		Set<String> set = new HashSet<String>(list);
		List<String> idList = new ArrayList<String>(set);
		//去除undefined--前端处理有库存的id
		idList.remove("undefined");
		//判断是否还有无库存的仓库
		ids = idList.toArray(new String[idList.size()]);
		if(ids.length < 1 && ids.length < total) {
			return;
		}
		try {
			//记录删除日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("删除仓库数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_INV_WAREHOUSE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");		
			pkColumnVO.setColumnValArr(ids);
			//记录日志
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);		
			//执行删除
			wareHouseDao.deleteWareHouseByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}		
	}
	@Override
	public List<InvWareHouseModel> queryERPWareCode() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return wareHouseDao.queryERPWareCode(factoryCode);
	}
	@Override
	public List<InvWareHouseModel> queryWareType() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return wareHouseDao.queryWareType(factoryCode);
	}
	
}
