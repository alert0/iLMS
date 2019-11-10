package com.hanthink.mp.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.manager.MpWeekPlanManager;
import com.hanthink.mp.model.MpWeekPlanModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：周计划维护 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpWeekPlan")
public class MpWeekPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpWeekPlanController.class);
	
	@Resource
	MpWeekPlanManager mpWeekPlanManager;
	  		
	/**
     * 分页查询周计划维护
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse, MpWeekPlanModel model) {
//    	String resultMsg=null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpWeekPlanModel> pageList = (PageList<MpWeekPlanModel>) mpWeekPlanManager.queryMpWeekPlanForPage(model,
					p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 保存周计划维护信息
	 * @param request
	 * @param response
	 * @param MpWeekPlan
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("update")
	public void update(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MpWeekPlanModel mpWeekPlanModel) throws Exception{
		String resultMsg = null;
		String id = mpWeekPlanModel.getId();
		try {
			mpWeekPlanModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
			mpWeekPlanManager.updateAndLog(mpWeekPlanModel, RequestUtil.getIpAddr(request));
			resultMsg="更新周计划维护成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
            resultMsg = "修改失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 获取年份填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("getYear")
	public @ResponseBody List<DictVO> getYear(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpWeekPlanManager.getYear();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
}
