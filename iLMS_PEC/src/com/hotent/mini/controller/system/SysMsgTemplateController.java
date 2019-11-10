package com.hotent.mini.controller.system;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.persistence.dao.FileDao;
import com.hotent.sys.persistence.manager.SysMsgTemplateManager;
import com.hotent.sys.persistence.model.SysMsgTemplate;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：消息模版 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysMsgTemplate/")
public class SysMsgTemplateController extends GenericController{
	@Resource
	SysMsgTemplateManager sysMsgTemplateManager;
	@Resource
	IUserService userServiceImpl;
	@Resource
	IUserGroupService userGroupService;
	
	
	/**
	 * 消息模版列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		DefaultQueryFilter queryFilter= (DefaultQueryFilter) getQueryFilter(request);
		queryFilter.addFieldSort("create_time_",Direction.DESC.name());//添加已创建时间排序
		Collections.reverse(queryFilter.getFieldSortList());//将排序设置倒序，优先以创建时间排序
		PageList<SysMsgTemplate> sysMsgTemplateList=(PageList<SysMsgTemplate>)sysMsgTemplateManager.query(queryFilter);
		return new PageJson(sysMsgTemplateList);
	}
	
	/**
	 * 编辑消息模版信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 *//*
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
//		SysMsgTemplate sysMsgTemplate=null;
//		if(StringUtil.isNotEmpty(id)){
//			sysMsgTemplate=sysMsgTemplateManager.get(id);
//		}
		return getAutoView().addObject("returnUrl", preUrl).addObject("id",id);
	}*/
	
	/**
	 * 消息模版明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("sysMsgTemplateGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		SysMsgTemplate sysMsgTemplate=null;
		if(StringUtil.isNotEmpty(id)){
			sysMsgTemplate=sysMsgTemplateManager.get(id);
			String createBy = sysMsgTemplate.getCreateBy();
			String updateBy = sysMsgTemplate.getUpdateBy();
			String createOrgId = sysMsgTemplate.getCreateOrgId();
			if (StringUtil.isNotEmpty(createBy)) {
				IUser defaultUser = userServiceImpl.getUserById(createBy);
				if (BeanUtils.isNotEmpty(defaultUser)) {
					sysMsgTemplate.setCreateBy(defaultUser.getFullname());
				}
			}
			
			if (StringUtil.isNotEmpty(updateBy)) {
				IUser updateUser = userServiceImpl.getUserById(updateBy);
				if (BeanUtils.isNotEmpty(updateUser)) {
					sysMsgTemplate.setUpdateBy(updateUser.getFullname());
				}
			}

		}
		return getAutoView().addObject("sysMsgTemplate", sysMsgTemplate).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存消息模版信息
	 * @param request
	 * @param response
	 * @param sysMsgTemplate
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		int isSuccess= ResultMessage.SUCCESS;
		String template = RequestUtil.getString(request, "template");
		SysMsgTemplate sysMsgTemplate = (SysMsgTemplate) JSONObject.toBean(JSONObject.fromObject(template), SysMsgTemplate.class);
		String currentUserId = ContextUtil.getCurrentUserId();
		IGroup group =ContextUtil.getCurrentGroup();
		String currentGroupId = "0";
		if(group!= null)currentGroupId = group.getGroupId();
		try {
			boolean isExist = false;
			if(StringUtil.isEmpty(sysMsgTemplate.getId())){
				isExist = sysMsgTemplateManager.isExistByKeyAndTypeKey(sysMsgTemplate.getKey(),sysMsgTemplate.getTypeKey());
				if (isExist) {
					resultMsg="消息模版业务键已经存在,添加失败!";
					isSuccess=ResultMessage.FAIL;
				}else {
					sysMsgTemplate.setId(UniqueIdUtil.getSuid());
					sysMsgTemplate.setCreateBy(currentUserId);
					sysMsgTemplate.setCreateOrgId(currentGroupId);
					sysMsgTemplateManager.create(sysMsgTemplate);
					resultMsg="添加消息模版成功";
				}
			}else{
				SysMsgTemplate sysMsgTemplateTemp = sysMsgTemplateManager.get(sysMsgTemplate.getId());
				if (BeanUtils.isNotEmpty(sysMsgTemplateTemp)) {
					if(!sysMsgTemplateTemp.getKey().equals(sysMsgTemplate.getKey())){
						isExist = sysMsgTemplateManager.isExistByKeyAndTypeKey(sysMsgTemplate.getKey(),sysMsgTemplate.getTypeKey());
					}
				}else {
					isExist = sysMsgTemplateManager.isExistByKeyAndTypeKey(sysMsgTemplate.getKey(),sysMsgTemplate.getTypeKey());
				}
				if (isExist) {
					resultMsg="消息模版业务键已经存在,更新失败!";
					isSuccess=ResultMessage.FAIL;
				}else {
					sysMsgTemplate.setUpdateBy(currentUserId);
					sysMsgTemplateManager.update(sysMsgTemplate);
					resultMsg="更新消息模版成功";
				}
			}
			writeResultMessage(response.getWriter(),resultMsg,isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg="对消息模版操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除消息模版记录
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
			sysMsgTemplateManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除消息模版成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除消息模版失败");
		}
		writeResultMessage(response.getWriter(), message);
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
		SysMsgTemplate sysMsgTemplate = sysMsgTemplateManager.get(id);
		return sysMsgTemplate;
	}
	
	/**
	 * 根据ID获取内容
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String id=RequestUtil.getString(request, "id");
			sysMsgTemplateManager.setDefault(id);
			message=new ResultMessage(ResultMessage.SUCCESS, "设置默认成功");
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "设置默认失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出系统模板xml。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportXml")
	public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String[] ids = RequestUtil.getStringAryByStr(request, "ids");
		if (BeanUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids);
			String fileName = "SysTemplate_"+ DateFormatUtil.getNowByString("yyyyMMddHHmmss");
			String strXml = sysMsgTemplateManager.exportXml(idList);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	/**
	 * 导入系统模板XML。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importXml")
	public void importXml(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		boolean clearAll = RequestUtil.getBoolean(request, "clearAll",false);
		boolean setDefault = RequestUtil.getBoolean(request, "setDefault",false);
		ResultMessage message = null;
		try {
			sysMsgTemplateManager.importXml(fileLoad.getInputStream(),clearAll,setDefault);
			message = new ResultMessage(ResultMessage.SUCCESS,"导入成功!");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL,"导入出错了，请检查导入格式是否正确或者导入的数据是否有问题！" );
		}
		writeResultMessage(response.getWriter(), message);
		
		
	}
}
