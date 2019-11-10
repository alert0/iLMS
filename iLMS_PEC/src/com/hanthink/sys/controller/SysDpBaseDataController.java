package com.hanthink.sys.controller;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysDpBaseDataManager;
import com.hanthink.sys.model.SysDpBaseDataModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：数据权限基础数据表 控制器类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sysdp/sysDpBaseData")
public class SysDpBaseDataController extends GenericController{
	@Resource
	SysDpBaseDataManager sysDpBaseDataManager;
	
	/**
	 * 查询数据权限基础数据的分类信息
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:56:15
	 */
    @RequestMapping("queryDpBaseDataType")
    public @ResponseBody PageJson queryDpBaseDataType(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpBaseDataModel model = new SysDpBaseDataModel();
			model.setTypeKey(RequestUtil.getDecodeString(request, "typeKey"));
			List<SysDpBaseDataModel> pageList = (PageList<SysDpBaseDataModel>) sysDpBaseDataManager.queryDpBaseDataType(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
     * 根据数据权限类型查询数据权限基础数据
     * @param request
     * @param reponse
     * @param model
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月25日 下午3:56:08
     */
    @RequestMapping("queryDpBaseDataByType")
    public @ResponseBody PageJson queryDpBaseDataByType(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Page p = getQueryFilter(request).getPage();
			SysDpBaseDataModel model = new SysDpBaseDataModel();
			model.setTypeCode(RequestUtil.getDecodeString(request, "typeCode"));
			model.setValueCode(RequestUtil.getDecodeString(request, "valueCode"));
			model.setValueDesc(RequestUtil.getDecodeString(request, "valueDesc"));
			List<SysDpBaseDataModel> pageList = (PageList<SysDpBaseDataModel>) sysDpBaseDataManager.queryDpBaseDataByType(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
    /**
     * 保存数据权限基础数据
     * @param request
     * @param response
     * @param sysType
     * @throws Exception
     * @author ZUOSL	
     * @DATE	2018年12月25日 下午4:26:02
     */
	@RequestMapping("saveSysDpBaseData")
	public void saveSysDpBaseData(HttpServletRequest request, HttpServletResponse response, @RequestBody SysDpBaseDataModel model) throws Exception {
		String resultMsg = null;
		String id = model.getId();
		String typeCode = model.getTypeCode();
		String valueCode = model.getValueCode();
		String curUserId = ContextUtil.getCurrentUser().getUserId();

		if (StringUtil.isEmpty(id)) {
			id = null;
		}
		boolean isKeyExist = sysDpBaseDataManager.isKeyExist(id, typeCode, valueCode);
		if (isKeyExist) {
			resultMsg = "输入的权限代码【" + valueCode + "】已存在!";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
			return;
		} else {
			try {
				if (StringUtil.isEmpty(id)) {
					model.setCreateBy(curUserId);
					model.setCreateTime(new Date());
					model.setUpdateBy(curUserId);
					model.setUpdateTime(new Date());
					model.setId(UniqueIdUtil.getSuid());
					sysDpBaseDataManager.create(model);
					resultMsg = "添加成功";
				} else {
					SysDpBaseDataModel qModel = sysDpBaseDataManager.get(id);
					qModel.setValueCode(model.getValueCode());
					qModel.setValueDesc(model.getValueDesc());
					qModel.setSortNum(model.getSortNum());
					qModel.setUpdateBy(curUserId);
					qModel.setUpdateTime(new Date());
					sysDpBaseDataManager.update(qModel);
					resultMsg = "更新成功";
				}
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			}
		}
	}
	
	/**
	 * 删除数据权限基础数据
	 * @param request
	 * @param response
	 * @param sysType
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午4:26:27
	 */
	@RequestMapping("deleteSysDpBaseData")
	public void deleteSysDpBaseData(HttpServletRequest request, HttpServletResponse response,@RequestBody SysDpBaseDataModel model) throws Exception {
		String resultMsg = null;
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				writeResultMessage(response.getWriter(), "缺失ID，操作失败", ResultMessage.FAIL);
				return;
			} else {
				String[] idArr = id.split(",");
				for(String tid : idArr){
					sysDpBaseDataManager.deleteSysDpBaseData(tid);
				}
				resultMsg = "删除分类成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
	}
	
}
