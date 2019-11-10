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
import com.hotent.bpmx.persistence.manager.BpmReminderHistoryManager;
import com.hotent.bpmx.persistence.model.BpmReminderHistory;


/**
 * 
 * <pre> 
 * 描述：催办历史 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:51:19
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/flow/bpmReminderHistory")
public class BpmReminderHistoryController extends GenericController{
	@Resource
	BpmReminderHistoryManager bpmReminderHistoryManager;
	
	/**
	 * 催办历史列表(分页条件查询)数据
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
		PageList<BpmReminderHistory> bpmReminderHistoryList=(PageList<BpmReminderHistory>)bpmReminderHistoryManager.query(queryFilter);
		return new PageJson(bpmReminderHistoryList);
	}
	
	
	
	/**
	 * 催办历史明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody BpmReminderHistory getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		BpmReminderHistory bpmReminderHistory=bpmReminderHistoryManager.get(id);
		return bpmReminderHistory;
	}
	
	/**
	 * 保存催办历史信息
	 * @param request
	 * @param response
	 * @param bpmReminderHistory
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody BpmReminderHistory bpmReminderHistory) throws Exception{
		String resultMsg=null;
		String id=bpmReminderHistory.getId();
		try {
			if(StringUtil.isEmpty(id)){
				bpmReminderHistory.setId(UniqueIdUtil.getSuid());
				bpmReminderHistoryManager.create(bpmReminderHistory);
				resultMsg="添加催办历史成功";
			}else{
				bpmReminderHistoryManager.update(bpmReminderHistory);
				resultMsg="更新催办历史成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对催办历史操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除催办历史记录
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
			bpmReminderHistoryManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除催办历史成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除催办历史失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
