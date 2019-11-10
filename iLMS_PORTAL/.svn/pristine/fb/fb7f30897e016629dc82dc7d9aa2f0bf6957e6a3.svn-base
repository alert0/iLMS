package com.hotent.mini.controller.system;


import java.util.List;

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
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.persistence.manager.IdentityManager;
import com.hotent.sys.persistence.model.Identity;

/**
 * 
 * <pre> 
 * 描述：流水号生成管理
 * 构建组：x5-bpmx-platform
 * 作者:zyp
 * 邮箱:zyp@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/identity/")
public class IdentityController extends GenericController{
	@Resource
	IdentityManager identityManager;
	@Resource
	IdentityService identityService;
	
	
	/**
	 * 流水号生成列表(分页条件查询)数据
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<Identity> identityList=(PageList<Identity>)identityManager.query(queryFilter);
		return new PageJson(identityList);
	}
	

	/**
	 * 根据ID获取内容
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getById")
	@ResponseBody
	public Object getById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		Identity identity = identityManager.get(id);
		return identity;
	}
	
	@RequestMapping("identityDialog")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return getAutoView();
	}
	
	/**
	 * 流水号生成明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("identityGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		Identity identity=null;
		if(StringUtil.isNotEmpty(id)){
			identity=identityManager.get(id);
		}
		return getAutoView().addObject("identity", identity);
	}
	
	/**
	 * 保存流水号生成信息
	 * @param request
	 * @param response
	 * @param identity
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody Identity identity) throws  Exception{
		String resultMsg=null;
		
		boolean rtn=identityManager.isAliasExisted(identity.getId(),identity.getAlias());
		if (rtn) {
			resultMsg="别名已经存在!";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			return;
		}
		
		try {
			if(StringUtil.isEmpty(identity.getId())){
				identityManager.create(identity);
				resultMsg="添加流水号生成成功";
			}else{
				identityManager.update(identity);
				resultMsg="更新流水号生成成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) { 
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 批量删除流水号生成记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			identityManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除流水号生成成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除流水号生成失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 取得流水号生成分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showlist")
	public @ResponseBody PageJson showlist(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<Identity> identityList=(PageList<Identity>)identityManager.query(queryFilter);
		return new PageJson(identityList);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("preview")
	public  @ResponseBody PageJson preview(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
 		String alias = RequestUtil.getString(request, "alias");
		List<Identity> identities = identityManager.getPreviewIden(alias);
		
		return new PageJson(identities);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNextIdByAlias") 
	public  @ResponseBody String getNextIdByAlias(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String alias = RequestUtil.getString(request, "alias");
		if(identityManager.isAliasExisted(null, alias)){
			String nextId = identityService.genNextNo(alias);
			return nextId;
		}
		return "";
	}
}
