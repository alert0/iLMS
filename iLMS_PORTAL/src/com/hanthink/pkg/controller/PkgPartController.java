package com.hanthink.pkg.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

import com.hanthink.pkg.manager.PkgPartManager;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
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
* <p>Title: PkgPartController.java<／p>
* <p>Description: 零件担当controller<／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月26日
*/
@Controller
@RequestMapping("/pkg/pkgPart")
public class PkgPartController extends GenericController{

	@Resource
	private PkgPartManager pkgPartManager;
	
	private static Logger log = LoggerFactory.getLogger(PkgPartController.class);

	/**
	 * 
	* @Title: curdlistJson 
	* @Description: 分页查询出零件担当维护信息 
	* @param @param request
	* @param @param reponse
	* @param @param model
	* @param @return
	* @param @throws Exception    
	* @return PageJson 
	* @throws
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") PkgPartModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgPartModel> pageList = (PageList<PkgPartModel>) pkgPartManager.queryPkgPartForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	* @Title: save 
	* @Description: 修改零件担当 
	* @param @param request
	* @param @param response
	* @param @param pkgPartModel
	* @param @throws Exception    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月7日 下午3:45:25
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response, PkgPartModel pkgPartModel) throws Exception{
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			pkgPartModel.setLastModifiedUser(user.getAccount());//记录修改人
			pkgPartManager.updateAndLog(pkgPartModel, RequestUtil.getIpAddr(request));
			resultMsg="更新成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="操作失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	* @Title: curdgetJson 
	* @Description: 获取零件担当维护明细  
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws Exception    
	* @return PkgBoxModel 
	* @throws 
	* @author luoxq
	* @date   2018年9月26日 下午4:05:20
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody PkgPartModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new PkgPartModel();
		}
		return pkgPartManager.get(id);
	}
	
	/**
	 * 
	* @Title: getTelNoByContact 
	* @Description: 通过零件担当带出联系电话 
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws Exception    
	* @return PkgPartModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月7日 下午3:55:14
	 */
	@RequestMapping("getTelNoByUser")
	public @ResponseBody PkgProposalModel getTelNoByUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String partRespUser = RequestUtil.getString(request, "partRespUser");
		if(StringUtil.isEmpty(partRespUser)){
			return new PkgProposalModel();
		}
		return pkgPartManager.getTelNoByUser(partRespUser);
	}
}
