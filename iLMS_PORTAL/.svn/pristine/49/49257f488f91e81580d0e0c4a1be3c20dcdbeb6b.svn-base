package com.hotent.mini.controller.system;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.ZipUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.QueryMetafieldManager;
import com.hotent.sys.persistence.manager.QuerySqldefManager;
import com.hotent.sys.persistence.manager.SysDataSourceManager;
import com.hotent.sys.persistence.model.QuerySqldef;

/**
 * 
 * <pre>
 * 描述：自定义SQL设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 16:46:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/query/querySqldef")
public class QuerySqldefController extends GenericController {
	@Resource
	QuerySqldefManager querySqldefManager;
	@Resource
	QueryMetafieldManager queryMetafieldManager;
	@Resource
	SysDataSourceManager sysDataSourceManager;
	
	private final static String ROOT_PATH = "attachFiles" + File.separator + "tempZip";

	/**
	 * 自定义SQL设置列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<QuerySqldef> querySqldefList = (PageList<QuerySqldef>) querySqldefManager.query(queryFilter);
		return new PageJson(querySqldefList);
	}

	/**
	 * 自定义SQL设置明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody QuerySqldef getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String alias = RequestUtil.getString(request, "alias");
		QuerySqldef querySqldef =null;
		if (StringUtil.isNotEmpty(id)) {
			querySqldef = querySqldefManager.get(id);
		}else if(StringUtil.isNotEmpty(alias)){
			querySqldef=querySqldefManager.getByAlias(alias);
		}
		
		if(querySqldef!=null){
			querySqldef.setMetafields(queryMetafieldManager.getBySqlId(querySqldef.getId()));
		}
		return querySqldef;
	}

	/**
	 * 保存自定义SQL设置信息
	 * 
	 * @param request
	 * @param response
	 * @param querySqldef
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,@RequestBody QuerySqldef querySqldef) throws Exception {
		try {
			String id = querySqldef.getId();
			String resultMsg = "添加自定义SQL查询成功，是否继续操作？";
			if(!StringUtil.isEmpty(id)){
				resultMsg = "更新自定义SQL查询成功，是否继续操作？";
			}
			querySqldefManager.save(querySqldef);
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "对自定义SQL设置操作失败", e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除自定义SQL设置记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			querySqldefManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除自定义SQL设置成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除自定义SQL设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("checkSql")
	public void checkSql(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sql = RequestUtil.getString(request, "sql", "", false);
		String dsName = RequestUtil.getString(request, "dsName", "");
		try {
			sysDataSourceManager.setDbContextDataSource(dsName);
			querySqldefManager.checkSql(dsName, sql);
		} catch (Exception e) {
			JSONObject data = JSONObject.fromObject(e.getMessage());
			if (data.getBoolean("result")) {
				writeResultMessage(response.getWriter(), data.getString("message"), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "SQL验证失败", data.getString("message"), ResultMessage.FAIL);
			}
		}
	}
	
	@RequestMapping("export")
	public void exportFrom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String[] ids = RequestUtil.getStringAryByStr(request, "ids");
			if (BeanUtils.isEmpty(ids)) 	return;
			List<String> idList = Arrays.asList(ids);
			String xml = querySqldefManager.export(idList); // 输出xml
			String fileName = "sqldef_"+DateFormatUtil.format(new Date(), "yyyy_MMdd_HHmm");
			HttpUtil.downLoadFile(request, response, xml, "sqldef.xml", fileName);
		} catch (Exception e) {
			e.printStackTrace();
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "导出失败!");
			writeResultMessage(response.getWriter(), message);
		} 
	}
	
	@RequestMapping("import")
	public void importBo(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage message = null;
		String unZipFilePath = null;
		try {
			//String rootRealPath = request.getSession().getServletContext().getRealPath(ROOT_PATH); // 操作的根目录
			String rootRealPath = this.getClass().getClassLoader().getResource(File.separator).getPath()+ROOT_PATH;// 操作的根目录
			String name = fileLoad.getOriginalFilename();
			String fileDir = StringUtil.substringBeforeLast(name, ".");
			
			ZipUtil.unZipFile(fileLoad, rootRealPath); // 解压文件
			unZipFilePath = rootRealPath + File.separator + fileDir; // 解压后文件的真正路径
			
			// 导入xml
			querySqldefManager.importDef(unZipFilePath);
			
			message = new ResultMessage(ResultMessage.SUCCESS, ThreadMsgUtil.getMessage());
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "导入失败! "+e.getMessage());
		} finally {
			if(StringUtil.isNotEmpty(unZipFilePath)){
				File formDir = new File(unZipFilePath);
				if (formDir.exists()) {
					FileUtil.deleteDir(formDir); // 删除解压后的目录
				}
			}
		}
		writeResultMessage(response.getWriter(), message);
	}
}
