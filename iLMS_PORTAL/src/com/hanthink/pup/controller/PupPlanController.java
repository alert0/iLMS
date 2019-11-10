package com.hanthink.pup.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.manager.PupPlanManager;
import com.hanthink.pup.model.PupPlanModel;
import com.hanthink.pup.model.PupPlanPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
/**
 * <pre> 
 * 功能描述:取货计划查询控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

@RequestMapping("/pup/getPlan")
@Controller
public class PupPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupPlanController.class);
	
	@Resource
	private PupPlanManager planManager;
	/**
	 * 取货计划查询业务控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/listPlan")
	public @ResponseBody PageJson queryPlanForPage(HttpServletRequest request,HttpServletResponse response,
													PupPlanPageModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<PupPlanModel> list = planManager.queryPlanForPage(model,page);
		return new PageJson(list);
	}
	/**
	 * 数据字典项下载状态加载控制器
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/getDownloadStatus")
	public @ResponseBody Object getDiffFlag()throws Exception {
		try {
			List<DictVO> models = planManager.getDownloadStatus();
			return new PageJson(models);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return null;
		}
	}
	/**
	 * 单条/批量删除数据控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/remove")
	public void deleteById(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String[] ids = RequestUtil.getStringAryByStr(request, "id");
		String resultMsg = null;
		try {
			planManager.deletePlansById(ids);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "删除失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
}
