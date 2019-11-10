package com.hotent.biz.meeting.controller;




import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.biz.meeting.manager.MeetingroomManager;
import com.hotent.biz.meeting.model.Meetingroom;



/**
 * 
 * <pre> 
 * 描述：会议室 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 16:15:07
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/business/meeting/meetingroom")
public class MeetingroomController extends GenericController{
	@Resource
	MeetingroomManager meetingroomManager;
	@Resource
	private BpmDefUserManager bpmDefUserManager;
	/**
	 * 会议室列表(分页条件查询)数据
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
		PageList<Meetingroom> meetingroomList=(PageList<Meetingroom>)meetingroomManager.query(queryFilter);
		return new PageJson(meetingroomList);
	}
	
	
	
	/**
	 * 会议室明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Meetingroom getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new Meetingroom();
		}
		Meetingroom meetingroom=meetingroomManager.get(id);
		return meetingroom;
	}
	
	/**
	 * 保存会议室信息
	 * @param request
	 * @param response
	 * @param meetingroom
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody Meetingroom meetingroom) throws Exception{
		String resultMsg=null;
		String id=meetingroom.getId();
		try {
			if(StringUtil.isEmpty(id)){
				meetingroom.setId(UniqueIdUtil.getSuid());
				meetingroomManager.create(meetingroom);
				resultMsg="添加会议室成功";
			}else{
				meetingroomManager.update(meetingroom);
				resultMsg="更新会议室成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对会议室操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String name = RequestUtil.getString(request, "key");
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("NAME_", name, QueryOP.EQUAL);
		if (StringUtil.isNotEmpty(name)) {
			List<Meetingroom> temp = meetingroomManager.query(queryFilter);// 数据库中用这个别名的对象
			if (BeanUtils.isEmpty(temp)) {
				return false;
			}
			return !temp.get(0).getId().equals(id);// 如果id跟数据库中用这个别名的对象一样就返回false，反之true
		}
		return false;
	}
	
	/**
	 * 批量删除会议室记录
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
			meetingroomManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除会议室成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除会议室失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 获取授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getColumnRights")
	public @ResponseBody JSONArray getColumnRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		return bpmDefUserManager.getRights(id,BpmDefUser.BPMDEFUSER_OBJ_TYPE.MEETING_ROOM_MANAGE);
	}
	
	
	/**
	 * 保存授权信息
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("saveColumnRights")
	public void saveColumnRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try { 
			String id = RequestUtil.getString(request, "id");
			String ownerNameJson = RequestUtil.getString(request, "rightsData");
			bpmDefUserManager.saveRights(id, BpmDefUser.BPMDEFUSER_OBJ_TYPE.MEETING_ROOM_MANAGE, ownerNameJson);
			message = new ResultMessage(ResultMessage.SUCCESS,"权限保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "权限保存失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
