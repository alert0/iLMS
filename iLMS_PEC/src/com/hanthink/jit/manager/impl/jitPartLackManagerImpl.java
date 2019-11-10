package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.jitPartLackDao;
import com.hanthink.jit.manager.jitPartLackManager;
import com.hanthink.jit.model.JitPartLackModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.util.ContextUtil;

@Service("jitPartLackManager")
public class jitPartLackManagerImpl extends AbstractManagerImpl<String, JitPartLackModel> implements jitPartLackManager{
	
	@Resource
	private jitPartLackDao dao;
	
	@Override
	protected Dao<String, JitPartLackModel> getDao() {
		
		return dao;
	}
	@Override
	public List<JitPartLackModel> getJitPartLackList(JitPartLackModel model, DefaultPage page) {
		return dao.getJitPartLackList(model,page);
	}
	@Override
	public List<JitPartLackModel> getJitPartLackDetialList(JitPartLackModel model,DefaultPage page) {
		
		return dao.getJitPartLackDetialList(model,page);
	}
	@Override
	public void jitPartLackCheck(String[] idArr, JitPartLackModel model) {
		for (String id : idArr) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(model.getDealIp());
			logVO.setFromName("检查");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_JIT_PART_LACK");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(id);
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			model.setId(id);
			dao.updateCheckFlag(model); //修改主表中检查标志
			dao.insertLackDeal(model);  //插入检查表中
		}
	}

}
