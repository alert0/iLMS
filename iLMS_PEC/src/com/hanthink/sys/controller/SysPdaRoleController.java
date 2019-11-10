package com.hanthink.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.hanthink.sys.manager.SysPdaRoleManager;
import com.hanthink.sys.model.SysPdaRoleModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * @Desc		: PDA用户菜单角色管理
 * @FileName	: SysPdaRoleController.java
 * @CreateOn	: 2019年1月22日 上午11:47:08
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 */
@Controller
@RequestMapping("/sysPda/sysPdaRole")
public class SysPdaRoleController extends GenericController{
	
    private static Logger log = LoggerFactory.getLogger(SysPdaRoleController.class);
    
    @Resource
    SysPdaRoleManager manager;
    
    /**
     * 分页查询PDA角色信息
     * @param request
     * @param reponse
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午1:36:43
     */
    @RequestMapping("queryPdaRolePager")
    public @ResponseBody PageJson queryPdaRolePager(HttpServletRequest request, HttpServletResponse response) {
    	try {
			Page p = getQueryFilter(request).getPage();
			SysPdaRoleModel model = new SysPdaRoleModel();
			model.setRoleName(RequestUtil.getDecodeString(request, "roleName"));
			List<SysPdaRoleModel> pageList = manager.queryPdaRolePager(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return new PageJson(new ArrayList<SysPdaRoleModel>());
    }
    
    /**
     * 查询PDA角色的菜单信息
     * @param request
     * @param response
     * @return
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午1:54:20
     */
    @RequestMapping("queryPdaRoleMenuPager")
    public @ResponseBody PageJson queryPdaRoleMenuPager(HttpServletRequest request, HttpServletResponse response) {
    	try {
			Page p = getQueryFilter(request).getPage();
			SysPdaRoleModel model = new SysPdaRoleModel();
			model.setMenuNameDesc(RequestUtil.getDecodeString(request, "menuDesc"));
			model.setPdaRoleId(RequestUtil.getDecodeString(request, "pdaRoleId"));
			List<SysPdaRoleModel> pageList = manager.queryPdaRoleMenuPager(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return new PageJson(new ArrayList<SysPdaRoleModel>());
    }
    
    /**
     * 保存PDA角色信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午2:04:38
     */
    @RequestMapping("savePdaRole")
    public void savePdaRole(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysPdaRoleModel model) {
    	String resultMsg = "操作失败";
		String id = model.getId();
		String curUserId = ContextUtil.getCurrentUserId();
		try {
			if (StringUtil.isEmpty(id)) {
				model.setCreateBy(curUserId);
				model.setCreateTime(new Date());
				model.setUpdateBy(curUserId);
				model.setUpdateTime(new Date());
				model.setId(UniqueIdUtil.getSuid());
				manager.create(model);
				resultMsg = "添加成功";
			} else {
				SysPdaRoleModel qModel = manager.get(model.getId());
				qModel.setRoleName(model.getRoleName());
				qModel.setRoleRemark(model.getRoleRemark());
				qModel.setUpdateBy(curUserId);
				qModel.setUpdateTime(new Date());
				manager.update(qModel);
				resultMsg = "更新成功";
			}
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
     * 删除PDA角色信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午2:25:47
     */
    @RequestMapping("deletePdaRole")
    public void deletePdaRole(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysPdaRoleModel model) {
    	String resultMsg = "操作失败";
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			manager.deletePdaRole(model);
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
    
    /**
     * 根据角色ID查询该角色还未添加的菜单数据信息
     * @param request
     * @param response
     * @return
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午2:36:19
     */
    @RequestMapping("queryNotAddPdaRoleDataByRoleId")
    public @ResponseBody PageJson queryNotAddPdaRoleByDataRoleId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysPdaRoleModel model = new SysPdaRoleModel();
			model.setPdaRoleId(RequestUtil.getDecodeString(request, "pdaRoleId"));
			model.setMenuNameDesc(RequestUtil.getDecodeString(request, "menuDesc"));
			List<SysPdaRoleModel> pageList = manager.queryNotAddPdaRoleDataByRoleId(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
    
    /**
     * 保存角色菜单信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午3:18:56
     */
    @RequestMapping("addPdaRoleMenu")
    public void addPdaRoleMenu(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysPdaRoleModel model) {
    	String resultMsg = "操作失败";
		String pdaRoleId = model.getPdaRoleId();
		String menuId = model.getMenuId();
		String curUserId = ContextUtil.getCurrentUserId();
		Date curDate = new Date();
		try {
			if (StringUtil.isEmpty(pdaRoleId) || StringUtil.isEmpty(menuId)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<SysPdaRoleModel> roleList = new ArrayList<SysPdaRoleModel>();
			String[] menuIdArr = menuId.split(",");
			for(String tmpMenuId : menuIdArr){
				SysPdaRoleModel t = new SysPdaRoleModel();
				t.setMenuId(tmpMenuId);
				t.setPdaRoleId(pdaRoleId);
				t.setCreateBy(curUserId);
				t.setCreateTime(curDate);
				roleList.add(t);
			}
			manager.addPdaRoleMenu(roleList);
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
     * 删除PDA的角色菜单信息
     * @param request
     * @param response
     * @param modelList
     * @author ZUOSL	
     * @DATE	2019年1月22日 下午3:37:46
     */
    @RequestMapping("deletePdaRoleMenu")
    public void deletePdaRoleMenu(HttpServletRequest request, HttpServletResponse response,
            @RequestBody List<SysPdaRoleModel> modelList) {
    	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			manager.deletePdaRoleMenu(modelList);
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
    
    
    
    
    /**
	  * 根据用户ID查询该用户的PDA角色信息
	  * @param request
	  * @param reponse
	  * @param model
	  * @return
	  * @author ZUOSL	
	  * @DATE	2018年12月26日 下午9:43:50
	  */
   @RequestMapping("queryUserPdaRoleByUserId")
   public @ResponseBody PageJson queryUserPdaRoleByUserId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysPdaRoleModel model = new SysPdaRoleModel();
			model.setUserId(RequestUtil.getString(request, "userId"));
			model.setRoleName(RequestUtil.getDecodeString(request, "roleName"));
			PageList<Map<String, Object>> pageList = manager.queryUserPdaRoleByUserId(model, p);
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
    * 根据用户ID查询该用户待添加的PDA角色信息
    * @param request
    * @param response
    * @param model
    * @return
    * @author ZUOSL	
    * @DATE	2018年12月26日 下午10:32:06
    */
   @RequestMapping("queryAddUserPdaRoleByUserId")
   public @ResponseBody PageJson queryAddUserPdaRoleByUserId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysPdaRoleModel model = new SysPdaRoleModel();
			model.setUserId(RequestUtil.getDecodeString(request, "userId"));
			model.setRoleName(RequestUtil.getDecodeString(request, "roleName"));
			IUser curUser = ContextUtil.getCurrentUser();
			PageList<Map<String, Object>> pageList = null;
			if(curUser.isAdmin()){
				pageList = manager.queryAddUserPdaRoleByUserId(model, p);
			}else{
				model.setCurLoginUserId(curUser.getUserId());
				pageList = manager.queryCurUserAddUserPdaRoleByUserId(model, p);
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
    * 添加用户的数据角色信息，多个PDA角色ID用英文逗号分隔
    * @param request
    * @param response
    * @param model
    * @return
    * @author ZUOSL	
    * @DATE	2018年12月26日 下午10:35:45
    */
   @RequestMapping("addUserPdaRole")
   public void addUserPdaRole(HttpServletRequest request, HttpServletResponse response,
   		@RequestBody SysPdaRoleModel model) {
	   
   		String resultMsg = "操作失败";
		String pdaRoleId = model.getPdaRoleId();
		String userId = model.getUserId();
		String curUserId = ContextUtil.getCurrentUserId();
		Date curDate = new Date();
		try {
			if (StringUtil.isEmpty(pdaRoleId) || StringUtil.isEmpty(userId)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<SysPdaRoleModel> userDataRoleList = new ArrayList<SysPdaRoleModel>();
			String[] pdaRoleIdArr = pdaRoleId.split(",");
			for(String roleId : pdaRoleIdArr){
				SysPdaRoleModel t = new SysPdaRoleModel();
				t.setPdaRoleId(roleId);
				t.setUserId(userId);
				t.setCreateBy(curUserId);
				t.setCreateTime(curDate);
				userDataRoleList.add(t);
			}
			manager.addUserPdaRole(userDataRoleList);
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
    * 删除用户的PDA角色信息
    * @param request
    * @param response
    * @param modelList
    * @author ZUOSL	
    * @DATE	2018年12月26日 下午10:42:14
    */
   @RequestMapping("deleteUserPdaRole")
   public void deleteUserPdaRole(HttpServletRequest request, HttpServletResponse response,
           @RequestBody List<SysPdaRoleModel> modelList) {
   	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			manager.deleteUserPdaRole(modelList);
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
