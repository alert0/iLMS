package com.hanthink.pub.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubPrPrinterManager;
import com.hanthink.pub.model.PubPrPrinterModel;
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
 * 描述：打印任务业务表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubPrPrinter")
public class PubPrPrinterController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PubPrPrinterController.class);
	
	@Resource
	PubPrPrinterManager pubPrPrinterManager;
	  		
	/**
     * 分页查询打印任务业务表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse, PubPrPrinterModel model) {
    	try {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactory(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<PubPrPrinterModel> pageList = (PageList<PubPrPrinterModel>) pubPrPrinterManager.queryPubPrPrinterForPage(model, p);
        return new PageJson(pageList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 保存打印任务业务表信息
	 * @param request
	 * @param response
	 * @param PubPrPrinter
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,
			@RequestBody PubPrPrinterModel PubPrPrinterModel) throws Exception{
		String resultMsg = null;
		String id = PubPrPrinterModel.getId();
		String factory = PubPrPrinterModel.getFactory();
		try {
			if(StringUtil.isEmpty(id)){
				if(factory.isEmpty()) {
				PubPrPrinterModel.setFactory(ContextUtil.getCurrentUser().getCurFactoryCode());
				}
				PubPrPrinterModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
				pubPrPrinterManager.create(PubPrPrinterModel);
				resultMsg="新增成功";
			}else{
				if(factory.isEmpty()) {
				PubPrPrinterModel.setFactory(ContextUtil.getCurrentUser().getCurFactoryCode());
				}
				PubPrPrinterModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				PubPrPrinterModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
				pubPrPrinterManager.updateAndLog(PubPrPrinterModel, RequestUtil.getIpAddr(request));
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			if(StringUtil.isEmpty(id)) {
				resultMsg = "新增失败";
			}else {
				resultMsg = "修改失败";
			}
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除打印任务业务表记录
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
			pubPrPrinterManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除打印任务业务表成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除打印任务业务表失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
}
