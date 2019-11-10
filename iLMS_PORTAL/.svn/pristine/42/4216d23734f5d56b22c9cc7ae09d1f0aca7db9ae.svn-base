package com.hanthink.pub.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubDataDictManager;
import com.hanthink.pub.model.PubDataDictModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：数据字典表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubDataDict")
public class PubDataDictController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PubDataDictController.class);
	@Resource
	PubDataDictManager pubDataDictManager;
	
	/**
     * 分页查询数据字典
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            PubDataDictModel model) {
    	String resultMsg=null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<PubDataDictModel> pageList = (PageList<PubDataDictModel>) pubDataDictManager
					.queryPubDataDictForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 右边栏位显示查询结果
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("getRowClick")
	public @ResponseBody PageJson getRowClick(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String codeType = RequestUtil.getString(request, "codeType");
		String codeTypeName = RequestUtil.getString(request, "codeTypeName");
		Map<String,String> map = new HashMap();
		map.put("codeType", codeType);
		map.put("codeTypeName",codeTypeName);
		map.put("factoryCode",ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		PageList<PubDataDictModel> pageList = (PageList<PubDataDictModel>)pubDataDictManager.getRowClick(map,p);
		
		return new PageJson(pageList);
	}
	
	/**
	 * 左侧新增
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("insertLeft")
	public void insertLeft(HttpServletRequest request,HttpServletResponse response,
			@RequestBody PubDataDictModel pubDataDictModel) throws Exception{
		String resultMsg = null;
		try {
			pubDataDictModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			pubDataDictManager.insertLeft(pubDataDictModel);
			resultMsg="新增成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "新增失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 右侧新增
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("insertRight")
	public void insertRight(HttpServletRequest request,HttpServletResponse response,
			@RequestBody PubDataDictModel pubDataDictModel) throws Exception{
		String resultMsg = null;
		try {
				pubDataDictModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				pubDataDictManager.insertRight(pubDataDictModel);
				resultMsg="新增成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "新增失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除数据字典记录
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
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			pubDataDictManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除数据字典成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除数据字典失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("update")
	public void update(HttpServletRequest request,HttpServletResponse response,
			@RequestBody PubDataDictModel pubDataDictModel) throws Exception{
		String resultMsg = null;
		String id = pubDataDictModel.getId().toString();
		try {
			if(StringUtil.isEmpty(id)){
				resultMsg="数据异常";
				return;
			}else{
				pubDataDictManager.updateAndLog(pubDataDictModel, RequestUtil.getIpAddr(request));
				resultMsg="更新成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "修改失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
}
