package com.hanthink.dpm.controller;

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

import com.hanthink.dpm.manager.DpmDepPersonManager;
import com.hanthink.dpm.model.DpmDepPersonModel;
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
 * 
 * <pre>
 * Description:部门人员维护界面Controller
 * Company: HanThink
 * Date: 2018年9月14日 上午11:05:10
 * </pre>
 * @author luoxq
 */
@Controller
@RequestMapping("/dpm/depPerson")
public class DpmDepPersonController extends GenericController{
	
	@Resource
	private DpmDepPersonManager dpmDepPersonManager;

    private static Logger log = LoggerFactory.getLogger(DpmDepPersonController.class);
	
	/**
	 * 查询所有dpmDepPersonModel数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:46:23
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") DpmDepPersonModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        PageList<DpmDepPersonModel> pageList = (PageList<DpmDepPersonModel>) dpmDepPersonManager.queryDpmDepPersonForPage(model, p);
        return new PageJson(pageList);
    }

	/**
	 * 根据主键ID查询dpmDepPersonModel信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:46:23
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmDepPersonModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DpmDepPersonModel();
		}
		return dpmDepPersonManager.get(id);
	}
	
	/**
	 * 保存更新DpmItem数据信息
	 * @param request
	 * @param response
	 * @param DpmItemModel
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:51:44
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody DpmDepPersonModel dpmDepPersonModel) throws Exception{
		String resultMsg = null;
		String id = dpmDepPersonModel.getId();
		IUser user = ContextUtil.getCurrentUser();
		dpmDepPersonModel.setCreateBy(user.getUserId());
	
		try {
			if(StringUtil.isEmpty(id)){
				String userId=dpmDepPersonModel.getUserId();
				

					
					dpmDepPersonManager.create(dpmDepPersonModel);
					resultMsg="添加成功";
//				}
			}else{
				dpmDepPersonManager.updateAndLog(dpmDepPersonModel, RequestUtil.getIpAddr(request));
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
	 * 删除DpmDepartmentModel数据信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:54:01
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			dpmDepPersonManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
//	@RequestMapping("getDepChecker")
//    public @ResponseBody PageJson getDepChecker(HttpServletRequest request,
//            HttpServletResponse reponse,
//            @ModelAttribute("model") DpmDepartmentModel model) throws Exception{
//        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
//        
//        PageList<DpmDepartmentModel> pageList = (PageList<DpmDepartmentModel>) dpmDepPersonManager.getDepChecker(model, p);
//        return new PageJson(pageList);
//    }

}
