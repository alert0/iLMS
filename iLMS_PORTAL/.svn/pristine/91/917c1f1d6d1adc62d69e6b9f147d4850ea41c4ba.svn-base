package com.hotent.mini.controller.flow;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmTaskReminderManager;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;


/**
 * 
 * <pre> 
 * 描述：任务催办 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:00
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/flow/bpmTaskReminder")
public class BpmTaskReminderController extends GenericController{
	@Resource
	BpmTaskReminderManager bpmTaskReminderManager;

	/**
	 * 任务催办列表(分页条件查询)数据
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
		PageList<BpmTaskReminder> bpmTaskReminderList=(PageList<BpmTaskReminder>)bpmTaskReminderManager.query(queryFilter);
		return new PageJson(bpmTaskReminderList);
	}
	
	
	
	/**
	 * 任务催办明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody BpmTaskReminder getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		BpmTaskReminder bpmTaskReminder=bpmTaskReminderManager.get(id);
		return bpmTaskReminder;
	}
	
	/**
	 * 保存任务催办信息
	 * @param request
	 * @param response
	 * @param bpmTaskReminder
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody BpmTaskReminder bpmTaskReminder) throws Exception{
		String resultMsg=null;
		String id=bpmTaskReminder.getId();
		try {
			if(StringUtil.isEmpty(id)){
				bpmTaskReminder.setId(UniqueIdUtil.getSuid());
				bpmTaskReminderManager.create(bpmTaskReminder);
				resultMsg="添加任务催办成功";
			}else{
				bpmTaskReminderManager.update(bpmTaskReminder);
				resultMsg="更新任务催办成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对任务催办操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除任务催办记录
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
			bpmTaskReminderManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除任务催办成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除任务催办失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
