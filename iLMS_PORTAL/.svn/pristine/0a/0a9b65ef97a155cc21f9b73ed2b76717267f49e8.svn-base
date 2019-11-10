package com.hotent.mini.controller.base;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.persistence.manager.RelResourceManager;
import com.hotent.base.persistence.model.RelResource;


/**
 * 
 * <pre> 
 * 描述：关联资源 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:30:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/base/base/relResource")
public class RelResourceController extends GenericController{
	@Resource
	RelResourceManager relResourceManager;
	
	/**
	 * 关联资源列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<RelResource> relResourceList=(PageList<RelResource>)relResourceManager.query(queryFilter);
		return new PageJson(relResourceList);
	}
	
	
	
	/**
	 * 关联资源明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody List<RelResource> getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resId=RequestUtil.getString(request, "resId");
		if(StringUtil.isEmpty(resId)){
			return null;
		}
		List<RelResource> relResource= relResourceManager.getByResourceId(resId);
		return relResource;
	}
	
	/**
	 * 保存关联资源信息
	 * @param request
	 * @param response
	 * @param relResource
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody RelResource relResource) throws Exception{
		String resultMsg=null;
		String id=relResource.getId();
		try {
			if(StringUtil.isEmpty(id)){
				relResource.setId(UniqueIdUtil.getSuid());
				relResourceManager.create(relResource);
				resultMsg="添加关联资源成功";
			}else{
				relResourceManager.update(relResource);
				resultMsg="更新关联资源成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对关联资源操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除关联资源记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			relResourceManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除关联资源成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除关联资源失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
