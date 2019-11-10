package com.hanthink.pub.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubProPlanManager;
import com.hanthink.pub.model.PubProPlanModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @Desc : MES下发W+3周计划查询
 * @CreateOn: 2019年1月3日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Controller
@RequestMapping("/pub/pubProPlan")
public class PubProPlanController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(PubProPlanController.class);

    @Resource
    PubProPlanManager pubProPlanManager;

    /**
     * 分页查询W+3周计划
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, PubProPlanModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PubProPlanModel> pageList = (PageList<PubProPlanModel>) pubProPlanManager.queryPubProPlanForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("downloadPubProPlanModel")
    public void downloadPubProPlanModel(HttpServletRequest request, HttpServletResponse response, PubProPlanModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PubProPlanModel> list = pubProPlanManager.queryPubProPlanByKey(model);
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

            String[] headers = { "生产单号", "VIN", "车辆类型", "焊装上线时间",
                    "总装下线时间", "MTOC", "车型", "阶段", "车辆状态", "过点工位", "过点时间",
                    "销售单号", "销售单行号" };
            String[] columns = { "orderNo", "vin", "orderTypeStr", "weOnTimeStr",
                    "afOffTimeStr", "mtoc", "modelCode", "phaseStr", "carStatusStr", "stationCode", "passTime",
                    "saleNo", "saleRowNo"};
            int[] widths = { 120, 120, 120, 100, 150, 120, 80, 80, 120, 120, 150, 150, 150 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "车辆信息" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

}
