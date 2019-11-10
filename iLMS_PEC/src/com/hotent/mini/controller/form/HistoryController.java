package com.hotent.mini.controller.form;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.form.persistence.manager.BpmFormHistoryManager;
import com.hotent.form.persistence.model.BpmFormHistory;

import net.sf.json.JSONObject;
 

/**
 * 
 * <pre> 
 * 描述：流程表单HTML设计历史记录 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang.qq.com
 * 日期:2014-10-23 15:31:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/form/history/")
public class HistoryController extends GenericController{
	@Resource
	BpmFormHistoryManager bpmFormHistoryManager;
	
	/**
	 * 流程表单HTML设计历史记录列表(分页条件查询)数据
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		List<BpmFormHistory> list = bpmFormHistoryManager.query(queryFilter);
		return new PageJson(list);
	}
	
	/**
	 * 编辑流程表单HTML设计历史记录信息页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("historyEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		BpmFormHistory bpmFormHistory=null;
		if(StringUtil.isNotEmpty(id)){
			bpmFormHistory=bpmFormHistoryManager.get(id);
		}
		return getAutoView().addObject("bpmFormHistory", bpmFormHistory).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 流程表单HTML设计历史记录明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("historyGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		BpmFormHistory bpmFormHistory=null;
		if(StringUtil.isNotEmpty(id)){
			bpmFormHistory=bpmFormHistoryManager.get(id);
		}
		return getAutoView().addObject("bpmFormHistory", bpmFormHistory).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存流程表单HTML设计历史记录信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param bpmFormHistory
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,BpmFormHistory bpmFormHistory) throws Exception{
		String resultMsg=null;
		String id=bpmFormHistory.getId();
		try {
			if(StringUtil.isEmpty(id)){
				bpmFormHistory.setId(UniqueIdUtil.getSuid());
				bpmFormHistoryManager.create(bpmFormHistory);
				resultMsg="添加流程表单HTML设计历史记录成功";
			}else{
				bpmFormHistoryManager.update(bpmFormHistory);
				resultMsg="更新流程表单HTML设计历史记录成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对流程表单HTML设计历史记录操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除流程表单HTML设计历史记录记录
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			bpmFormHistoryManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除流程表单HTML设计历史记录成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除流程表单HTML设计历史记录失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 异步返回历史记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByAjax")
	@ResponseBody
	public BpmFormHistory getByAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String hisId = RequestUtil.getString(request, "hisId");
		BpmFormHistory BpmFormHistory = bpmFormHistoryManager.get(hisId);
		return BpmFormHistory;
	}
}
