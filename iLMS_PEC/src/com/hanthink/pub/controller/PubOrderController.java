package com.hanthink.pub.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.pub.manager.PubOrderManager;
import com.hanthink.pub.model.PubOrderModel;
import com.hanthink.pub.model.PubOrderModelImport;
import com.hanthink.pup.util.PupUtil;
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
 * 零件订购基础数据
 * 
 * @Desc :
 * @CreateOn: 2018年11月3日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Controller
@RequestMapping("/pub/pubOrder")
public class PubOrderController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(PubOrderController.class);

    @Resource
    PubOrderManager pubOrderManager;

    /**
     * 分页查询零件订购基础数据
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, PubOrderModel model) throws IOException {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            /**
             * 判断pageList是否为空
             */
            List<PubOrderModel> pageList;
            if (!PupUtil.isAllFieldNull(model)) {
                pageList = (PageList<PubOrderModel>) pubOrderManager.queryAllPubOrderForPage(model, p);
            } else {
                /**
                 * 没有数据返回空行
                 */
                pageList = new ArrayList<>();
            }
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            writeResultMessage(reponse.getWriter(), e.getMessage(), "查询失败", ResultMessage.FAIL);
        }
        return null;
    }

    /**
     * 剩余量主数据明细页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     *             ModelAndView
     */
    // @RequestMapping("curdgetJson")
    // public @ResponseBody PubOrderModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    // String id=RequestUtil.getString(request, "id");
    // try {
    // if(StringUtil.isEmpty(id)){
    // return new PubOrderModel();
    // }
    // return pubOrderManager.get(id);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    /**
     * 修改零件订购基础数据
     * 
     * @param request
     * @param response
     * @param pubOrderModelImport
     * @throws Exception
     */
    @RequestMapping("save")
    public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody
    PubOrderModelImport pubOrderModelImport) throws Exception {
        String resultMsg = null;
        IUser user = ContextUtil.getCurrentUser();
        try {
            if (pubOrderModelImport != null) {
                pubOrderManager.updatePurOrder(pubOrderModelImport, user, RequestUtil.getIpAddr(request));
                resultMsg = "更新零件订购基础主数据成功";
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e.toString());
            writeResultMessage(response.getWriter(), e.getMessage(), e.getMessage(), ResultMessage.FAIL);
        }
    }

    /**
     * 批量删除订购零件基础数据
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("remove")
    public void remove(@RequestBody
    List<PubOrderModel> purOrderList, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        try {
            if (purOrderList != null && purOrderList.size() > 0) {
                pubOrderManager.batchRemovePurOrder(purOrderList, RequestUtil.getIpAddr(request), ContextUtil.getCurrentUser());
            }
            message = new ResultMessage(ResultMessage.SUCCESS, "批量删除订购零件基础数据成功");
        } catch (Exception e) {
        	e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "批量删除订购零件基础数据失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 下载导出PubOrder数据信息
     * 
     * @param request
     * @param response
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:25:20
     */
    @RequestMapping("downloadPubOrderModel")
    public void downloadPubOrderModel(HttpServletRequest request, HttpServletResponse response, PubOrderModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PubOrderModel> list = pubOrderManager.queryPubOrderByKey(model);
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

            String[] headers = { "零件号", "车型", "车间", "供应商代码", "出货地代码", "简号",
                    "订购包装数", "安全库存", "零件名称", "供应商名称", "生效日期", "失效日期",
                    "物流模式",  "出货仓库","出货仓库类别", "信息点", "组单台套数",
                    "组单提前台套数", "发车取货(台套数)", "备件(台套数)", "装车运输(台套数)",
                    "零件组代码", "零件组名称", "组票台套数", "组票指示票数", "是否考虑出货地切换",
                    "是否生成订单", "装车代码", "装车代码描述", "计算队列", "初始SortId",
                    "收容数", "到货仓库", "到货仓库类别", "配送提前台套数", "到货提前台套数",
                    "记号", "卸货口"};
            String[] columns = { "partNo", "modelCode", "workcenter", "supplierNo", "supFactory", "partShortNo",
                    "orderPackageInt", "safeNumInt", "partName", "supplierName", "effStartStr", "effEndStr",
                    "logisticsModel",  "shipDepot", "shipDepotType", "planCode","orderProductNumInt",
                    "aheadProductNumInt", "dispatchNumInt", "prepareNumInt", "deliveryNumInt", 
                    "partgroupNo", "partgroupName","insProductNumInt", "orderInsNumInt", "genInsWay",
                    "orderFlag", "routeCode", "routeDesc", "mpUnloadPort", "firstSortIdInt",
                    "standardPackageInt", "arrDepot", "arrDepotType", "distributionNumInt", "arriveNumInt",
                    "partMark", "unloadPort"};
            int[] widths = { 160, 80, 80, 100, 80,
                    100, 80, 150, 150, 100, 100,
                    80, 100, 100, 120, 150, 100,
                    150, 150, 150, 150,
                    100, 100, 100, 120, 150,
                    120, 80, 120, 80, 100,
                    100, 80, 120, 150, 150,
                    100, 80};
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLS, request, response, "订购零件基础数据" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

    /**
     * 导入订购基础数据
     * 
     * @param file
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importPubOrderModel")
    public void importPubOrderModel(@RequestParam("file")
    MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            IUser user = ContextUtil.getCurrentUser();
            String uuid = null;
            HttpSession session = request.getSession();
            uuid = (String) session.getAttribute(SessionKey.PUB_ORDER_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            if (StringUtil.isNotEmpty(uuid)) {
                pubOrderManager.deletePubOrderImportTempDataByUUID(uuid);
            } else {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.PUB_ORDER_IMPORT_UUID, uuid);

            Map<String, Object> resultMap = pubOrderManager.importPubOrderModel(file, uuid, RequestUtil.getIpAddr(request), user);
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
            rtn.put("console", "导入失败!");
            writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
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
        String uuid = (String) session.getAttribute(SessionKey.PUB_ORDER_IMPORT_UUID);
        if (StringUtil.isEmpty(uuid)) {
            uuid = RequestUtil.getString(request, "uuid");
        }
        paramMap.put("uuid", uuid);
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<PubOrderModelImport> pageList = (PageList<PubOrderModelImport>) pubOrderManager.queryPubOrderPartImportTempData(paramMap, page);
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
        String uuid = (String) session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
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
        pubOrderManager.insertPubOrderImportData(paramMap, RequestUtil.getIpAddr(request));
        message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 导出临时表数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping("exportTempData")
    public void exportTempData(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
            String uuid = (String) session.getAttribute(SessionKey.PUB_ORDER_IMPORT_UUID);
            if (StringUtil.isEmpty(uuid)) {
                uuid = RequestUtil.getString(request, "uuid");
            }
            paramMap.put("uuid", uuid);
            List<PubOrderModelImport> list = pubOrderManager.queryPubOrderImportTempDataForExport(paramMap);

            String[] headers = { "零件号", "车型", "车间", "供应商代码","出货地代码", "简号",
                    "订购包装数", "安全库存", "零件名称", "供应商名称", "生效日期", "失效日期",
                    "物流模式",  "出货仓库","出货仓库类别", "信息点", "组单台套数",
                    "组单提前台套数", "发车取货(台套数)", "备件(台套数)", "装车运输(台套数)",
                    "零件组代码", "零件组名称", "组票台套数", "组票指示票数", "是否考虑出货地切换",
                    "是否生成订单", "装车代码", "装车代码描述", "计算队列", "初始SortId",
                    "收容数", "到货仓库", "到货仓库类别", "配送提前台套数", "到货提前台套数",
                    "记号", "卸货口", "校验结果", "校验信息" };
            String[] columns = { "partNo", "modelCode", "workcenter", "supplierNo", "supFactory", "partShortNo",
                    "orderPackageInt", "safeNumInt", "partName", "supplierName", "effStartStr", "effEndStr",
                    "logisticsModel",  "shipDepot", "shipDepotType", "planCode","orderProductNumInt",
                    "aheadProductNumInt", "dispatchNumInt", "prepareNumInt", "deliveryNumInt", 
                    "partgroupNo", "partgroupName","insProductNumInt", "orderInsNumInt", "genInsWay",
                    "orderFlag", "routeCode", "routeDesc", "mpUnloadPort", "firstSortIdInt",
                    "standardPackageInt", "arrDepot", "arrDepotType", "distributionNumInt", "arriveNumInt",
                    "partMark", "unloadPort", "checkResult", "checkInfo" };
            int[] widths = { 160, 80, 80, 100, 80,
                    100, 80, 150, 150, 100, 100,
                    80, 100, 100, 120, 150, 100,
                    150, 150, 150, 150,
                    100, 100, 100, 120, 150,
                    120, 80, 120, 80, 100,
                    100, 80, 120, 150, 150,
                    100, 80, 80, 300};
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLS, request, response, "零件订购基础数据导入临时数据" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    /**
     * 获取计算队列填充下拉框
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     *             PageJson
     * @exception
     */
    @RequestMapping("queryPlanCode")
    public @ResponseBody
    Object queryPlanCode(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        try {
            List<DictVO> models = pubOrderManager.queryPlanCode();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }
    
    /**
     * 获取计算队列填充下拉框
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     *             PageJson
     * @exception
     */
    @RequestMapping("queryPlanCode1")
    public @ResponseBody
    Object queryPlanCode1(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        try {
            List<DictVO> models = pubOrderManager.queryPlanCode1();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

    /**
     * 获取计算队列填充下拉框
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     *             PageJson
     * @exception
     */
    @RequestMapping("queryArriveDepot")
    public @ResponseBody
    Object queryArriveDepot(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        try {
            List<DictVO> models = pubOrderManager.queryArriveDepot();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

    /**
     * 获取卸货口下拉框
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     *             PageJson
     * @exception
     */
    @RequestMapping("queryUnloadPort")
    public @ResponseBody
    Object queryUnloadPort(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        try {
            List<DictVO> models = pubOrderManager.queryUnloadPort();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

}
