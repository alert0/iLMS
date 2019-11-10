package com.hotent.mini.controller.bo;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.persistence.manager.BOEntManager;
import com.hotent.bo.persistence.manager.BOEntRelManager;
import com.hotent.bo.persistence.manager.BoAttributeManager;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BoAttribute;
import com.hotent.mini.web.json.PageJson;

/**
 * 
 * <pre>
 * 描述：业务对象定义 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:liyijun@jee-soft.cn
 * 日期:2016-03-31 14:47:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/bo/def/bOEnt/")
public class BOEntController extends GenericController {
	@Resource
	BOEntManager bOEntManager;
	@Resource
	BOEntRelManager boEntRelManager;
	@Resource
	BoAttributeManager boAttributeManager;

	/**
	 * 业务对象定义列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<BOEnt> bOEntList = (PageList<BOEnt>) bOEntManager.query(queryFilter);
		 
		PageList<JSONObject> pageList = new PageList<JSONObject>(bOEntList.getPageResult());
		JSONArray entList =  (JSONArray) JSON.toJSON(bOEntList);
		for (int i = 0; i < entList.size(); i++) {
			JSONObject obj = entList.getJSONObject(i);
			int r = bOEntManager.getCanEditByName(obj.getString("name"));
			obj.put("editable", r==0);
			pageList.add(obj);
		}
		
		return new PageJson(pageList);
	}

	/**
	 * 保存业务对象定义信息
	 * 
	 * @param request
	 * @param response
	 * @param bOEnt
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());
		String resultMsg = null;
		try {
			resultMsg = "保存实体成功";
			bOEntManager.save(json);
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "保存实体失败!", e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除业务对象定义记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			bOEntManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除对象成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("getObject")
	public @ResponseBody BOEnt getObject(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isNotEmpty(id)) {
			return bOEntManager.get(id);
		}
		String name = RequestUtil.getString(request, "name");
		if (StringUtil.isNotEmpty(name)) {
			return bOEntManager.getByName(name);
		}
		return null;
	}

	@RequestMapping("getAttrList")
	public @ResponseBody List<BoAttribute> getAttrList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String entId = RequestUtil.getString(request, "entId");
		if (StringUtil.isNotEmpty(entId)) {
			List<BoAttribute> list = boAttributeManager.getByEntId(entId);
			return list;
		}
		return null;
	}

	@RequestMapping("createTable")
	public void createTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String id = RequestUtil.getString(request, "id");
			BOEnt boEnt = bOEntManager.getById(id);
			bOEntManager.createTable(boEnt);
			message = new ResultMessage(ResultMessage.SUCCESS, "生成表成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("createTableByName")
	public void createTableByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String name = RequestUtil.getString(request, "name");
			BOEnt boEnt = bOEntManager.getByName(name);
			if(BeanUtils.isNotEmpty(boEnt) && !boEnt.isCreatedTable()){
				bOEntManager.createTable(boEnt);
				message = new ResultMessage(ResultMessage.SUCCESS, "生成表成功");
			}else{
				message = new ResultMessage(ResultMessage.SUCCESS, "该实体不存在或已生成表");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("setStatus")
	public void setStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String id = RequestUtil.getString(request, "id");
			String status = RequestUtil.getString(request, "status","enabled");
			BOEnt boEnt = bOEntManager.getById(id);
			boEnt.setStatus(status);
			bOEntManager.update(boEnt);
			message = new ResultMessage(ResultMessage.SUCCESS, "更改状态成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String name = RequestUtil.getString(request, "key");
		if (StringUtil.isNotEmpty(name)) {
			BOEnt temp = bOEntManager.getByName(name);// 数据库中用这个别名的对象
			if (temp == null) {
				return false;
			}
			return !temp.getId().equals(id);// 如果id跟数据库中用这个别名的对象一样就返回false，反之true
		}
		return false;
	}

	@RequestMapping("isEditabled")
	public @ResponseBody boolean isEditabled(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return true;
		}
		BOEnt ent = bOEntManager.get(id);
		int r = bOEntManager.getCanEditByName(ent.getName());
		return r == 0;
	}
	
	@RequestMapping("reload")
	public @ResponseBody ResultMessage reload(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		ResultMessage result=ResultMessage.getSuccess();
		try{
			bOEntManager.reloadByEntId(id);
		}
		catch(Exception ex){
			result=ResultMessage.getFail(ex.getMessage());
		}
		return result;
	}
}
