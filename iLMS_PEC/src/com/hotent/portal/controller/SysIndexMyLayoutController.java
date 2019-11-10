package com.hotent.portal.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.portal.persistence.manager.SysIndexColumnManager;
import com.hotent.portal.persistence.manager.SysIndexLayoutManager;
import com.hotent.portal.persistence.manager.SysIndexMyLayoutManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexLayout;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：sys_index_layout 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysIndexMyLayout")
public class SysIndexMyLayoutController extends GenericController {
	@Resource
	private SysIndexLayoutManager sysIndexLayoutManager;
	@Resource
	private SysIndexMyLayoutManager sysIndexMyLayoutManager;
	@Resource
	protected SysIndexColumnManager sysIndexColumnManager;
	/**
	 * 设计我的首页布局
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("design")

	public ModelAndView design(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId =ContextUtil.getCurrentUserId();
		//首页布局
		List<SysIndexLayout> list = sysIndexLayoutManager.getAll();
		DefaultQueryFilter queryFilter=(DefaultQueryFilter) getQueryFilter(request);
		// 不限制取值
		DefaultPage page = (DefaultPage) queryFilter.getPage();
		page.setLimit(Integer.MAX_VALUE);
		queryFilter.setPage(page);
		queryFilter.addFilter("IS_PUBLIC", SysIndexColumn.TYPE_PC, QueryOP.EQUAL);
//		Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request, false, false);
		Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request);
		//首页栏目，true 取出来是已经解析了
		List<SysIndexColumn>  columnList = sysIndexColumnManager.getHashRightColumnList(queryFilter,params,true,SysIndexColumn.TYPE_PC);
		//获取展示的布局
		Map<String,List<SysIndexColumn>> columnMap = sysIndexColumnManager.getColumnMap(columnList);
		//获取当前的布局
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutManager.getLayoutList(userId,columnList);
		
		// 解码   columnMap 在sysIndexColumnManager.getColumnMap 中解码
		for (SysIndexLayout sysIndexLayout : list) {
			sysIndexLayout.setTemplateHtml(Base64.getFromBase64(sysIndexLayout.getTemplateHtml()));
		}
				
		return getAutoView().addObject("layoutList",list)
						.addObject("columnMap",columnMap)
						.addObject("sysIndexMyLayout", sysIndexMyLayout);
		
		
	}
	
	
	/**
	 * 保存首页布局
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveLayout")
	public void saveLayout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String html = com.hotent.base.core.web.RequestUtil.getString(request, "html");
		String designHtml = com.hotent.base.core.web.RequestUtil.getString(request, "designHtml");
		ResultMessage resultObj = null;
		try {
			
			sysIndexMyLayoutManager.save(html,designHtml);
			resultObj = new ResultMessage(ResultMessage.SUCCESS,"保存成功");	
		} catch (Exception e) {
			e.printStackTrace();
			resultObj = new ResultMessage(ResultMessage.FAIL, e.getMessage());	
		}
		 response.getWriter().print(resultObj);
		}
	
	
	/**
	 * 删除首页布局
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("deleteLayout")
	public void deleteLayout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ResultMessage resultObj = null;
		String[] id=com.hotent.base.core.web.RequestUtil.getStringAryByStr(request, "id");
	try {
		sysIndexMyLayoutManager.removeByIds(id);
		resultObj = new ResultMessage(ResultMessage.SUCCESS,"删除成功");	
	} catch (Exception e) {
		e.printStackTrace();
		resultObj = new ResultMessage(ResultMessage.FAIL, e.getMessage());	
	}
	 response.getWriter().print(resultObj);
	}

}
