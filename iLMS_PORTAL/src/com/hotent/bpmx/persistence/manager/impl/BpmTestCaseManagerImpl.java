package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FastJsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.bpmx.api.model.process.nodedef.ext.CallActivityNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.persistence.dao.BpmTestCaseDao;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTestCaseManager;
import com.hotent.bpmx.persistence.manager.DefaultBpmDefinitionAccessor;
import com.hotent.bpmx.persistence.model.BpmTestCase;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.util.PublishAutoTestEventUtil;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.StartResult;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.restful.params.CommonResult;
import com.hotent.restful.params.StartFlowParamObject;
import com.hotent.sys.util.SysObjTypeConstants;

/**
 * 
 * <pre> 
 * 描述：流程的测试用例设置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2018-01-15 16:39:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmTestCaseManager")
public class BpmTestCaseManagerImpl extends AbstractManagerImpl<String, BpmTestCase> implements BpmTestCaseManager{
	
	private Logger logger = LoggerFactory.getLogger(BpmTestCaseManagerImpl.class);
	@Resource
	BpmTestCaseDao bpmTestCaseDao;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	IProcessService processService;
	@Resource
	BpmTaskService bpmTaskService;
	@Resource
	IUserService userService;
	@Resource
	DefaultBpmDefinitionAccessor defaultBpmDefinitionAccessor;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmFormManager bpmFormManager;
	
	@Override
	protected Dao<String, BpmTestCase> getDao() {
		return bpmTestCaseDao;
	}
	@Override
	public void startTest(String ids) throws Exception {
		 
		List<BpmTestCase> bpmTestCaseList = bpmTestCaseDao.getByIds(Arrays.asList(ids.split(",")), null);
		 
		for (BpmTestCase bpmTestCase : bpmTestCaseList) {
			startFlow(bpmTestCase);
		}
	}
	
	
	@Override
	public void doNext(String id) {
		ContextThreadUtil.putCommonVars("skipDebugger", true);
		PublishAutoTestEventUtil.publishAutoTestEvent(id);
	}
	
	@Override
	public CommonResult<JSONObject> getBaseInfo(String defKey) {
	
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey);
		String defId = bpmDefinition.getId();
		
		JSONObject jo = new JSONObject();
		
		JSONArray defKeys = new JSONArray();
		
		getAllNodeId(jo,defKeys,defKey);
		
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.PC);
		BpmNodeForm nodeForm = bpmFormService.getByDefId(defId);
		
		
		BpmForm bpmForm = bpmFormManager.getMainByFormKey(nodeForm.getForm().getFormValue());
		String formId = "";
		if(BeanUtils.isNotEmpty(bpmForm)){
			formId = bpmForm.getId();
		}
		
		JSONObject jsonObject = new JSONObject();
		
		// 用户设置表单数据
		jsonObject.put("formId", formId);
		jsonObject.put("nodeInfo", jo);
		jsonObject.put("defKeys", defKeys);
		
		CommonResult<JSONObject> result =  new CommonResult<JSONObject>(true, "获取配置信息成功", jsonObject);
		
		return result;
	}
	
	@Override
	public JSONObject getReportData(String ids) {
		List<BpmTestCase> bpmTestCases = bpmTestCaseDao.getByIds(Arrays.asList(ids.split(",")), "id_");
		
		JSONObject jo = new JSONObject();
		
		JSONObject procInstJo = new JSONObject();
		
		for (BpmTestCase bpmTestCase : bpmTestCases) {
			List<String> entityIds = new ArrayList<String>();
			entityIds.add(SysObjTypeConstants.BPMX_AUTO_TEST+bpmTestCase.getId());
			List<DefaultBpmProcessInstance> instances = bpmProcessInstanceManager.getByIds(entityIds, "sys_code_");
			JSONObject _tmp = new JSONObject();
			for (DefaultBpmProcessInstance defaultBpmProcessInstance : instances) {
				
				if("end".equals(defaultBpmProcessInstance.getStatus())){
					_tmp.put("end", FastJsonUtil.getInt(_tmp, "end",0)+1);
				}
				
				if("manualend".equals(defaultBpmProcessInstance.getStatus())){
					_tmp.put("endProcess", FastJsonUtil.getInt(_tmp, "endProcess",0)+1);
				}
				if("running".equals(defaultBpmProcessInstance.getStatus())){
					_tmp.put("unend", FastJsonUtil.getInt(_tmp, "unend",0)+1);
				}
				
			}
			procInstJo.put(bpmTestCase.getId(), _tmp);
			
		}
		JSONArray xAxisJa = new JSONArray();
		JSONArray endJa = new JSONArray();
		JSONArray unendJa = new JSONArray();
		JSONArray endProcessJa = new JSONArray();
		
		for (BpmTestCase bpmTestCase : bpmTestCases) {
			xAxisJa.add(bpmTestCase.getName());
			endJa.add(FastJsonUtil.getInt(procInstJo.getJSONObject(bpmTestCase.getId()),"end",0));
			unendJa.add(FastJsonUtil.getInt(procInstJo.getJSONObject(bpmTestCase.getId()),"unend",0));
			endProcessJa.add(FastJsonUtil.getInt(procInstJo.getJSONObject(bpmTestCase.getId()),"endProcess",0));
		}
		
		jo.put("xAxis", xAxisJa);
		jo.put("end", endJa);
		jo.put("unend", unendJa);
		jo.put("endProcess", endProcessJa);
		
		return jo;
	}
	
	private void getAllNodeId(JSONObject jo,JSONArray defKeys, String defKey) {
		
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey);
		String defId = bpmDefinition.getId();
		List<BpmNodeDef> allNodeDef = defaultBpmDefinitionAccessor.getAllNodeDef(defId);
		
		
		JSONArray ja = new JSONArray();
		JSONObject defInfo = new JSONObject();
		defInfo.put("defKey", defKey);
		defInfo.put("defName", bpmDefinition.getName());
		defKeys.add(defInfo);
		
		for (BpmNodeDef bpmNodeDef : allNodeDef) {
			if(bpmNodeDef instanceof UserTaskNodeDef || bpmNodeDef instanceof SignNodeDef){
				JSONObject nodeJo = new JSONObject();
				nodeJo.put("nodeId", bpmNodeDef.getNodeId());
				nodeJo.put("nodeName", bpmNodeDef.getName());
				nodeJo.put("nodeType", bpmNodeDef.getType().getKey());
				ja.add(nodeJo);
			}
			
			if( bpmNodeDef instanceof CallActivityNodeDef ){
				CallActivityNodeDef callActivityNodeDef = (CallActivityNodeDef) bpmNodeDef;
				getAllNodeId(jo,defKeys, callActivityNodeDef.getFlowKey()); 
			}
		}
		jo.put(defKey, ja);
		
	}
	
	private Set<String> getStartors(String startor){
		Set<String> set = new HashSet<String>();
		if(StringUtil.isEmpty(startor)) return set;
		JSONArray parseArray = JSON.parseArray(startor);
		
		List<IUser> userList = new ArrayList<IUser>();
		
		for (Object object : parseArray) {
			JSONObject jo = (JSONObject) object;
			String ids = jo.getString("id");
			String[] split = ids.split(",");
			for (String id : split) {
				if("user".equals(jo.getString("type"))){
					IUser user = userService.getUserById(id);
					if(BeanUtils.isEmpty(user)) continue;
					userList.add(user);
				}else{
					List<IUser> users = userService.getUserListByGroup(jo.getString("type"), id);
					if(BeanUtils.isEmpty(users)) continue;
					userList.addAll(users);
				}
			}
			
		}
		
		for (IUser iUser : userList) {
			if(BeanUtils.isEmpty(iUser) || StringUtil.isEmpty(iUser.getAccount()) ) continue;
			set.add(iUser.getAccount());
		}
		
		return set;
	}
	
	// 根据测试用例启动流程
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void startFlow(BpmTestCase bpmTestCase) throws InterruptedException, ExecutionException {
		String account= bpmTestCase.getStartorAccount();
		 
		 String startor = bpmTestCase.getStartor();
		 Set<String> startors = getStartors(startor);
		 if(StringUtil.isNotEmpty(account)){
			 startors.add(account);
		 }
		 if(BeanUtils.isEmpty(startors)){
			 throw new RuntimeException("请设置流程启动人员");
		 }
		 
		 // 创建一个线程池  
	     ExecutorService pool = Executors.newFixedThreadPool(startors.size());  
	     // 创建多个有返回值的任务  
	     List<Future> list = new ArrayList<Future>();  
	     for (String _account : startors) {
		      Callable c = new MyCallable(bpmTestCase,_account);  
		      // 执行任务并获取Future对象  
		      Future f = pool.submit(c);  
		      list.add(f);  
	     }  

//		 // 获取所有并发任务的运行结果  
//		 for (Future f : list) {  
//		   // 从Future对象上获取任务的返回值，并输出到控制台  
//		   String instId = f.get().toString();
//		   System.out.println(">>>" + f.get().toString());  
//		   if(BeanUtils.isEmpty(instId))continue;
//		   
//	       PublishAutoTestEventUtil.publishAutoTestEvent(instId);
//		 } 
		 // 关闭线程池  
		 pool.shutdown(); 
		 
	}
	
}


class MyCallable implements Callable<Object> {  
	private Logger logger = LoggerFactory.getLogger(BpmTestCaseManagerImpl.class);
	private BpmTestCase bpmTestCase;  
	private String account;
	
	MyCallable(BpmTestCase bpmTestCase,String account) {  
	  this.bpmTestCase = bpmTestCase;  
	  this.account = account;
	}  
	
	public Object call() throws Exception {  
	  logger.debug(">>>" + account + "任务启动");  
	  Date dateTmp1 = new Date();  
	  String instId = startFlow(); 
	  Thread.sleep(200);
	  Date dateTmp2 = new Date();  
	  long time = dateTmp2.getTime() - dateTmp1.getTime();  
	  logger.debug(">>>" + account + "任务终止; 花费时间为： " + time);  
	  return instId;  
	}
	
	
	private String startFlow(){

		 StartFlowParamObject startFlowParamObject = new StartFlowParamObject();
		 startFlowParamObject.setAccount(account);
		 startFlowParamObject.setFlowKey(bpmTestCase.getDefKey());
		 startFlowParamObject.setSubject("流程仿真测试启动流程["+bpmTestCase.getName()+"]");
		 try {
			startFlowParamObject.setData(Base64.getBase64(bpmTestCase.getBoFormData()));
			startFlowParamObject.setSysCode(SysObjTypeConstants.BPMX_AUTO_TEST+bpmTestCase.getId());
			IProcessService processService = AppUtil.getBean(IProcessService.class);
			StartResult start = new StartResult("");
			start = processService.start(startFlowParamObject);
			if(start.getResult() && StringUtil.isNotEmpty(start.getInstId()) ){
				logger.debug("instId:"+start.getInstId());
				
				PublishAutoTestEventUtil.publishAutoTestEvent(start.getInstId());
				
				return start.getInstId();
			}else{
				throw new RuntimeException("启动流程仿真测试失败");
			}
		} catch ( Exception e1) {
			e1.printStackTrace();
		}
		return "";
	
	}
	
}