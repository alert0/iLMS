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

import com.hanthink.dpm.manager.DpmItemManager;
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
 * Description:不良品项目维护界面Controller
 * Company: HanThink
 * Date: 2018年9月14日 上午11:05:10
 * </pre>
 * @author luoxq
 */

@Controller
@RequestMapping("/dpm/item")
public class DpmItemController extends GenericController{

	
	@Resource
	private DpmItemManager dpmItemManager;
	
	private static Logger log = LoggerFactory.getLogger(DpmItemController.class);
	
	/**
	 * 查询所有dpmItem数据
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
            @ModelAttribute("model") DpmItemModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<DpmItemModel> pageList = (PageList<DpmItemModel>) dpmItemManager.queryDpmItemForPage(model, p);
        return new PageJson(pageList);
    }

	/**
	 * 根据主键ID查询DpmItem信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:46:23
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmItemModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DpmItemModel();
		}
		return dpmItemManager.get(id);
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
	public void save(HttpServletRequest request,HttpServletResponse response, DpmItemModel dpmItemModel) throws Exception{
		String resultMsg = null;
		String id = dpmItemModel.getId();
		IUser user = ContextUtil.getCurrentUser();

		try {
			if(StringUtil.isEmpty(id)){
				String itemCode=dpmItemModel.getItemCode();
				DpmItemModel d= dpmItemManager.getByCode(itemCode);
				if (d!=null) {
					resultMsg="不良品项目代码已存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else {
					dpmItemModel.setCreateBy(user.getAccount()); //记录创建人
					dpmItemModel.setFactoryCode(user.getCurFactoryCode());
					dpmItemManager.create(dpmItemModel);
					resultMsg="添加成功";
				}
			}else{
				dpmItemModel.setUpdateBy(user.getAccount());//记录修改人
				dpmItemManager.updateAndLog(dpmItemModel, RequestUtil.getIpAddr(request));
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
	 * 删除DpmItemModel数据信息
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
			dpmItemManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
