package com.hanthink.dpm.controller;

import java.net.URLDecoder;
import java.util.List;

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

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.manager.DpmDepartmentManager;
import com.hanthink.dpm.manager.DpmItemManager;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.dpm.model.DpmItemModel;
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
 * Description:责任组维护界面controller
 * Company: HanThink
 * Date: 2018年9月13日 下午12:00:03
 * </pre>
 * @author luoxq
 */
@Controller
@RequestMapping("/dpm/department")
public class dpmDepartmentController extends GenericController{
	
	@Resource
	private DpmDepartmentManager dpmDepartmentManager;
	
	private static Logger log = LoggerFactory.getLogger(dpmDepartmentController.class);
	
	/**
	 * 查询所有dpmDepartmentModel数据
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
            @ModelAttribute("model") DpmDepartmentModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<DpmDepartmentModel> pageList = (PageList<DpmDepartmentModel>) dpmDepartmentManager.queryDpmDepartmentForPage(model, p);
        return new PageJson(pageList);
    }

	/**
	 * 根据主键ID查询DpmDepartmentModel信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:46:23
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmDepartmentModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DpmDepartmentModel();
		}
		return dpmDepartmentManager.get(id);
	}
	
	/**
	 * 保存更新DpmDepartment数据信息
	 * @param request
	 * @param response
	 * @param DpmItemModel
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:51:44
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response, DpmDepartmentModel dpmDepartmentModel) throws Exception{
		String resultMsg = null;
		String id = dpmDepartmentModel.getId();
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			if(StringUtil.isEmpty(id)){
				String departmentCode=dpmDepartmentModel.getDepCode();
				DpmDepartmentModel d = dpmDepartmentManager.getByCode(departmentCode);
				String belongDepString = URLDecoder.decode(dpmDepartmentModel.getBelongDep(),"UTF-8");
				//判断责任组编码是否重复
				if (d!=null) {
					resultMsg = "责任组编码已存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else {
					dpmDepartmentModel.setCreationUser(user.getAccount());//记录创建人
					dpmDepartmentModel.setFactoryCode(user.getCurFactoryCode());
					dpmDepartmentManager.create(dpmDepartmentModel);
					resultMsg="保存成功";
				}
			}else{
				dpmDepartmentModel.setLastModifiedUser(user.getAccount());//记录修改人
				dpmDepartmentManager.updateAndLog(dpmDepartmentModel, RequestUtil.getIpAddr(request));
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="系统错误,请联系管理员";
			writeResultMessage(response.getWriter(),resultMsg,resultMsg,ResultMessage.FAIL);
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
			dpmDepartmentManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "系统错误,请联系管理员");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 获取部门审核人
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 上午11:42:15
	 */
	@RequestMapping("getDepChecker")
    public @ResponseBody List<DpmDepartmentModel> getDepChecker(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") DpmDepartmentModel model) throws Exception{
//        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        List<DpmDepartmentModel> list = (List<DpmDepartmentModel>) dpmDepartmentManager.getDepChecker(model);
        return list;
    }
	
	/**
	 * 
	 * @Description: 部门人员界面，获取责任组下拉框
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception   
	 * @return Object  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 上午11:42:54
	 */
	@RequestMapping("getDepUnloadCode")
	public @ResponseBody  List<DictVO> getDepUnloadCode(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		IUser user = ContextUtil.getCurrentUser();
		String factoryCode = user.getCurFactoryCode();
		try {
            List<DictVO> models = dpmDepartmentManager.getDepUnloadCode(factoryCode);
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	

}
