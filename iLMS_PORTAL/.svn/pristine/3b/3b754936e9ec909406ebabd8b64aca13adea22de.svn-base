package com.hotent.mini.controller.system;

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
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.json.PageJson;
import com.hotent.sys.persistence.manager.SysDataSourceDefManager;
import com.hotent.sys.persistence.model.SysDataSourceDef;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * 描述：sys_data_source_pool管理
 * 构建组：x5-bpmx-platform
 * 作者:liyj_aschs
 * 邮箱:liyj_aschs@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/sysDataSourceDef/")
public class SysDataSourceDefController extends GenericController {
	@Resource
	SysDataSourceDefManager sysDataSourceDefManager;

	/**
	 * sys_data_source_pool列表(分页条件查询)数据 TODO方法名称描述
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<SysDataSourceDef> sysDataSourceDefList = (PageList<SysDataSourceDef>) sysDataSourceDefManager.query(queryFilter);
		return new PageJson(sysDataSourceDefList);
	}

	/**
	 * 编辑sys_data_source_pool信息页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysDataSourceDefEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		
		return getAutoView().addObject("returnUrl", preUrl);
	}

	/**
	 * sys_data_source_pool明细页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysDataSourceDefGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		SysDataSourceDef sysDataSourceDef = null;
		if (StringUtil.isNotEmpty(id)) {
			sysDataSourceDef = sysDataSourceDefManager.get(id);
		}
		return getAutoView().addObject("sysDataSourceDef", sysDataSourceDef).addObject("returnUrl", preUrl);
	}

	/**
	 * 保存sys_data_source_pool信息 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @param sysDataSourceDef
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String json = FileUtil.inputStream2String(request.getInputStream());

		// 把fields字段变成字符串格式
		JSONObject jsonObject = JSONObject.fromObject(json);
		String str = "\"" + jsonObject.get("settingJson") + "\"";// 关键是要加""
		jsonObject.remove("settingJson");
		jsonObject.accumulate("settingJson", str);

		SysDataSourceDef sysDataSourceDef = (SysDataSourceDef) JSONObject.toBean(jsonObject, SysDataSourceDef.class);

		String resultMsg = null;
		try {
			if (StringUtil.isEmpty(sysDataSourceDef.getId())) {
				sysDataSourceDef.setId(UniqueIdUtil.getSuid());
				sysDataSourceDefManager.create(sysDataSourceDef);
				resultMsg = "添加成功";
			} else {
				sysDataSourceDefManager.update(sysDataSourceDef);
				resultMsg = "更新成功";
			}
		} catch (Exception e) {
			resultMsg = e.getMessage();
		}

		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
	}

	/**
	 * 批量删除sys_data_source_pool记录 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			sysDataSourceDefManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除sys_data_source_pool成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除sys_data_source_pool失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 
	 * 根据类名获取Setter字段，以json的格式返回,json格式定义如下 [{"name":"属性名称","comment":"描述","type":"值类型","baseAttr":"是否基础属性（是否必填）0：false,1:true"},{...},...]
	 * 
	 * @param request
	 * @param response
	 *            void
	 * @return 
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("getSetterFields")
	@ResponseBody
	public JSONArray getSetterFields(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classPath = RequestUtil.getString(request, "classPath");
		return sysDataSourceDefManager.getHasSetterFieldsJsonArray(classPath);
	}

	/**
	 * 按照id返回对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             BpmDefExtProperties
	 */
	@RequestMapping("getById")
	@ResponseBody
	public SysDataSourceDef getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		SysDataSourceDef sysDataSourceDef = null;
		if (StringUtil.isNotEmpty(id)) {
			sysDataSourceDef = sysDataSourceDefManager.get(id);
		}
		return sysDataSourceDef;
	}
	
	/**
	 * 获取所有数据池
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             BpmDefExtProperties
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public List<SysDataSourceDef> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<SysDataSourceDef> list = sysDataSourceDefManager.getAll();
		
		return list;
	}
}
