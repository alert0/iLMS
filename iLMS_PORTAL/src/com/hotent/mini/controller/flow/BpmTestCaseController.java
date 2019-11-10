package com.hotent.mini.controller.flow;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmTestCaseManager;
import com.hotent.bpmx.persistence.model.BpmTestCase;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.restful.params.CommonResult;


/**
 * 
 * <pre> 
 * 描述：流程的测试用例设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2018-01-15 16:44:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/bpmx/test/bpmTestCase")
public class BpmTestCaseController extends GenericController{
	@Resource
	BpmTestCaseManager bpmTestCaseManager;
	
	/**
	 * 流程的测试用例设置列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<BpmTestCase> bpmTestCaseList=(PageList<BpmTestCase>)bpmTestCaseManager.query(queryFilter);
		return new PageJson(bpmTestCaseList);
	}
	
	
	
	/**
	 * 流程的测试用例设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody BpmTestCase getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new BpmTestCase();
		}
		BpmTestCase bpmTestCase=bpmTestCaseManager.get(id);
		return bpmTestCase;
	}
	
	/**
	 * 保存流程的测试用例设置信息
	 * @param request
	 * @param response
	 * @param bpmTestCase
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody BpmTestCase bpmTestCase) throws Exception{
		String resultMsg=null;
		String id=bpmTestCase.getId();
		try {
			if(StringUtil.isEmpty(id)){
				bpmTestCase.setId(UniqueIdUtil.getSuid());
				bpmTestCaseManager.create(bpmTestCase);
				resultMsg="添加流程的测试用例设置成功";
			}else{
				bpmTestCaseManager.update(bpmTestCase);
				resultMsg="更新流程的测试用例设置成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对流程的测试用例设置操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除流程的测试用例设置记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			bpmTestCaseManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除流程的测试用例设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除流程的测试用例设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	/**
	 * 启动测试
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("startTest")
	public @ResponseBody BasicResult startTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ids=RequestUtil.getString(request, "ids");
		if(StringUtil.isEmpty(ids)){
			return new BasicResult(false, "没有传递测试用例编号");
		}
		bpmTestCaseManager.startTest(ids);
		return  new BasicResult(true, "启动测试用例成功");
	}
	
	/**
	 * 继续启动测试用例
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("doNext")
	public @ResponseBody BasicResult doNext(HttpServletRequest request,HttpServletResponse response){
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new BasicResult(false, "没有传递流程实例id");
		}
		bpmTestCaseManager.doNext(id);
		
		return  new BasicResult(true, "测试用例重新启动成功");
	}
	
	/**
	 * 
	 * @param defKey
	 * @return  {"flowInfo":{   },"formId":"" }
	 */
	@RequestMapping("getBaseInfo")
	public @ResponseBody CommonResult<JSONObject> getBaseInfo(@RequestParam String defKey){
		return bpmTestCaseManager.getBaseInfo(defKey);
	}
	
	@RequestMapping("getReportData")
	public @ResponseBody CommonResult<JSONObject> getReportData(HttpServletRequest request,HttpServletResponse response){
		
		String ids=RequestUtil.getString(request, "ids");
		if(StringUtil.isEmpty(ids)){
			return new CommonResult<JSONObject>(false, "测试用例id不能为空", null);
		}
		JSONObject jo = bpmTestCaseManager.getReportData(ids);
		
		return  new CommonResult<JSONObject>(true, "测试用例重新启动成功", jo); 
		
	}
	
	
}
