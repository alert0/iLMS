package com.hanthink.sys.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysDpUserDpConfigManager;
import com.hanthink.sys.model.SysDpUserDpConfigModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：用户数据角色配置 控制器类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sysdp/userDpConfig")
public class SysDpUserDpConfigController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SysDpUserDpConfigController.class);
	
	@Resource
	SysDpUserDpConfigManager sysDpUserDpConfigManager;
	
	
	/**
	 * 查询系统用户数据信息
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午10:33:35
	 */
	@RequestMapping("querySysUserData")
    public @ResponseBody PageJson querySysUserData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("accountOrName", RequestUtil.getDecodeString(request, "accountOrName"));
			PageList<Map<String, Object>> pageList = sysDpUserDpConfigManager.querySysUserData(paramMap, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
	
	 /**
	  * 根据用户ID查询该用户的数据角色信息
	  * @param request
	  * @param reponse
	  * @param model
	  * @return
	  * @author ZUOSL	
	  * @DATE	2018年12月26日 下午9:43:50
	  */
    @RequestMapping("queryUserDataRoleByUserId")
    public @ResponseBody PageJson queryUserDataRoleByUserId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpUserDpConfigModel model = new SysDpUserDpConfigModel();
			model.setUserId(RequestUtil.getString(request, "userId"));
			PageList<Map<String, Object>> pageList = sysDpUserDpConfigManager.queryUserDataRoleByUserId(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
    
    /**
     * 根据用户ID查询该用户待添加的数据角色信息
     * @param request
     * @param response
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午10:32:06
     */
    @RequestMapping("queryAddUserDataRoleByUserId")
    public @ResponseBody PageJson queryAddUserDataRoleByUserId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpUserDpConfigModel model = new SysDpUserDpConfigModel();
			model.setUserId(RequestUtil.getDecodeString(request, "userId"));
			model.setDataRoleName(RequestUtil.getDecodeString(request, "dataRoleName"));
			model.setDataRoleTypeName(RequestUtil.getDecodeString(request, "dataRoleTypeName"));
			IUser curUser = ContextUtil.getCurrentUser();
			PageList<Map<String, Object>> pageList = null;
			if(curUser.isAdmin()){
				pageList = sysDpUserDpConfigManager.queryAddUserDataRoleByUserId(model, p);
			}else{
				model.setCurLoginUserId(curUser.getUserId());
				pageList = sysDpUserDpConfigManager.queryCurUserAddUserDataRoleByUserId(model, p);
			}
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
	
    /**
     * 添加用户的数据角色信息，多个数据角色ID用英文逗号分隔
     * @param request
     * @param response
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午10:35:45
     */
    @RequestMapping("addUserDpRole")
    public void addUserDpRole(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody SysDpUserDpConfigModel model) {
    	String resultMsg = "操作失败";
		String dataRoleId = model.getDataRoleId();
		String userId = model.getUserId();
		String curUserId = ContextUtil.getCurrentUserId();
		Date curDate = new Date();
		try {
			if (StringUtil.isEmpty(dataRoleId) || StringUtil.isEmpty(userId)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<SysDpUserDpConfigModel> userDataRoleList = new ArrayList<SysDpUserDpConfigModel>();
			String[] dataRoleIdArr = dataRoleId.split(",");
			for(String roleId : dataRoleIdArr){
				SysDpUserDpConfigModel t = new SysDpUserDpConfigModel();
				t.setDataRoleId(roleId);
				t.setUserId(userId);
				t.setCreateBy(curUserId);
				t.setCreateTime(curDate);
				userDataRoleList.add(t);
			}
			sysDpUserDpConfigManager.addUserDpRole(userDataRoleList);
			resultMsg = "添加成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    /**
     * 删除用户的数据角色信息
     * @param request
     * @param response
     * @param modelList
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午10:42:14
     */
    @RequestMapping("deleteUserDataRole")
    public void deleteUserDataRole(HttpServletRequest request, HttpServletResponse response,
            @RequestBody List<SysDpUserDpConfigModel> modelList) {
    	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			sysDpUserDpConfigManager.deleteUserDataRole(modelList);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
}
