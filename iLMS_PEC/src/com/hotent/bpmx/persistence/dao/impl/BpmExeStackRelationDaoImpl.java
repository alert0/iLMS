package com.hotent.bpmx.persistence.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;

/**
 * 
 * <pre> 
 * 描述：堆栈关系表 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-06-17 13:56:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmExeStackRelationDaoImpl extends MyBatisDaoImpl<String, BpmExeStackRelation> implements BpmExeStackRelationDao{

    @Override
    public String getNamespace() {
        return BpmExeStackRelation.class.getName();
    }
    
    /**
	 * 根据堆栈ID获取关系记录
	 * @param stackId
	 * @param isToOrFrom
	 *  是在ToStackId位置还是以FromStackId字段：to,from
	 * @return
	 */
    @Override
    public BpmExeStackRelation getByStackId(String stackId,String isToOrFrom)
    {
    	 if(isToOrFrom.equals("to"))
    	 { 
    		return (BpmExeStackRelation)this.getOne("getByToStackId", stackId);
    	 }
    	 else {
    		 return (BpmExeStackRelation)this.getOne("getByFromStackId", stackId);
		}
     

    }

	@Override
	public List<BpmExeStackRelation> getListByProcInstId(String procInstId) {
		 Map<String, Object> params=new HashMap<String, Object>();
		 params.put("procInstId", procInstId);
		return this.getList("getListByProcInstId", params);
	}
    
	
}

