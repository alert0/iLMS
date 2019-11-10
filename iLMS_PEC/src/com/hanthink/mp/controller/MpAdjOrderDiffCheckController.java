package com.hanthink.mp.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpAdjOrderDiffCheckManager;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModel;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModelImport;
import com.hanthink.mp.model.MpResidualModelImport;
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
 * <pre>
 * 描述：计划对比差异查询 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpAdjOrderDiffCheck")
public class MpAdjOrderDiffCheckController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(MpAdjOrderDiffCheckController.class);

    @Resource
    MpAdjOrderDiffCheckManager mpAdjOrderDiffCheckManager;

    /**
     * 分页查询计划对比差异查询
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, MpAdjOrderDiffCheckModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<MpAdjOrderDiffCheckModel> pageList = (PageList<MpAdjOrderDiffCheckModel>) mpAdjOrderDiffCheckManager
                    .queryMpAdjOrderDiffCheckForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载导出数据信息
     * 
     * @param request
     * @param response
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:25:20
     */
    @RequestMapping("downloadMpAdjOrderDiffCheckModel")
    public void downloadMpAdjOrderDiffCheckModel(HttpServletRequest request, HttpServletResponse response, MpAdjOrderDiffCheckModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<MpAdjOrderDiffCheckModel> list = mpAdjOrderDiffCheckManager.queryMpAdjOrderDiffCheckByKey(model);
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

            String[] headers = { "供应商代码", "出货地代码", "计算队列", "车型", "车间", "零件号", "简号", "零件名称", "差异数量", "手动调整数量", "调整后数量" };
            String[] columns = { "supplierNo", "supFactory", "unloadPort", "modelCode", "workcenter", "partNo", "partShortNo", "partName",
                    "adjDiffNum", "manuNum", "afAdjNum" };
            int[] widths = { 120, 100, 100, 100, 100, 200, 80, 150, 100, 180, 150 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "计划对比差异查询" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

    /**
     * 生成USP_MP_ZSB_DIFF
     * <p>
     * return: void
     * </p>
     * <p>
     * Description: MpVehPlanController.java
     * </p>
     * 
     * @author linzhuo
     * @date 2018年9月20日
     * @version 1.0
     */
    @RequestMapping("getMpZsbDiff")
    public void getMpZsbDiff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        try {
            /**
             * 根据当前登录人获取到工厂信息
             */
            Integer count = mpAdjOrderDiffCheckManager.getMpZsbDiff(ContextUtil.getCurrentUser().getCurFactoryCode());
            if (0 == count) {
                message = new ResultMessage(ResultMessage.SUCCESS, "生成成功");
            } else {
                message = new ResultMessage(ResultMessage.FAIL, "生成失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "生成失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 清除计划对比差异
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("clearOrderDiffData")
    public void clearOrderDiffData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        try {
            /**
             * 根据当前登录人获取到工厂信息
             */
            mpAdjOrderDiffCheckManager.clearOrderDiffData(ContextUtil.getCurrentUser().getCurFactoryCode());
            message = new ResultMessage(ResultMessage.SUCCESS, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "操作失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 保存调整数量
     * 
     * @param request
     * @param response
     * @param mpResidualModel
     * @throws Exception
     */
    @RequestMapping("save")
    public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody
    MpAdjOrderDiffCheckModel adjOrderDiffCheckModel) throws Exception {
        String resultMsg = null;
        try {
            adjOrderDiffCheckModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            mpAdjOrderDiffCheckManager.updateManuNum(adjOrderDiffCheckModel);
            resultMsg = "更新成功";
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg = "更新失败";
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
        }
    }

    /**
     * 导入临时数据
     * 
     * @param file
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importMpAdjOrderDiffModel")
    public void importMpAdjOrderDiffModel(@RequestParam("file")
    MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            IUser user = ContextUtil.getCurrentUser();
            String uuid = null;
            HttpSession session = request.getSession();
            uuid = (String) session.getAttribute(SessionKey.MP_ADJ_ORDER_DIFF_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            if (StringUtil.isNotEmpty(uuid)) {
                mpAdjOrderDiffCheckManager.deleteMpAdjOrderDiffImportTempDataByUUID(uuid);
            } else {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.MP_ADJ_ORDER_DIFF_IMPORT_UUID, uuid);

            Map<String, Object> resultMap = mpAdjOrderDiffCheckManager.importMpAdjOrderModel(file, uuid, RequestUtil.getIpAddr(request), user);
            /**
             * 这里要传递uuid***
             */
            resultMap.put("uuid", uuid);
            if ((Boolean) resultMap.get("result")) {
                writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
            } else {
                writeResultMessage(
                        response.getWriter(),
                        "失败",
                        (String) resultMap.get("console"),
                        JSONObject.fromObject(resultMap),
                        ResultMessage.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            Map<String, Object> rtn = new HashMap<String, Object>();
            rtn.put("result", false);
            rtn.put("console", "导入失败");
            writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
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
        paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<MpAdjOrderDiffCheckModelImport> pageList = (PageList<MpAdjOrderDiffCheckModelImport>) mpAdjOrderDiffCheckManager
                .queryMpAdjOrderImportTempData(paramMap, page);
        return new PageJson(pageList);
    }

    /**
     * 确认导入将临时表数据写入正式表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImport")
    public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            HttpSession session = request.getSession();
            String uuid = (String) session.getAttribute(SessionKey.MP_ADJ_ORDER_DIFF_IMPORT_UUID);
            /**
             * 若uuid为空，则从前端请求中获取
             */
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
            /**
             * 临时表数据写入正式表
             */
            mpAdjOrderDiffCheckManager.insertMpAdjOrderImportData(paramMap, RequestUtil.getIpAddr(request));
            message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());

            message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 导出临时表数据
     * @param request
     * @param response
     */
    @RequestMapping("exportTempData")
    public void exportTempData(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
            String uuid = (String) session.getAttribute(SessionKey.MP_ADJ_ORDER_DIFF_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            paramMap.put("uuid", uuid);
            List<MpAdjOrderDiffCheckModelImport> list = mpAdjOrderDiffCheckManager.queryMpAdjOrderImportTempDataForExport(paramMap);
            String[] headers = { "供应商代码", "出货地代码", "计算队列", "车型", "车间", "零件号", "简号", "零件名称", "差异数量", "手动调整数量", "调整后数量", "校验结果", "导入状态", "校验信息" };
            String[] columns = { "supplierNo", "supFactory", "unloadPort", "modelCode", "workcenter", "partNo", "partShortNo", "partName",
                    "adjDiffNum", "manuNum", "afAdjNum", "checkResult", "importStatus", "checkInfo" };
            int[] widths = { 120, 100, 100, 100, 100, 200, 80, 150, 100, 180, 150, 50, 50, 360 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "计划对比调整数据" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

}
