package com.hotent.mini.controller.org;

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
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;

/**
 * <pre>
 * 描述：组织关联关系 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/orgRel")
public class OrgRelController extends GenericController {
	@Resource
	OrgRelManager orgRelManager;

	/**
	 * 组织关联关系列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String orgId = RequestUtil.getString(request, "orgId");
		if (StringUtil.isNotEmpty(orgId)) {
			queryFilter.addParamsFilter("orgId", orgId);
		}
		PageList<OrgRel> orgRelList = (PageList<OrgRel>) orgRelManager.queryInfoList(queryFilter);
		return new PageJson(orgRelList);
	}
	/**
	 * 组织关联关系明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody OrgRel getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		OrgRel orgRel = orgRelManager.get(id);
		return orgRel;
	}
	/**
	 * 保存组织关联关系信息
	 * @param request
	 * @param response
	 * @param orgRel
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody OrgRel orgRel) throws Exception {
		String resultMsg = null;
		String id = orgRel.getId();
		boolean success = true;
		try {
			if (StringUtil.isEmpty(id)) {
				orgRel.setId(UniqueIdUtil.getSuid());
				orgRelManager.create(orgRel);
				resultMsg = "添加岗位成功";
			} else {
				orgRelManager.update(orgRel);
				resultMsg = "更新岗位成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, success ? ResultMessage.SUCCESS : ResultMessage.FAIL);
		} catch (Exception e) {
			resultMsg = "对组织岗位操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 批量删除组织关联关系记录
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
			orgRelManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除组织岗位成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除组织岗位失败");
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
			OrgRel temp = orgRelManager.getByCode(code);
			return temp != null;
		}
		return false;
	}
}
