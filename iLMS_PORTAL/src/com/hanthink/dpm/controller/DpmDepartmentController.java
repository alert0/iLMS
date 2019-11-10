package com.hanthink.dpm.controller;

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
public class DpmDepartmentController extends GenericController{
	
	@Resource
	private DpmDepartmentManager dpmDepartmentManager;
	
	private static Logger log = LoggerFactory.getLogger(DpmDepartmentController.class);
	
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
	 * 保存更新DpmItem数据信息
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
		
	
		 //判断输入的人员姓名是否存在
		dpmDepartmentModel.setFullname(dpmDepartmentModel.getDepChecker());
		List<DpmDepartmentModel> ds = dpmDepartmentManager.isExistChecker(dpmDepartmentModel);
		if (ds.size()<1) {
			resultMsg = "人员不存在";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			return;
		}
		
		try {
			if(StringUtil.isEmpty(id)){
				String departmentCode=dpmDepartmentModel.getDepCode();
				DpmDepartmentModel d = dpmDepartmentManager.getByCode(departmentCode);
				
				//判断责任组编码是否重复
				if (d!=null) {
					resultMsg = "责任组编码已存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else {
					dpmDepartmentModel.setCreateBy(user.getAccount());//记录创建人
					dpmDepartmentModel.setFactoryCode(user.getCurFactoryCode());
					dpmDepartmentManager.create(dpmDepartmentModel);
					resultMsg="添加成功";
				}
			}else{
				dpmDepartmentModel.setUpdateBy(user.getAccount());//记录修改人
				dpmDepartmentManager.updateAndLog(dpmDepartmentModel, RequestUtil.getIpAddr(request));
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
			dpmDepartmentManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("getDepChecker")
    public @ResponseBody PageJson getDepChecker(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") DpmDepartmentModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        PageList<DpmDepartmentModel> pageList = (PageList<DpmDepartmentModel>) dpmDepartmentManager.getDepChecker(model, p);
        return new PageJson(pageList);
    }
	
	@RequestMapping("getDepUnloadCode")
	public @ResponseBody Object getDepUnloadCode(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmDepartmentManager.getDepUnloadCode();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	

}
