/**
 * 描述：TODO
 * 包名：com.hotent.bpmx.persistence.model.query
 * 文件名：DefQueryFields.java
 * 作者：win-mailto:chensx@jee-soft.cn
 * 日期2014-1-3-上午11:07:37
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.bpmx.persistence.model.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.api.query.WhereClause;
import com.hotent.base.core.constants.Bool;
import com.hotent.base.db.model.DefaultQueryField;
import com.hotent.bpmx.persistence.constants.ProcDefTestStatus;

/**
 * <pre> 
 * 描述：简化流程定义查询字段的构造（对使用者屏蔽字段名称）
 * 构建组：x5-bpmx-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-1-3-上午11:07:37
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BpmDefQueryFields {
	private List<WhereClause> queryFields = new ArrayList<WhereClause>();

	/**
	 * 返回查询字段集合
	 * @return
	 */
	public List<WhereClause> getQueryFields() {
		return queryFields;
	}
	public BpmDefQueryFields addName(String value){
		DefaultQueryField queryField = new DefaultQueryField("NAME_",QueryOP.LIKE,value);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addName(QueryOP op,String value){
		DefaultQueryField queryField = new DefaultQueryField("NAME_",op,value);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addDefKey(String value){
		DefaultQueryField queryField = new DefaultQueryField("DEF_KEY_",QueryOP.LIKE,value);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addDefKey(QueryOP op,String value){
		DefaultQueryField queryField = new DefaultQueryField("DEF_KEY_",op,value);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addTypeId(String typeId){
		DefaultQueryField queryField = new DefaultQueryField("TYPE_ID_",QueryOP.EQUAL,typeId);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addStatus(String status){
		DefaultQueryField queryField = new DefaultQueryField("STATUS_",QueryOP.EQUAL,status);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addStatus(List<String> statusList){		
		DefaultQueryField queryField = new DefaultQueryField("STATUS_",QueryOP.IN,statusList);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addTestStatus(ProcDefTestStatus testStatus){
		DefaultQueryField queryField = new DefaultQueryField("TEST_STATUS_",QueryOP.EQUAL,testStatus);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addBpmnDefId(String bpmnDefId){
		DefaultQueryField queryField = new DefaultQueryField("BPMN_DEF_ID_",QueryOP.EQUAL,bpmnDefId);
		queryFields.add(queryField);
		return this;		
	}
	public BpmDefQueryFields addBpmnDeployId(String bpmnDeployId){
		DefaultQueryField queryField = new DefaultQueryField("BPMN_DEPLOY_ID_",QueryOP.EQUAL,bpmnDeployId);
		queryFields.add(queryField);
		return this;		
	}	
	public BpmDefQueryFields addIsMain(Bool trueOrFalse){
		DefaultQueryField queryField = new DefaultQueryField("IS_MAIN_",QueryOP.EQUAL,trueOrFalse.value());
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addCreateBy(String userId){
		DefaultQueryField queryField = new DefaultQueryField("CREATE_BY_",QueryOP.EQUAL,userId);
		queryFields.add(queryField);
		return this;
	}
	public BpmDefQueryFields addCreateTimeRange(Date startTime,Date endTime){
		if(startTime!=null && endTime!=null){
			List<Date> dateList = new ArrayList<Date>();
			dateList.add(startTime);
			dateList.add(endTime);
			DefaultQueryField queryField = new DefaultQueryField("CREATE_TIME_",QueryOP.BETWEEN,dateList);
			queryFields.add(queryField);
		}else if(startTime!=null){
			DefaultQueryField queryField = new DefaultQueryField("CREATE_TIME_",QueryOP.GREAT_EQUAL,startTime);
			queryFields.add(queryField);			
		}else if(endTime!=null){
			DefaultQueryField queryField = new DefaultQueryField("CREATE_TIME_",QueryOP.LESS_EQUAL,endTime);
			queryFields.add(queryField);
		}
		return this;
	}
}
