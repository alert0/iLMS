package com.hotent.mini.controller.org;


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
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.SysDemensionManager;
import com.hotent.org.persistence.model.SysDemension;


/**
 * 
 * <pre> 
 * 描述：维度管理 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-19 15:30:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/sysDemension")
public class SysDemensionController extends GenericController{
	@Resource
	SysDemensionManager sysDemensionManager;
	
	/**
	 * 维度管理列表(分页条件查询)数据
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
		PageList<SysDemension> sysDemensionList=(PageList<SysDemension>)sysDemensionManager.query(queryFilter);
		return new PageJson(sysDemensionList);
	}
	
	
	
	/**
	 * 维度管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysDemension getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new SysDemension();
		}
		SysDemension sysDemension=sysDemensionManager.get(id);
		return sysDemension;
	}
	
	/**
	 * 保存维度管理信息
	 * @param request
	 * @param response
	 * @param sysDemension
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysDemension sysDemension) throws Exception{
		String resultMsg=null;
		String id=sysDemension.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysDemension.setId(UniqueIdUtil.getSuid());
				sysDemensionManager.create(sysDemension);
				resultMsg="添加组织维度成功";
			}else{
				sysDemensionManager.update(sysDemension);
				resultMsg="更新组织维度成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对组织维度操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除维度管理记录
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
			sysDemensionManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除组织维度成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除组织维度失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String name = RequestUtil.getString(request, "key");
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("dem_name_", name, QueryOP.EQUAL);
		if (StringUtil.isNotEmpty(name)) {
			List<SysDemension> temp = sysDemensionManager.query(queryFilter);// 数据库中用这个别名的对象
			if (BeanUtils.isEmpty(temp)) {
				return false;
			}
			return !temp.get(0).getId().equals(id);// 如果id跟数据库中用这个别名的对象一样就返回false，反之true
		}
		return false;
	}
	
	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			String id = RequestUtil.getString(request, "id");
			sysDemensionManager.setDefaultDemension(id);
			writeResultMessage(response.getWriter(), "设置默认维度成功!", ResultMessage.SUCCESS);
		}
		catch(Exception ex){
			writeResultMessage(response.getWriter(), "设置默认维度失败!", ResultMessage.FAIL);
		}
	}
}
