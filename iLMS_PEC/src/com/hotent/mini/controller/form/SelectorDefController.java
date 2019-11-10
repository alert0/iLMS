package com.hotent.mini.controller.form;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmSelectorDefManager;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;
/**
 * 
 * <pre>
 * 描述：控件组合定义管理
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-9-28-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/selectorDef/")
public class SelectorDefController extends GenericController {
	@Resource
	BpmSelectorDefManager bpmSelectorDefManager;

	/**
	 * 控件组合定义列表(分页条件查询)数据 TODO方法名称描述
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<BpmSelectorDef> bpmSelectorDefList = (PageList<BpmSelectorDef>) bpmSelectorDefManager
				.query(queryFilter);
		return new PageJson(bpmSelectorDefList);
	}
	
	@RequestMapping("getAll")
	@ResponseBody
	public List<BpmSelectorDef> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BpmSelectorDef> bpmSelectorDefs = bpmSelectorDefManager.getAll();
		return bpmSelectorDefs;
	}
	
	/**
	 * 编辑控件组合定义
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 */
	@RequestMapping("selectorDefEdit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		return getAutoView().addObject("id", id)
							.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 获取控件的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("selectorData")
	@ResponseBody
	public Object selectorData(HttpServletRequest request, HttpServletResponse response,@RequestBody Map map) throws Exception {
		String id = getString(map, "id", "");
		BpmSelectorDef bpmSelectorDef = null;
		if (StringUtil.isNotEmpty(id)) {
			bpmSelectorDef = bpmSelectorDefManager.get(id);
		}
		return bpmSelectorDef;
	}

	/**
	 * 控件组合定义明细页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 */
	@RequestMapping("selectorDefGet")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		BpmSelectorDef bpmSelectorDef = null;
		if (StringUtil.isNotEmpty(id)) {
			bpmSelectorDef = bpmSelectorDefManager.get(id);
		}
		return getAutoView().addObject("bpmSelectorDef", bpmSelectorDef)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @exception
	 */
	@RequestMapping("getById")
	public @ResponseBody
	BpmSelectorDef getById(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		String id = RequestUtil.getString(request, "id");
		BpmSelectorDef bpmSelectorDef = null;
		if (StringUtil.isNotEmpty(id)) {
			bpmSelectorDef = bpmSelectorDefManager.get(id);
		}
		return bpmSelectorDef;
	}
	/**
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @exception
	 */
	@RequestMapping("getByAlias")
	public @ResponseBody
	BpmSelectorDef getByAlias(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		BpmSelectorDef bpmSelectorDef = null;
		if (StringUtil.isNotEmpty(alias)) {
			bpmSelectorDef = bpmSelectorDefManager.getByAlias(alias);
		}
		return bpmSelectorDef;
	}

	/**
	 * 保存控件组合定义信息
	 * 
	 * @param request
	 * @param response
	 * @param bpmSelectorDef
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,@RequestBody BpmSelectorDef bpmSelectorDef) throws Exception {
		String resultMsg = null;
		String id = bpmSelectorDef.getId();
		try {
			boolean b = bpmSelectorDefManager.isExistAlias(bpmSelectorDef.getAlias(),id);
			if(b){
				return getResult(false,"重复的别名,请检查!");
			}
				
			if (StringUtil.isEmpty(id)) {
				bpmSelectorDef.setId(UniqueIdUtil.getSuid());
				bpmSelectorDefManager.create(bpmSelectorDef);
				resultMsg = "添加控件组合定义成功";
			} else {
				bpmSelectorDefManager.update(bpmSelectorDef);
				resultMsg = "更新控件组合定义成功";
			}
			return getResult(true,resultMsg);
		} catch (Exception e) {
			resultMsg = ExceptionUtils.getRootCauseMessage(e);
			return getResult(false,resultMsg);
		}
	}

	/**
	 * 批量删除控件组合定义记录 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			bpmSelectorDefManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除控件组合定义成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除控件组合定义失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
