package com.hanthink.sys.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysDpRoleManageManager;
import com.hanthink.sys.model.SysDpRoleManageModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：数据权限角色管理 控制器类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sysdp/sysDpRoleManage")
public class SysDpRoleManageController extends GenericController{
	@Resource
	SysDpRoleManageManager sysDpRoleManageManager;
	
	/**
	 * 分页查询数据角色数据信息
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午12:59:49
	 */
    @RequestMapping("queryDpRolePager")
    public @ResponseBody PageJson queryDpRolePager(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpRoleManageModel model = new SysDpRoleManageModel();
			model.setDataRoleName(RequestUtil.getDecodeString(request, "dataRoleName"));
			model.setDataRoleTypeName(RequestUtil.getDecodeString(request, "dataRoleTypeName"));
			List<SysDpRoleManageModel> pageList = sysDpRoleManageManager.queryDpRolePager(model, p);
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
     * 分页查询数据角色对应的数据权限基础数据
     * @param request
     * @param reponse
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午2:33:14
     */
    @RequestMapping("queryDpRoleDataPager")
    public @ResponseBody PageJson queryDpRoleDataPager(HttpServletRequest request, HttpServletResponse response) { //@RequestBody SysDpRoleManageModel model
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpRoleManageModel model = new SysDpRoleManageModel();
			model.setDataRoleId(RequestUtil.getString(request, "dataRoleId"));
			model.setTypeName(RequestUtil.getDecodeString(request, "typeName"));
			model.setValueDesc(RequestUtil.getDecodeString(request, "valueDesc"));
			List<SysDpRoleManageModel> pageList = sysDpRoleManageManager.queryDpRoleDataPager(model, p);
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
     * 保存数据权限角色信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午3:33:12
     */
    @RequestMapping("saveDpRole")
    public void saveDpRole(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysDpRoleManageModel model) {
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
				sysDpRoleManageManager.create(model);
				resultMsg = "添加成功";
			} else {
				SysDpRoleManageModel qModel = sysDpRoleManageManager.get(id);
				qModel.setDataRoleName(model.getDataRoleName());
				qModel.setDataRoleTypeCode(model.getDataRoleTypeCode());
				qModel.setRemark(model.getRemark());
				qModel.setSortNum(model.getSortNum());
				qModel.setUpdateBy(curUserId);
				qModel.setUpdateTime(new Date());
				sysDpRoleManageManager.update(qModel);
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
     * 删除数据权限的角色数据
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午3:33:58
     */
    @RequestMapping("deleteDpRole")
    public void deleteDpRole(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysDpRoleManageModel model) {
    	String resultMsg = "操作失败";
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			sysDpRoleManageManager.deleteDpRole(model);
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
     * 根据角色ID查询该角色还未添加的数据权限基础数据信息
     * @param request
     * @param response
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午3:52:50
     */
    @RequestMapping("queryNotAddDpRoleDataByDataRoleId")
    public @ResponseBody PageJson queryNotAddDpRoleDataByDataRoleId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpRoleManageModel model = new SysDpRoleManageModel();
			model.setDataRoleId(RequestUtil.getDecodeString(request, "dataRoleId"));
			model.setTypeName(RequestUtil.getDecodeString(request, "typeName"));
			model.setValueDesc(RequestUtil.getDecodeString(request, "valueDesc"));
			List<SysDpRoleManageModel> pageList = sysDpRoleManageManager.queryNotAddDpRoleDataByDataRoleId(model, p);
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
     * 添加数据角色的权限数据信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午3:47:20
     */
    @RequestMapping("addDpRoleData")
    public void addDpRoleData(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SysDpRoleManageModel model) {
    	String resultMsg = "操作失败";
		String dataRoleId = model.getDataRoleId();
		String dpBaseId = model.getDpBaseId();
		String curUserId = ContextUtil.getCurrentUserId();
		Date curDate = new Date();
		try {
			if (StringUtil.isEmpty(dataRoleId) || StringUtil.isEmpty(dpBaseId)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<SysDpRoleManageModel> dpRoleList = new ArrayList<SysDpRoleManageModel>();
			String[] baseIdArr = dpBaseId.split(",");
			for(String baseId : baseIdArr){
				SysDpRoleManageModel t = new SysDpRoleManageModel();
				t.setDataRoleId(dataRoleId);
				t.setDpBaseId(baseId);
				t.setCreateBy(curUserId);
				t.setCreateTime(curDate);
				dpRoleList.add(t);
			}
			sysDpRoleManageManager.addDpRoleData(dpRoleList);
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
     * 删除数据角色的权限信息
     * @param request
     * @param response
     * @param model
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午4:34:58
     */
    @RequestMapping("deleteDpRoleData")
    public void deleteDpRoleData(HttpServletRequest request, HttpServletResponse response,
            @RequestBody List<SysDpRoleManageModel> modelList) {
    	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			sysDpRoleManageManager.deleteDpRoleData(modelList);
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
