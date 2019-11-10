package com.hanthink.sps.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.controller.MpResidualController;
import com.hanthink.sps.manager.SpsPadCheckManager;
import com.hanthink.sps.model.SpsPadCheckModel;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: SpsPadCheckController
 * @Description: pad无纸化分拣查询
 * @author dtp
 * @date 2018年10月16日
 */
@Controller
@RequestMapping("/sps/spsPadCheck")
public class SpsPadCheckController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(MpResidualController.class);
	@Resource
	private SpsPadCheckManager spsPadCheckManager;
	
	/**
	 * @Description: pad无纸化分拣查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("querySpsPadCheckPage")
	public @ResponseBody PageJson querySpsPadCheckPage(HttpServletRequest request,HttpServletResponse response, 
		  SpsPadCheckModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<SpsPadCheckModel> pageList = spsPadCheckManager.querySpsPadCheckPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 获取票据模板名称填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("getMouldName")
	public @ResponseBody List<DictVO> getUnloadPort(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = spsPadCheckManager.getMouldName();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
}
