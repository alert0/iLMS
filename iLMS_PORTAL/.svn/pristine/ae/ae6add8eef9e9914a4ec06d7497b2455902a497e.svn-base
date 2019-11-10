package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				throw new Exception("仓库代码重复");
			}
		}
		if(StringUtil.isEmpty(model.getWareName())) {
			throw new Exception("仓库名称不能为空");
		}
		if(StringUtil.isEmpty(model.getWareType())) {
			throw new Exception("仓库类型不能为空");
		}
		wareHouseDao.createWareHouse(model);
	}
	/**
	 * 修改仓库信息业务实现方法
	 * @param model
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void updateWareHouse(InvWareHouseModel model) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setLastModifiedUser(user.getAccount());
		wareHouseDao.updateWareHouse(model);
	}
	/**
	 * 单条/批量数据删除业务实现方法
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@Override
	public void deleteWareHouseByIds(String factoryCode,String[] ids,String ipAddr) throws Exception {
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("仓库数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_INV_WAREHOUSE");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(ids);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		wareHouseDao.deleteWareHouseByIds(ids);
		
	}
}
