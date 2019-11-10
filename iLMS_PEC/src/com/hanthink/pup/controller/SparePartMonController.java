package com.hanthink.pup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pup.manager.SparePartMonManager;
import com.hanthink.pup.model.SparePartMonModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * 
 * <pre> 
 * 功能描述: 备件出库看板控制器
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年12月20日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/part/sparePart")
@Controller
public class SparePartMonController extends GenericController{
	@Resource
	private SparePartMonManager sparePartManager;
	
	@RequestMapping("/querySpareForPage")
	public @ResponseBody PageJson querySparePartForPage(HttpServletRequest request,HttpServletResponse response,
															SparePartMonModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<SparePartMonModel> pageList = sparePartManager.querySparePartForPage(model,page);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/queryPartCheck")
	public @ResponseBody List<SparePartMonModel> queryPartCheck(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		String ipAddr = RequestUtil.getIpAddr(request);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", factoryCode);
		paramMap.put("ip", ipAddr);
		DefaultPage page = new DefaultPage(new RowBounds(1,6));
				
		return sparePartManager.queryPartCheck(paramMap,page);
	}
}
