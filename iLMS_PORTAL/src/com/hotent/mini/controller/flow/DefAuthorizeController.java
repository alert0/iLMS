package com.hotent.mini.controller.flow;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.BpmDefAuthorize;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * <pre>
 * 对象功能:流程分管授权  控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:39:26
 * </pre>
 */
@Controller
@RequestMapping("/flow/defAuthorize/")
public class  DefAuthorizeController extends GenericController{
	
	@Resource
	private BpmDefAuthorizeManager bpmDefAuthorizeManager;	
	
	@Resource
	private BpmDefinitionManager bpmDefinitionManager;	
	
	
	
	
	/**
	 * 取得流程定义权限列表
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter filter = getQueryFilter(request);		
		PageList<BpmDefAuthorize> bpmDefAuthorizeList = (PageList<BpmDefAuthorize>) bpmDefAuthorizeManager.getAuthorizeListByFilter(filter);
		return new PageJson(bpmDefAuthorizeList);
	}
	
	
	/**
	 * 新增修改授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defAuthorizeJson")
	@ResponseBody
	public BpmDefAuthorize edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=RequestUtil.getString(request,"id");
		BpmDefAuthorize bpmDefAuthorize = null;
		if(StringUtil.isNotEmpty(id)){
			bpmDefAuthorize = bpmDefAuthorizeManager.getAuthorizeById(id);
		}else{
			bpmDefAuthorize =new BpmDefAuthorize();
		}
		return bpmDefAuthorize;
	}
	
	
	/**
	 * 保存新增或修改授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,
			HttpServletResponse response,@RequestBody BpmDefAuthorize author) throws Exception {
		//用户
		IUser user = ContextUtil.getCurrentUser();
		String userId = user.getUserId();
		
		String id=author.getId();
		//用ID判断是修改还是新增
		if(StringUtil.isNotEmpty(id)){
			author.setId(id);
			
		}else{
			author.setId("");
			author.setCreateTime(new Date());
			//增加流程分管授权查询判断
			author.setCreateBy(userId);
			author.setCreator(user.getFullname());
		}
		
		
		String myId = bpmDefAuthorizeManager.saveOrUpdateAuthorize(author);
		if(StringUtil.isNotEmpty(myId)){
			writeResultMessage(response.getWriter(),"保存授权信息成功！",ResultMessage.SUCCESS);
		}else{
			writeResultMessage(response.getWriter(),"保存授权信息失败！",ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 删除流程分管授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String[] lAryId =RequestUtil.getStringAryByStr(request, "id");
			bpmDefAuthorizeManager.deleteAuthorizeByIds(lAryId);
			String str = "删除流程分管授权信息成功！";
		
			writeResultMessage(response.getWriter(),str,ResultMessage.SUCCESS);
		}catch(Exception ex){
			writeResultMessage(response.getWriter(),"删除流程分管授权信息失败！"+ ex.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 获得流程分管授权详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defAuthorizeGet")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=RequestUtil.getString(request,"id");
		BpmDefAuthorize bpmDefAuthorize = null;
		if(StringUtil.isNotEmpty(id)){
			bpmDefAuthorize = bpmDefAuthorizeManager.getAuthorizeById(id);
		}else{
			bpmDefAuthorize =new BpmDefAuthorize();
		}
		return getAutoView().addObject("bpmDefAuthorize",bpmDefAuthorize);
	}
	
	
	
	/**
	 * 取得流程定义明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defAuthorizeActDetail")
	public ModelAndView actDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String preUrl= RequestUtil.getPrePage(request);
		String defId=RequestUtil.getString(request, "defId");
		
		DefaultBpmDefinition bpmDefinition=null;
		if(StringUtil.isEmpty(defId)){
			String defKey = RequestUtil.getString(request, "defKey","");
			bpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey, true);
		}else{			
			bpmDefinition = bpmDefinitionManager.get(defId);
		}
		
		return getAutoView().addObject("bpmDefinition", bpmDefinition)
				.addObject("returnUrl", preUrl);
	}
	
	
	
	
}
