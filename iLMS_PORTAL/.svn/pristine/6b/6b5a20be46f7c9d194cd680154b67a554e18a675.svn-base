package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmExeStackExecutorDao;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;


@Repository
public class BpmExeStackExecutorDaoImpl extends MyBatisDaoImpl<String, BpmExeStackExecutor> implements BpmExeStackExecutorDao{

    @Override
    public String getNamespace() {
        return BpmExeStackExecutor.class.getName();
    }

	@Override
	public BpmExeStackExecutor getByTaskId(String taskId) {
		return this.getUnique("getByTaskId", taskId);
	}

	@Override
	public List<BpmExeStackExecutor> getByStackId(String stackId) {
		return this.getByKey("getByStackId", stackId);
	}

	@Override
	public void deleteByStackId(String stackId) {
		this.deleteByKey("deleteByStackId",stackId);
	}

	@Override
	public void deleteByStackPath(String stackPath) {
		String [] stackIds = stackPath.split("\\.");
		if(BeanUtils.isEmpty(stackIds)) return ;
		
		Map map = this.buildMap("stackIds", stackIds);
		this.deleteByKey("deleteByStackIds", map);
	}
	
	
	public static void main(String[] args) {
		String aa = "111.11.1.";
		String [] stackIds = aa.split("\\.");
		System.out.println(stackIds.length);
	}

}

