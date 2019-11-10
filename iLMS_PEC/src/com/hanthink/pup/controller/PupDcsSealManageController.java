package com.hanthink.pup.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupDcsSealManager;
import com.hanthink.pup.model.PupDcsSealModel;
import com.hanthink.pup.model.PupDcsSealModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @Desc : DCS封条号管理
 * @CreateOn: 2019年1月4日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Controller
@RequestMapping("/pup/pupDcsSeal")
public class PupDcsSealManageController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(PupDcsSealManageController.class);

    @Resource
    PupDcsSealManager pupDcsSealManager;

    /**
     * 分页查询封条号数据
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, PupDcsSealModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsSealModel> pageList = (PageList<PupDcsSealModel>) pupDcsSealManager.queryPupDcsSealForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出封条号数据
     * 
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("downloadPupDcsSealModel")
    public void downloadPupDcsSealModel(HttpServletRequest request, HttpServletResponse response, PupDcsSealModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsSealModel> list = pupDcsSealManager.queryPupDcsSealByKey(model);
            /**
             * 如果查询记录超过10000条则报错
             */
            if (0 == list.size()) {
                ExcelUtil.exportNoDataError(request, response);
                return;
            }
            int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); // 获取系统所允许的最大导出数量
            if (list.size() > sysMaxNum) {
                ExcelUtil.exportNumError(sysMaxNum, request, response);
                return;
            }

            String[] headers = { "封条号", "使用状态" };
            String[] columns = { "sealNo", "dealFlagStr" };
            int[] widths = { 120, 100 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "封条号" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

    /**
     * 删除所有未使用的封条号数据
     * 
     * @param request
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping("batchDel")
    public void batchDel(HttpServletRequest request, HttpServletResponse response, PupDcsSealModel model) throws IOException {
        ResultMessage message = null;
        try {
            pupDcsSealManager.batchDelDcsSeal(ContextUtil.getCurrentUser().getCurFactoryCode());
            message = new ResultMessage(ResultMessage.SUCCESS, "批量删除DCS封条号未使用的数据成功");
        } catch (Exception e) {
            message = new ResultMessage(ResultMessage.FAIL, "批量删除DCS封条号未使用的数据失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 导入DCS封条号临时数据
     * @param request
     * @param response
     * @param file
     * @throws Exception
     */
    @RequestMapping("/importDcsSealModel")
    public void importDcsSealModel(HttpServletRequest request, HttpServletResponse response, @RequestParam("file")
    MultipartFile file) throws Exception {
        String uuid = null;
        Map<String, Object> rtn = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            uuid = (String) session.getAttribute(SessionKey.PUP_DCS_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            if (StringUtil.isNotEmpty(uuid)) {
                pupDcsSealManager.deleteDcsSealByUUID(uuid);
            } else {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.PUP_DCS_IMPORT_UUID, uuid);
            IUser user = ContextUtil.getCurrentUser();
            rtn = pupDcsSealManager.importDcsSealToTempTable(file, uuid, RequestUtil.getIpAddr(request), user);
            rtn.put("uuid", uuid);
            if ((boolean) rtn.get("result")) {
                writeResultMessage(response.getWriter(), "文件导入成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
            } else {
                writeResultMessage(
                        response.getWriter(),
                        (String) rtn.get("console"),
                        (String) rtn.get("console"),
                        JSONObject.fromObject(rtn),
                        ResultMessage.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            rtn.put("result", false);
            rtn.put("console", e.getMessage());
            writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
        }
    }
    
    /**
     * 查询导入的临时表数据
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistImportTempJson")
    public @ResponseBody
    PageJson curdlistImportTempJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(SessionKey.PUP_DCS_IMPORT_UUID);
        if (StringUtil.isEmpty(uuid)) {
            uuid = RequestUtil.getString(request, "uuid");
        }
        paramMap.put("uuid", uuid);
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<PupDcsSealModelImport> pageList = (PageList<PupDcsSealModelImport>) pupDcsSealManager.queryPupDcsSealImportTempData(paramMap, page);
        return new PageJson(pageList);
    }
    
    /**
     * 确定导入正式表
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImport")
    public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(SessionKey.PUP_DCS_IMPORT_UUID);
        /**
         * 若uuid为空，则从前端请求中获取
         */
        if (StringUtil.isEmpty(uuid)) {
            uuid = RequestUtil.getString(request, "uuid");
        }
        paramMap.put("uuid", uuid);
        paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
        paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
        /**
         * 临时表数据写入正式表
         */
        pupDcsSealManager.insertPupDcsSealImportData(paramMap, RequestUtil.getIpAddr(request));
        message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

        writeResultMessage(response.getWriter(), message);
    }
}
