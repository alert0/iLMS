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
import com.hanthink.dpm.manager.DpmAreaManager;
import com.hanthink.dpm.model.DpmAreaModel;
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
 * Description:发现区域维护界面controller
 * Company: HanThink
 * Date: 2018年9月11日 下午4:00:01
 * </pre>
 * @author luoxq
 */
@Controller
@RequestMapping("/dpm/area")
public class DpmAreaController extends GenericController{
	
	
	@Resource
	private DpmAreaManager dpmAreaManager;
	
	
	private static Logger log = LoggerFactory.getLogger(DpmAreaController.class);
	
	/**
	 * 查询所有dpmAreaModel数据
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
            @ModelAttribute("model") DpmAreaModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
//        System.out.println(model.getItemName()+"---------");
        
        PageList<DpmAreaModel> pageList = (PageList<DpmAreaModel>) dpmAreaManager.queryDpmAreaForPage(model, p);
        return new PageJson(pageList);
    }

	/**
	 * 根据主键ID查询dpmAreaModel信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:46:23
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmAreaModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DpmAreaModel();
		}
		return dpmAreaManager.get(id);
	}
	
	/**
	 * 保存更新DpmAreaModel数据信息
	 * @param request
	 * @param response
	 * @param DpmAreaModel
	 * @throws Exception
	 * @author luoxq	
	 * @DATE	2018年9月10日 下午3:51:44
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response, DpmAreaModel dpmAreaModel) throws Exception{
		String resultMsg = null;
		String id = dpmAreaModel.getId();
		IUser user = ContextUtil.getCurrentUser();
		
		
		try {
			if(StringUtil.isEmpty(id)){
				String areaCode=dpmAreaModel.getAreaCode();
				DpmAreaModel d= dpmAreaManager.getByCode(areaCode);
				if (d!=null) {
					resultMsg="发现区域代码已存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else {
					dpmAreaModel.setFactoryCode(user.getCurFactoryCode());
					dpmAreaModel.setCreateBy(user.getAccount()); //记录创建人
					dpmAreaManager.create(dpmAreaModel);
					resultMsg="添加成功";
				}
			}else{
				dpmAreaManager.updateAndLog(dpmAreaModel, RequestUtil.getIpAddr(request));
				dpmAreaModel.setUpdateBy(user.getAccount());//记录修改人
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
			dpmAreaManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取发现区域代码填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("getUnloadCode")
	public @ResponseBody Object getUnloadCode(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmAreaManager.getUnloadCode();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 获取新增界面仓库代码下拉框值 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月12日 下午5:17:06
	 * @throws
	 */
	@RequestMapping("getUnloadWareCode")
	public @ResponseBody Object getUnloadWareCode(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmAreaManager.getUnloadWareCode();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 获取新增界面车间下拉框值 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月12日 下午6:02:44
	 * @throws
	 */
	@RequestMapping("getUnloadWorkcenter")
	public @ResponseBody Object getUnloadWorkcenter(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		IUser user = ContextUtil.getCurrentUser();
		String factoryCode=user.getCurFactoryCode();
		
		try {
			if (StringUtil.isEmpty(factoryCode)) {
				return null;
			} else {
				List<DictVO> models = dpmAreaManager.getUnloadWorkcenter(factoryCode);
	            return new PageJson(models);
			}
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 新增或修改时通过仓库代码带出仓库名称 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception    
	 * @return DpmAreaModel     
	 * @time   2018年9月12日 下午5:17:46
	 * @throws
	 */
	@RequestMapping("getWareNameByCode")
	public @ResponseBody DpmAreaModel getWareNameByCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String wareCode = RequestUtil.getString(request, "wareCode");
		if(StringUtil.isEmpty(wareCode)){
			return new DpmAreaModel();
		}
		System.out.println(dpmAreaManager.getWareNameByCode(wareCode).getWareName()+"====");
		return dpmAreaManager.getWareNameByCode(wareCode);
	}

}
