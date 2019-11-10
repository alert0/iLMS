package com.hotent.mini.controller.org;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.Role;

/**
 * <pre>
 * 描述：组织关系定义 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-29 18:00:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/orgReldef")
public class OrgReldefController extends GenericController {
	@Resource
	OrgReldefManager orgReldefManager;

	/**
	 * 组织关系定义列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<OrgReldef> orgReldefList = (PageList<OrgReldef>) orgReldefManager.query(queryFilter);
		return new PageJson(orgReldefList);
	}
	@RequestMapping("getAllJson")
	public @ResponseBody List<OrgReldef> getAllJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		List<OrgReldef> orgReldefList = orgReldefManager.getAll();
		return orgReldefList;
	}
	/**
	 * 组织关系定义明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody OrgReldef getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		OrgReldef orgReldef = orgReldefManager.get(id);
		return orgReldef;
	}
	/**
	 * 保存组织关系定义信息
	 * @param request
	 * @param response
	 * @param orgReldef
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody OrgReldef orgReldef) throws Exception {
		String resultMsg = null;
		String id = orgReldef.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				orgReldef.setId(UniqueIdUtil.getSuid());
				orgReldefManager.create(orgReldef);
				resultMsg = "添加职务定义成功";
			} else {
				orgReldefManager.update(orgReldef);
				resultMsg = "更新职务定义成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对职务定义操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 批量删除组织关系定义记录
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			orgReldefManager.removeByIds(aryIds);
			if(StringUtil.isEmpty(ThreadMsgUtil.getMessage(false))){
				message = new ResultMessage(ResultMessage.SUCCESS, "删除职务定义成功");
			}else{
				message = new ResultMessage(ResultMessage.FAIL, ThreadMsgUtil.getMessage());
			}
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除职务定义失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String code = RequestUtil.getString(request, "key");
		if (StringUtil.isNotEmpty(id))
			return false;
		if (StringUtil.isNotEmpty(code)) {
			OrgReldef temp = orgReldefManager.getByCode(code);
			return temp != null;
		}
		return false;
	}
}
