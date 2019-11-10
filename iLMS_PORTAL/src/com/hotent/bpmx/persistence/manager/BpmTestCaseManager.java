package com.hotent.bpmx.persistence.manager;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTestCase;
import com.hotent.restful.params.CommonResult;

/**
 * 
 * <pre> 
 * 描述：流程的测试用例设置 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2018-01-15 16:39:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTestCaseManager extends Manager<String, BpmTestCase>{

	void startTest(String ids)  throws Exception ;

	CommonResult<JSONObject> getBaseInfo(String defKey);

	void doNext(String id);

	JSONObject getReportData(String ids);
	
}
