package com.hotent.mini.controller.form;

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
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.form.persistence.manager.CombinateDialogManager;
import com.hotent.form.persistence.model.CombinateDialog;

/**
 * 
 * <pre>
 * 描述：组合对话框 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:liyijun@jee-soft.cn
 * 日期:2016-03-04 15:48:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/form/combinateDialog/")
public class CombinateDialogController extends GenericController {
	@Resource
	CombinateDialogManager combinateDialogManager;

	/**
	 * 组合对话框列表(分页条件查询)数据
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
		PageList<CombinateDialog> combinateDialogList = (PageList<CombinateDialog>) combinateDialogManager.query(queryFilter);
		return new PageJson(combinateDialogList);
	}

	/**
	 * 编辑组合对话框信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 */
	@RequestMapping("combinateDialogEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		CombinateDialog combinateDialog = null;
		if (StringUtil.isNotEmpty(id)) {
			combinateDialog = combinateDialogManager.get(id);
		}
		return getAutoView().addObject("combinateDialog", combinateDialog).addObject("returnUrl", preUrl);
	}

	/**
	 * 获取一个对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("getObject")
	@ResponseBody
	public Object getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isNotEmpty(id)) {
			return combinateDialogManager.get(id);
		}
		String alias = RequestUtil.getString(request, "alias");
		if (StringUtil.isNotEmpty(alias)) {
			return combinateDialogManager.getByAlias(alias);
		}
		return null;
	}

	/**
	 * 保存组合对话框信息
	 * 
	 * @param request
	 * @param response
	 * @param combinateDialog
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());
		CombinateDialog combinateDialog = GsonUtil.toBean(json, CombinateDialog.class);
		
		String resultMsg = null;
		String id = combinateDialog.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				combinateDialog.setId(UniqueIdUtil.getSuid());
				combinateDialogManager.create(combinateDialog);
				resultMsg = "添加组合对话框成功";
			} else {
				combinateDialogManager.update(combinateDialog);
				resultMsg = "更新组合对话框成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对组合对话框操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除组合对话框记录
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
			combinateDialogManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除组合对话框成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除组合对话框失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
