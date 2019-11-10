package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmCheckOpinionDao;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;

@Service("bpmCheckOpinionManager")
public class BpmCheckOpinionManagerImpl extends AbstractManagerImpl<String, DefaultBpmCheckOpinion> implements BpmCheckOpinionManager{
	@Resource
	BpmCheckOpinionDao bpmCheckOpinionDao;
	@Override
	protected Dao<String, DefaultBpmCheckOpinion> getDao() {
		return bpmCheckOpinionDao;
	}
	@Override
	public DefaultBpmCheckOpinion getByTaskId(String taskId) {
		return bpmCheckOpinionDao.getByTaskId(taskId);
	}
	@Override
	public void archiveHistory(String instId) {
		bpmCheckOpinionDao.archiveHistory(instId);
	}
	
	@Override
	public List<DefaultBpmCheckOpinion> getByInstId(String instId) {
		//取得顶级的流程实例ID
		String supInstId=getTopInstId(instId);
		List<String> instList=getListByInstId(supInstId);
		return bpmCheckOpinionDao.getByInstIds(instList);
	}
	
	/**
	 * 向上查询得到顶级的流程实例。
	 * @param instId
	 * @return String
	 */
	@Override
	public String getTopInstId(String instId){
		String rtn=instId;
		String supInstId=bpmCheckOpinionDao.getSupInstByInstId(instId);
		while(StringUtil.isNotZeroEmpty(supInstId)){
			rtn=supInstId;
			supInstId=bpmCheckOpinionDao.getSupInstByInstId(supInstId);
		}
		return rtn;
	}

	/**
	 * 向下查询流程实例。
	 * @param supperId
	 * @param instList 
	 * void
	 */
	private void getChildInst(String supperId,List<String> instList){
		List<String> list=bpmCheckOpinionDao.getBySupInstId(supperId);
		if(BeanUtils.isEmpty(list)) return ;
		for(String instId:list){
			instList.add(instId);
			getChildInst(instId,instList);
		}
	}
	
	
	@Override
	public List<String> getListByInstId(String supInstId) {
		List<String> instList=new ArrayList<String>();
		instList.add(supInstId);
		//递归往下查询
		getChildInst(supInstId,instList);
		return instList;
	}
	
	
	@Override
	public void delByInstList(List<String> instList) {
		bpmCheckOpinionDao.delByInstList(instList);
	}
	@Override
	public List<DefaultBpmCheckOpinion> getByInstNodeId(String instId, String nodeId) {
		return bpmCheckOpinionDao.getByInstNodeId(instId,nodeId);
	}
	@Override
	public void updStatusByWait(String taskId, String status) {
		bpmCheckOpinionDao.updStatusByWait(taskId, status);
	}

	@Override
	public List<DefaultBpmCheckOpinion> getByInstIdsAndWait(List<String> list) {
		return bpmCheckOpinionDao.getByInstIdsAndWait(list);
	}
	
	@Override
	public List<DefaultBpmCheckOpinion> getFormOpinionByInstId(String instId) {
		List<DefaultBpmCheckOpinion> rtnList=new ArrayList<DefaultBpmCheckOpinion>();
		List<DefaultBpmCheckOpinion> list= getByInstId(instId);
		for(DefaultBpmCheckOpinion opinion:list){
			if(StringUtil.isNotEmpty(opinion.getFormName())){
				rtnList.add(opinion);
			}
		}
		return rtnList;
	}
	@Override
	public DefaultBpmCheckOpinion getByTaskIdStatus(String taskId,String taskAction) {
		return bpmCheckOpinionDao.getByTaskIdStatus(taskId,taskAction);
	}
	
}
