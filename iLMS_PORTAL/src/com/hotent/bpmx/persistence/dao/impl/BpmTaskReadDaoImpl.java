package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskReadDao;
import com.hotent.bpmx.persistence.model.BpmTaskRead;


@Repository

public class BpmTaskReadDaoImpl extends MyBatisDaoImpl<String, BpmTaskRead> implements BpmTaskReadDao{

    @Override
    public String getNamespace() {
        return BpmTaskRead.class.getName();
    }
    
    /**
	  * 根据流程实例列表删除任务。
	  * @param instList 
	  * void
	  */
	 public void delByInstList(List<String> instList){
		 this.deleteByKey("delByInstList", instList);
	 }
	
}

