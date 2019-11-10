package com.hanthink.sw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwAnnounceManager;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.UserModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
* <p>Title: SwAnnounceController.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/
@Controller
@RequestMapping("/sw/swAnnounce")
public class SwAnnounceController extends GenericController{
	
	@Resource 
	private SwAnnounceManager swAnnounceManager;

	private static Logger log = LoggerFactory.getLogger(SwAnnounceController.class);

	/**
	 * 
	* @Title: queryJisoGroupPage 
	* @Description: 分页查询公告发布管理 
	* @param @param request
	* @param @param reponse
	* @param @param model
	* @param @return
	* @param @throws Exception    
	* @return PageJson 
	* @throws 
	* @author luoxq
	* @date   2018年10月15日 下午4:06:29
	 */
	@RequestMapping("queryJisoGroupPage")
	public @ResponseBody PageJson queryJisoGroupPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAnnounceModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionKey.SW_SUPPLIER_GROUP_QUERYFILTER, model);
        
        PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.queryJisoAnnouncePage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: remove</p>  
	 * <p>Description:删除公告 </p>  
	 * @param request
	 * @param response
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:58:01
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
			ResultMessage message=null;
			try {
				String[] noticeIds = request.getParameterValues("id");
				swAnnounceManager.removeAndLogByIds(noticeIds, RequestUtil.getIpAddr(request));
				message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				message = new ResultMessage(ResultMessage.FAIL, "删除失败");
			}
			writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * <p>Title: saveGroup</p>  
	 * <p>Description: 保存新增和修改方法</p>  
	 * @param request
	 * @param response
	 * @param swSupplierGroupModel
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午9:44:20
	 */
	@RequestMapping("save")
	public void saveGroup(HttpServletRequest request,HttpServletResponse response,@RequestBody SwAnnounceModel model) 
			throws Exception{
		String resultMsg = null;
		String noticeId = model.getNoticeId();
		IUser user = ContextUtil.getCurrentUser();

		try {
			if(StringUtil.isEmpty(noticeId)){
				model.setFactoryCode(user.getCurFactoryCode());
				model.setCreationUser(user.getAccount()); //记录创建人
				swAnnounceManager.insertAnnounce(model);
				resultMsg="添加成功";
			}else{
				model.setLastModifiedUser(user.getAccount());//记录修改人
				swAnnounceManager.updateAndLog(model, RequestUtil.getIpAddr(request));
				resultMsg="更新成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * <p>Title: curdGetGroup</p>  
	 * <p>Description: 获取公告发布明细</p>  
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午3:51:12
	 */
	@RequestMapping("get")
	public @ResponseBody SwAnnounceModel curdGetGroup(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		String noticId = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(noticId)){
			return new SwAnnounceModel();
		}
		return swAnnounceManager.get(noticId);
	}
	
	/**
	 * 
	 * <p>Title: publish</p>  
	 * <p>Description: 发布公告</p>  
	 * @param request
	 * @param response
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:57:51
	 */
	@RequestMapping("publish")
	public void publish(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
			ResultMessage message=null;
			try {
				String[] noticeIds = request.getParameterValues("id");
				swAnnounceManager.publishNotice(noticeIds);
				message = new ResultMessage(ResultMessage.SUCCESS, "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				message = new ResultMessage(ResultMessage.FAIL, "发布失败");
			}
			writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * <p>Title: queryJisoFeedbackPage</p>  
	 * <p>Description: 公告发布管理界面， 供应商反馈查看功能 </p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午11:23:00
	 */
	@RequestMapping("queryJisoFeedbackPage")
	public @ResponseBody PageJson queryJisoFeedbackPage(HttpServletRequest request,
            HttpServletResponse reponse) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        SwAnnounceModel model = new SwAnnounceModel();
        String noticeId = RequestUtil.getString(request, "noticeId");
        model.setNoticeId(noticeId);
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionKey.SW_SUPPLIER_GROUP_QUERYFILTER, model);
        
        PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.queryJisoFeedbackPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: viewAnnounceJisoPage</p>  
	 * <p>Description: 公告查看界面分页查询</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午6:30:56
	 */
	@RequestMapping("viewAnnounce")
	public @ResponseBody PageJson viewAnnounceJisoPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAnnounceModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        //通过登录用户的账号获取用户对象
//        UserModel userModel = swAnnounceManager.getUserModel(user.getAccount());
//        if (null != userModel && Constant.PUB_USER_FLAG_SU.equals(userModel.getUserFlag())) {
//			model.setSupplierNo(userModel.getSupplier());
//		}
        
        PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.viewAnnounceJisoPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * <p>Title: feedBack</p>  
	 * <p>Description: 公告查看界面反馈功能</p>  
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午9:35:52
	 */
	@RequestMapping("feedBack")
	public void feedBack(HttpServletRequest request,HttpServletResponse response,@RequestBody SwAnnounceModel model) 
			throws Exception{
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();

		try {
			model.setLastModifiedUser(user.getAccount());//记录修改人
			
			//通过账号关联出供应商代码
			
			model.setNoticeStatus("1");
			swAnnounceManager.updateViewAndLog(model, RequestUtil.getIpAddr(request));
			
			resultMsg="反馈成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
}
