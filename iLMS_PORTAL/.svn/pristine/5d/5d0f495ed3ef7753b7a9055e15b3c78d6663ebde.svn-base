package com.hotent.mini.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.persistence.manager.SysObjLogManager;
import com.hotent.sys.persistence.manager.SysTransDefManager;
import com.hotent.sys.persistence.model.SysObjLog;
import com.hotent.sys.persistence.model.SysTransDef;
import com.hotent.sys.persistence.util.SysObjLogUtil;

/**
 * <pre>
 * 对象功能:sys_trans_def 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-04-16 11:15:55
 * </pre>
 */
@Controller
@RequestMapping("/system/sysTransDef/")
public class SysTransDefController extends GenericController {
	@Resource
	private SysTransDefManager sysTransDefManager;
	@Resource
	private IUserService userService;
	@Resource
	private  SysObjLogManager sysObjLogManager;

	/**
	 * 添加或更新sys_trans_def。
	 * 
	 * @param request
	 * @param response
	 * @param sysTransDef
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());
		SysTransDef sysTransDef = JSONObject.parseObject(json, SysTransDef.class);
		String resultMsg = null;
		try {
			if (StringUtil.isEmpty(sysTransDef.getId())) {
				resultMsg = "添加";
			} else {
				resultMsg = "更新";
			}
			sysTransDefManager.save(sysTransDef);
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 取得sys_trans_def分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<SysTransDef> sysServiceSetList=(PageList<SysTransDef>)sysTransDefManager.query(queryFilter);
		return new PageJson(sysServiceSetList);
	}

	/**
	 * 删除sys_trans_def
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] lAryId = RequestUtil.getStringAryByStr(request, "id");
			sysTransDefManager.removeByIds(lAryId);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除sys_trans_def成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败" + ex.getMessage());
		}
		writeResultMessage(response.getWriter(), message);

	}

	@RequestMapping("getObject")
	@ResponseBody
	public SysTransDef getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isNotEmpty(id)) {
			return sysTransDefManager.get(id);
		}
		return null;
	}

	@RequestMapping("treeListJson")
	@ResponseBody
	public List<JSONObject> treeListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("state_", "1",QueryOP.EQUAL);
		List<SysTransDef> list = sysTransDefManager.query(queryFilter);
		String authorId = RequestUtil.getString(request, "authorId", "");
		return sysTransDefManager.treeListJson(list, authorId);
	}

	@RequestMapping("excuteSelectSql")
	@ResponseBody
	public List<Map<String, Object>> excuteSelectSql(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		String authorId = RequestUtil.getString(request, "authorId", "");
		SysTransDef sysTransDef = sysTransDefManager.get(id);
		
		List<Map<String, Object>> mapList = sysTransDefManager.excuteSelectSql(sysTransDef, authorId);
		
		/**oracle 字段改成小写 */
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> dataMap : mapList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (String key : dataMap.keySet()) {
				resultMap.put(key.toLowerCase(),dataMap.get(key));
			}
			resultList.add(resultMap);
		}
		
		return resultList;
	}

	@RequestMapping("excuteUpdateSql")
	@ResponseBody
	public void excuteUpdateSql(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String authorId = RequestUtil.getString(request, "authorId", "");
		String targetPersonId = RequestUtil.getString(request, "targetPersonId", "");
		String selectedItem = RequestUtil.getString(request, "selectedItem", "");

		IUser author =userService.getUserById(authorId);
		IUser targetPerson =userService.getUserById(targetPersonId);
		
		SysTransDef sysTransDef = sysTransDefManager.get(id);
		try {
			sysTransDefManager.excuteUpdateSql(sysTransDef, author, targetPerson, selectedItem);
			writeResultMessage(response.getWriter(),"操作成功", ResultMessage.SUCCESS);
			String content = sysTransDefManager.getLogContent(sysTransDef, authorId, targetPersonId, selectedItem);
			
			SysObjLogUtil.add("权限转移", content ,SysTransDef.class.getSimpleName());
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "操作失败:"+e.getMessage(), ResultMessage.FAIL);
		}
	}
	
}


