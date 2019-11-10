package com.hotent.mini.controller.calendar;

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
import com.hotent.base.db.api.IdGenerator;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.system.worktime.manager.CalendarDateTypeManager;
import com.hotent.system.worktime.model.CalendarDateType;

/**
 * 时间维度管理
 * <pre> 
 * 构建组：x5-bpmx-platform
 * 作者:hyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/dateType/")
public class DateTypeController extends GenericController{
	@Resource
	CalendarDateTypeManager calendarDateTypeManager;
	@Resource
	IdGenerator idGenerator;
	@Resource
	UserManager userManager;
	/**
	 * @名称：XOG_DIMENSION【维度】列表(分页条件查询)数据
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<CalendarDateType> calendarDateTypeList=(PageList<CalendarDateType>)calendarDateTypeManager.query(queryFilter);
		return new PageJson(calendarDateTypeList);
	}
	
	/**
	 * 编辑@名称：XOG_DIMENSION【维度】信息页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		CalendarDateType calendarDateType=null;
		if(StringUtil.isNotEmpty(id)){//编辑
			calendarDateType=calendarDateTypeManager.get(id);
		}else{//新增
			calendarDateType=new CalendarDateType();
		}
		return getAutoView()
				.addObject("calendarDateType", calendarDateType)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 系统用户信息明细页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		CalendarDateType calendarDateType=null;
		if(StringUtil.isNotEmpty(id)){
			calendarDateType=calendarDateTypeManager.get(id);
		}
		return getAutoView().addObject("calendarDateType", calendarDateType).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存系统用户信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param calendarDateType
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,CalendarDateType calendarDateType) throws Exception{
		
		String resultMsg=null;
		String id=calendarDateType.getId();
		
		try {
			if(StringUtil.isEmpty(id)){
				calendarDateType.setId(idGenerator.getSuid());
				calendarDateTypeManager.create(calendarDateType);
				resultMsg="添加加班成功";
			}else{
				calendarDateTypeManager.update(calendarDateType);
				resultMsg="更新加班成功";
			}
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除系统用户记录(逻辑删除)
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
			calendarDateTypeManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除@名称：XOG_DIMENSION【维度】成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除@名称：XOG_DIMENSION【维度】失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}