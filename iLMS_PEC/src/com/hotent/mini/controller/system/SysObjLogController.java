package com.hotent.mini.controller.system;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.SysObjLogManager;
import com.hotent.sys.persistence.model.SysObjLog;


/**
 * 
 * <pre> 
 * 描述：常用日志 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-09-27 10:33:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysObjLog")
public class SysObjLogController extends GenericController{
	@Resource
	SysObjLogManager sysObjLogManager;
	
	/**
	 * 常用日志列表(分页条件查询)数据
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
		String type = RequestUtil.getString(request, "type");
		if(StringUtil.isNotEmpty(type))queryFilter.addFilter("obj_type_", type, QueryOP.EQUAL);
		
		PageList<SysObjLog> sysObjLogList=(PageList<SysObjLog>)sysObjLogManager.query(queryFilter);
		return new PageJson(sysObjLogList);
	}
	
	
	
	/**
	 * 常用日志明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysObjLog getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		SysObjLog sysObjLog=sysObjLogManager.get(id);
		return sysObjLog;
	}
	
	/**
	 * 保存常用日志信息
	 * @param request
	 * @param response
	 * @param sysObjLog
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysObjLog sysObjLog) throws Exception{
		String resultMsg=null;
		String id=sysObjLog.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysObjLog.setId(UniqueIdUtil.getSuid());
				sysObjLogManager.create(sysObjLog);
				resultMsg="添加常用日志成功";
			}else{
				sysObjLogManager.update(sysObjLog);
				resultMsg="更新常用日志成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对常用日志操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除常用日志记录
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
			sysObjLogManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除常用日志成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除常用日志失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
