package com.hanthink.pup.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperPrint;
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
import com.hanthink.pup.manager.PupDcsManager;
import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.pup.model.PupDcsModelImport;
import com.hanthink.pup.util.PrintDcsUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hanthink.util.print.PrintOrderUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.FileUtil;
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
 * @Desc : DCS管理
 * @CreateOn: 2019年1月7日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Controller
@RequestMapping("/pup/pupDcs")
public class PupDcsManageController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(PupDcsManageController.class);

    @Resource
    PupDcsManager pupDcsManager;

    /**
     * 分页查询DCS任务
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, PupDcsModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsModel> pageList = (PageList<PupDcsModel>) pupDcsManager.queryPupDcsForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出DCS单数据
     * 
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("downloadPupDcsModel")
    public void downloadPupDcsModel(HttpServletRequest request, HttpServletResponse response, PupDcsModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsModel> list = pupDcsManager.queryPupDcsByKey(model);
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

            String[] headers = { "工作日", "DCS单号", "路线代码", "路线名称", "集货类型", "车次", "计划发车时间", "计划到厂时间", "取货车型", "打印状态", "任务执行状态", "返空站台", "车牌号" };
            String[] columns = { "workDayStr", "planSheetNo", "routeCode", "routeName", "pickTypeStr", "carNum", "planStartTime", "planArriveTime",
                    "takeCar", "statusStr", "excuteStatusStr", "retEmptyPlatform", "plateNum" };
            int[] widths = { 120, 180, 120, 180, 100, 80, 150, 150, 150, 120, 120, 100, 150 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "DCS任务" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }
    
    /**
     * 导出DCS单明细数据
     * 
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("downloadPupDcsDetailModel")
    public void downloadPupDcsDetailModel(HttpServletRequest request, HttpServletResponse response, PupDcsModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsModel> list = pupDcsManager.queryPupDcsDetailByKey(model);
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

            String[] headers = { "工作日", "DCS单号", "订单号", "路线代码", "路线名称", "集货类型", "车次", "计划发车时间", "计划到厂时间", "取货车型", "打印状态", "任务执行状态", "返空站台", "车牌号" };
            String[] columns = { "workDayStr", "planSheetNo", "orderNo", "routeCode", "routeName", "pickTypeStr", "carNum", "planStartTime", "planArriveTime",
                    "takeCar", "statusStr", "excuteStatusStr", "retEmptyPlatform", "plateNum" };
            int[] widths = { 120, 180, 150, 120, 180, 100, 80, 150, 150, 150, 120, 120, 100, 150 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "DCS任务" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

    /**
     * 生成DCS任务
     * 
     * @param request
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping("genDcs")
    public void genDcs(HttpServletRequest request, HttpServletResponse response, PupDcsModel p) throws IOException {
        ResultMessage message = null;
        try {
            if(p == null){
                p = new PupDcsModel();
            }
            p.setOpeId(ContextUtil.getCurrentUser().getAccount());
            p.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            String outCode = pupDcsManager.genDcs(p);
            if ("0".equals(outCode)) {
                message = new ResultMessage(ResultMessage.SUCCESS, "生成DCS成功");
            } else {
                message = new ResultMessage(ResultMessage.FAIL, "生成DCS失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "生成DCS失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 导入DCS临时数据
     * 
     * @param request
     * @param response
     * @param file
     * @throws Exception
     */
    @RequestMapping("/importDcsModel")
    public void importDcsModel(HttpServletRequest request, HttpServletResponse response, @RequestParam("file")
    MultipartFile file) throws Exception {
        String uuid = null;
        Map<String, Object> rtn = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            uuid = (String) session.getAttribute(SessionKey.PUP_DCS_MANAGE_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            if (StringUtil.isNotEmpty(uuid)) {
                pupDcsManager.deleteDcsByUUID(uuid);
            } else {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.PUP_DCS_MANAGE_IMPORT_UUID, uuid);
            IUser user = ContextUtil.getCurrentUser();
            rtn = pupDcsManager.importDcsToTempTable(file, uuid, RequestUtil.getIpAddr(request), user);
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
     * 
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
        String uuid = (String) session.getAttribute(SessionKey.PUP_DCS_MANAGE_IMPORT_UUID);
        if (StringUtil.isEmpty(uuid)) {
            uuid = RequestUtil.getString(request, "uuid");
        }
        paramMap.put("uuid", uuid);
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<PupDcsModelImport> pageList = (PageList<PupDcsModelImport>) pupDcsManager.queryPupDcsImportTempData(paramMap, page);
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
        String uuid = (String) session.getAttribute(SessionKey.PUP_DCS_MANAGE_IMPORT_UUID);
        /**
         * 若uuid为空，则从前端请求中获取
         */
        if (StringUtil.isEmpty(uuid)) {
            uuid = RequestUtil.getString(request, "uuid");
        }
        paramMap.put("uuid", uuid);
        paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
        paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
        paramMap.put("opeUser", ContextUtil.getCurrentUser().getAccount());
        /**
         * 生成DCS
         */
        String outCode = null;
        try {
            outCode = pupDcsManager.insertPupDcsImportData(paramMap, RequestUtil.getIpAddr(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("0".equals(outCode)) {
            message = new ResultMessage(ResultMessage.SUCCESS, "调整成功");
        } else {
            message = new ResultMessage(ResultMessage.FAIL, "调整失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * DCS单行打印
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("printDcs")
    public void printDcs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ResultMessage message = null;
            // 取出DCS单号
            String planSheetNoStr = RequestUtil.getString(request, "planSheetNo");
            planSheetNoStr = URLDecoder.decode(planSheetNoStr, "utf-8");
            String[] planSheetNoArr = planSheetNoStr.split(",");
            PupDcsModel pupDcsModel = new PupDcsModel();
            pupDcsModel.setPlanSheetNoArr(planSheetNoArr);
            pupDcsModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PupDcsModel> pList = pupDcsManager.queryDcsForListToPrint(pupDcsModel);
            if (pList == null || pList.size() <= 0) {
                message = new ResultMessage(ResultMessage.FAIL, "查询打印数据失败");
                writeResultMessage(response.getWriter(), message);
                return;
            }
            pupDcsModel.setStatus("0");
            List<PupDcsModel> dList = pupDcsManager.queryDcsDetailForList(pupDcsModel);
            // 厢车的供应商有多少家
            int n = 0;
            // 飞翼车的供应商有多少家
            int m = 0;
            // 厢车的DCS单有几个
            int x = 0;
            // 飞翼车的DCS单有几个
            int f = 0;

            int total = 0;
            /**
             * 判断车辆类型,取封条号
             */
            for (int i = 0; i < pList.size(); i++) {
                PupDcsModel tempModel = pList.get(i);
                if ("0".equals(tempModel.getTakeCarType()) && "0".equals(tempModel.getStatus())) {
                    f++;
                } else if ("1".equals(tempModel.getTakeCarType()) && "0".equals(tempModel.getStatus())) {
                    x++;
                }
            }
            for (int i = 0; i < dList.size(); i++) {
                PupDcsModel tempModel = dList.get(i);
                if ("0".equals(tempModel.getTakeCarType())) {
                    m++;
                } else if ("1".equals(tempModel.getTakeCarType())) {
                    n++;
                } else {
                    message = new ResultMessage(ResultMessage.FAIL, "存在未维护取货车辆,或者维护的车辆不是飞翼车或者厢车");
                    writeResultMessage(response.getWriter(), message);
                    return;
                }
            }
            if (m > 0) {
                // 考虑到从厂里出去也需要两个封条
                total = total + (m + f) * 2;
            }

            if (n > 0) {
                // 考虑到从厂里出去也需要一个封条
                total = total + x + 1;
            }
            pupDcsModel.setSealNum(total);
            pupDcsModel.setSessionNo("");
            List<PupDcsModel> sealList = pupDcsManager.querySealForList(pupDcsModel);
            System.out.println(total + " " + sealList.size());
            if (sealList.size() < total) {
                message = new ResultMessage(ResultMessage.FAIL, "封条号不够");
                writeResultMessage(response.getWriter(), message);
                return;
            }

            // 打印N张
            List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();

            for (int i = 0; i < pList.size(); i++) {
                int index = 0;

                PupDcsModel vo = pList.get(i);
                // 每家供应需要用几个封条
                int s = 0;

                vo.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
                List<PupDcsModel> detailList;
                List<PupDcsModel> seals;
                if ("0".equals(vo.getStatus())) {
                    // 表明是未打印的，所以需要取新的封条号
                    detailList = pupDcsManager.queryDcsDetailForList(vo);
                    if ("0".equals(vo.getTakeCarType())) {
                        s = 2;
                    } else if ("1".equals(vo.getTakeCarType())) {
                        s = 1;
                    }
                    vo.setSealNum(detailList.size() * s + s);
                    vo.setSessionNo(UUID.randomUUID().toString());
                    vo.setCreateUser(ContextUtil.getCurrentUser().getAccount());
                    seals = pupDcsManager.querySealForList(vo);
                    for (int j = 0; j < seals.size(); j++) {
                        PupDcsModel model = seals.get(j);
                        model.setPlanSheetNo(vo.getPlanSheetNo());
                    }

                } else {
                    // 表明是已打印的，从TEMP表取出封条号
                    detailList = pupDcsManager.queryDcsDetailForList(vo);
                    if ("0".equals(vo.getTakeCarType())) {
                        s = 2;
                    } else if ("1".equals(vo.getTakeCarType())) {
                        s = 1;
                    }
                    vo.setSealNum(detailList.size() * s + s);
                    seals = pupDcsManager.queryPrintedSeals(vo);
                    if (seals == null || seals.size() < vo.getSealNum()) {
                        message = new ResultMessage(ResultMessage.FAIL, "数据解析异常");
                        writeResultMessage(response.getWriter(), message);
                        return;
                    }
                }
                // 拣取图片
                String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page" + File.separator
                        + "gacneLogo.jpg";
             // 拣取图片
                String pagePath1 = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page" + File.separator
                        + "gacne_logo.jpg";
                BufferedImage logoImg = ImageIO.read(new FileInputStream(pagePath));
                BufferedImage gacneLogoImg = ImageIO.read(new FileInputStream(pagePath1));
                PupDcsModel paramModel = new PupDcsModel();
                paramModel.setRouteCode(vo.getRouteCode());
                paramModel.setRouteName(vo.getRouteCode() + "（" + vo.getRouteName() + "）");
                paramModel.setPlanStartDate(vo.getPlanStartTime().substring(0, 10));
                paramModel.setPlanEndDate(vo.getPlanArriveTime().substring(0, 10));
                paramModel.setPlanStartTime(vo.getPlanStartTime().substring(10, vo.getPlanStartTime().length()));
                paramModel.setPlanEndTime(vo.getPlanArriveTime().substring(10, vo.getPlanArriveTime().length()));
                paramModel.setWorkDay(vo.getWorkDay());
                paramModel.setPlanSheetNo(vo.getPlanSheetNo());
                paramModel.setSupPickNumStr(vo.getSupPickNum() + "");
                paramModel.setFactoryName("广汽新能源");
                if ("0".equals(vo.getTakeCarType())) {
                    // 飞翼车的处理
                    paramModel.setSealNo(seals.get(index++).getSealNo() + "," + seals.get(index++).getSealNo());
                } else if ("1".equals(vo.getTakeCarType())) {
                    // 厢车的处理
                    paramModel.setSealNo(seals.get(index++).getSealNo());
                }
                paramModel.setPlanSheetNo(vo.getPlanSheetNo());
                paramModel.setLogoImg(logoImg);
                paramModel.setGacneLogoImg(gacneLogoImg);
                for (int j = 0; j < detailList.size(); j++) {
                    PupDcsModel detailVo = detailList.get(j);
                    detailVo.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
                    detailVo.setPlanSheetNo(vo.getPlanSheetNo());
                    if ("0".equals(vo.getTakeCarType())) {
                        // 飞翼车的处理
                        detailVo.setSealNo(seals.get(index++).getSealNo() + "," + seals.get(index++).getSealNo());
                    } else if ("1".equals(vo.getTakeCarType())) {
                        // 厢车的处理
                        detailVo.setSealNo(seals.get(index++).getSealNo());
                    }

                    List<PupDcsModel> orderList = pupDcsManager.queryOrderForList(detailVo);
                    detailVo.setOrderList(orderList);
                }
                // 生成多个InputStream file,防止抛异常
                String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport" + File.separator + "dcs"
                        + File.separator + "dcs_report.jasper";
                InputStream file = new FileInputStream(filenurl);
                JasperPrint jasperPrint = PrintDcsUtil.getJasPerPrintUtil(detailList, file, paramModel);
                if (null != jasperPrint) {
                    JasperPrintList.add(jasperPrint);
                }
                if ("0".equals(vo.getStatus())) {
                    // 更新封条信息
                    pupDcsManager.insertSealTemp(seals);
                }
            }
            //更新DCS任务的状态
            pupDcsManager.updateDcsPrintStatus(pupDcsModel);
            PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            String resultMsg = "打印失败";
            writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
        }
    }

    /**
     * 修改取货车牌号
     * 
     * @param request
     * @param response
     * @param mpResidualModel
     * @throws Exception
     */
    @RequestMapping("updaetDcsPlateNum")
    public void updaetDcsPlateNum(HttpServletRequest request, HttpServletResponse response, @RequestBody
    PupDcsModel pupDcsModel) throws Exception {
        String resultMsg = null;
        String planSheetNo = pupDcsModel.getPlanSheetNo();
        try {
            if (StringUtil.isEmpty(planSheetNo)) {
                resultMsg = "数据格式错误!";
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
                return;
            }
            // 更新DCS任务的车牌号
            pupDcsModel.setCreateUser(ContextUtil.getCurrentUser().getAccount());
            pupDcsModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            // 判断该DCS任务号的取货车牌号是否为空
            String plateNum = pupDcsManager.queryPlateNumByPlanSheetNo(pupDcsModel);
            if (plateNum != null && !"".equals(plateNum)) {
                resultMsg = "该DCS已录入取货车牌号,不可修改!";
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
                return;
            }
            pupDcsManager.updaetDcsPlateNum(pupDcsModel, RequestUtil.getIpAddr(request));
            resultMsg = "更新车牌号成功";
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
        } catch (Exception e) {
            log.error(e.toString());
            writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
        }
    }
}
