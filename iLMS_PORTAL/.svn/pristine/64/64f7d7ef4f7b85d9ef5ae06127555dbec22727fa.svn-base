package com.hotent.mini.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

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
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.SysDataSourceDefManager;
import com.hotent.sys.persistence.manager.SysDataSourceManager;
import com.hotent.sys.persistence.model.SysDataSource;

/**
 * 
 * <pre>
 * 描述：sys_data_source管理
 * 构建组：x5-bpmx-platform
 * 作者:liyj_aschs
 * 邮箱:liyj_aschs@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/sysDataSource/")
public class SysDataSourceController extends GenericController {
	@Resource
	SysDataSourceManager sysDataSourceManager;
	@Resource
	SysDataSourceDefManager sysDataSourceDefManager;

	/**
	 * sys_data_source列表(分页条件查询)数据 TODO方法名称描述
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
		PageList<SysDataSource> dataSourceList = (PageList<SysDataSource>) sysDataSourceManager.query(queryFilter);
		return new PageJson(dataSourceList);
	}

	/**
	 * 编辑sys_data_source信息页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysDataSourceEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		SysDataSource dataSource = null;
		if (StringUtil.isNotEmpty(id)) {
			dataSource = sysDataSourceManager.get(id);
		}
		return getAutoView().addObject("dataSource", dataSource).addObject("returnUrl", preUrl);
	}

	/**
	 * sys_data_source明细页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("sysDataSourceGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		SysDataSource dataSource = null;
		if (StringUtil.isNotEmpty(id)) {
			dataSource = sysDataSourceManager.get(id);
		}
		return getAutoView().addObject("dataSource", dataSource).addObject("returnUrl", preUrl);
	}

	/**
	 * 保存sys_data_source信息
	 * 
	 * @param request
	 * @param response
	 * @param dataSource
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());


		// 把fields字段变成字符串格式
		JSONObject jsonObject = JSONObject.fromObject(json);
		String str = "\"" + jsonObject.get("settingJson") + "\"";// 关键是要加""
		jsonObject.remove("settingJson");
		jsonObject.accumulate("settingJson", str);

		SysDataSource sysDataSource = (SysDataSource) JSONObject.toBean(jsonObject, SysDataSource.class);

		String resultMsg = null;
		try {
			if (StringUtil.isEmpty(sysDataSource.getId())) {
				sysDataSource.setId(UniqueIdUtil.getSuid());
				sysDataSourceManager.create(sysDataSource);
				resultMsg = "添加成功";
			} else {
				sysDataSourceManager.update(sysDataSource);
				resultMsg = "更新成功";
			}
			// 更新了数据同时也更新beans容器的数据源
			if (sysDataSource.getEnabled()) {
				DataSource dataSource=	sysDataSourceManager.getDsFromSysSource(sysDataSource);
				DataSourceUtil.addDataSource(sysDataSource.getAlias(), dataSource,true);
			}
		} catch (Exception e) {
			resultMsg = e.getMessage();
		}

		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);

	}

	/**
	 * 批量删除sys_data_source记录 TODO方法名称描述
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
			sysDataSourceManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
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
	public SysDataSource getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		SysDataSource dataSource = null;
		if (StringUtil.isNotEmpty(id)) {
			dataSource = sysDataSourceManager.get(id);
		}
		return dataSource;
	}

	/**
	 * 改变当前的数据源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("checkConnection")
	public void checkConnection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());

		// 把fields字段变成字符串格式
		JSONObject jsonObject = JSONObject.fromObject(json);
		String str = "\"" + jsonObject.get("settingJson") + "\"";// 关键是要加""
		jsonObject.remove("settingJson");
		jsonObject.accumulate("settingJson", str);

		SysDataSource sysDataSource = (SysDataSource) JSONObject.toBean(jsonObject, SysDataSource.class);

		boolean b = false;
		try {
			b = sysDataSourceManager.checkConnection(sysDataSource);
		} catch (Exception e) {
		}

		String resultMsg = "";

		if (b) {
			resultMsg = sysDataSource.getName() + ":连接成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} else {
			resultMsg = sysDataSource.getName() + ":连接失败";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
		}
	}
	
	/**
	 * 获取在容器的数据源
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             List<SysDataSource>
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("getDataSourcesInBean")
	@ResponseBody
	public List<SysDataSource> getDataSourcesInBean(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 获取容器中的配置数据源
		List<SysDataSource> result = sysDataSourceManager.getSysDataSourcesInBean();

		// 把默认的数据源也加载进去
		result.add(sysDataSourceManager.getDefaultDataSource());

		return result;
	}
	
}
