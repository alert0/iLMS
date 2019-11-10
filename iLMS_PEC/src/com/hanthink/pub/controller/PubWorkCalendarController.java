package com.hanthink.pub.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubWorkCalendarManager;
import com.hanthink.pub.model.PubWorkCalendarModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre>
 * 描述：生产日历表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubWorkCalendar")
public class PubWorkCalendarController extends GenericController {

    static Logger logger = Logger.getLogger(PubWorkCalendarController.class);

    @Resource
    PubWorkCalendarManager pubWorkCalendarManager;

    /**
     * 分页查询生产日历头表
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, PubWorkCalendarModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<PubWorkCalendarModel> pageList = (PageList<PubWorkCalendarModel>) pubWorkCalendarManager.queryPubWorkCalendarForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 右边栏位显示查询结果
     * 
     * @param request
     * @param response
     * @throws Exception
     *             void
     * @exception
     */
    @RequestMapping("getRowClick")
    public @ResponseBody
    PageJson getRowClick(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idWorkCalendar = RequestUtil.getString(request, "id");
        DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                .getPageSize()));
        PageList<PubWorkCalendarModel> pageList = (PageList<PubWorkCalendarModel>) pubWorkCalendarManager.getRowClick(idWorkCalendar, p);

        return new PageJson(pageList);
    }

    /**
     * 加载工作时间
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("loadWorkTime")
    public void loadWorkTime(HttpServletRequest request, HttpServletResponse response, PubWorkCalendarModel model) throws Exception {
        Map<String, Object> rtn = new HashMap<String, Object>();
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            // 查询工作时间
            List<PubWorkCalendarModel> tempWorkTimeModels = pubWorkCalendarManager.queryWorkTime(model);
            if (tempWorkTimeModels != null && tempWorkTimeModels.size() > 0) {
                List<PubWorkCalendarModel> workTimeModels = new ArrayList<>();
                for (int i = 0; i < tempWorkTimeModels.size(); i++) {
                    PubWorkCalendarModel workTimeModel = tempWorkTimeModels.get(i);
                    // 查询该班次下的休息时间
                    List<PubWorkCalendarModel> restTimeModels = pubWorkCalendarManager.queryRestTime(workTimeModel);
                    if (restTimeModels != null && restTimeModels.size() > 0) {
                        workTimeModel.setRestTimeList(restTimeModels);
                    }
                    workTimeModels.add(workTimeModel);
                }
                rtn.put("workTimeModels", workTimeModels);
            }
            writeResultMessage(response.getWriter(), "", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            writeResultMessage(response.getWriter(), "数据查询失败", "", null, ResultMessage.FAIL);
        }
    }

    /**
     * 加载是否工作日
     * 
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @RequestMapping("renderIsWorkDay")
    public void renderIsWorkDay(@RequestBody
    List<PubWorkCalendarModel> models, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> rtn = new HashMap<String, Object>();
        List<PubWorkCalendarModel> newPubWorkCalendarModels = new ArrayList<>();
        try {
            if (models != null && models.size() > 0) {
                for (int i = 0; i < models.size(); i++) {
                    PubWorkCalendarModel tempModel = models.get(i);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    Date d = format.parse(tempModel.getTime());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(d);
                    if ("prev".equals(tempModel.getType())) {
                        calendar.add(calendar.MONTH, -1);
                    } else if ("next".equals(tempModel.getType())) {
                        calendar.add(calendar.MONTH, 1);
                    }
                    Date date = calendar.getTime();
                    String yearMonth = format.format(date);
                    String day = String.format("%02d", Integer.parseInt(tempModel.getValue()));
                    tempModel.setTime(yearMonth + "-" + day);
                    System.out.println(tempModel.getTime());
                    tempModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
                    newPubWorkCalendarModels.add(tempModel);
                }
            }
            List<PubWorkCalendarModel> isWorkDayList = null;
            if (newPubWorkCalendarModels != null && newPubWorkCalendarModels.size() > 0) {
                isWorkDayList = pubWorkCalendarManager.queryIsWorkDayForList(newPubWorkCalendarModels);
                for (int i = 0; i < isWorkDayList.size(); i++) {
                    PubWorkCalendarModel tempModel = isWorkDayList.get(i);
                    if (tempModel != null) {
                        System.out.println(tempModel.getIsWorkDay());
                        if (Integer.parseInt(tempModel.getIsWorkDay()) > 0) {
                            tempModel.setIsWorkDay("");
                        } else {
                            tempModel.setIsWorkDay("休");
                        }
                    }
                }
            }
            rtn.put("newPubWorkCalendarModels", isWorkDayList);
            writeResultMessage(response.getWriter(), "", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            writeResultMessage(response.getWriter(), "数据查询失败", "", null, ResultMessage.FAIL);
        }
    }

    /**
     * 加载是否工作日
     * 
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
//    @RequestMapping("renderIsWorkDay")
//    public void renderIsWorkDay(@RequestBody
//    Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println(map);
//        System.out.println(map.get("dayData"));
//        Map<String, Object> rtn = new HashMap<String, Object>();
//        PubWorkCalendarModel model = new PubWorkCalendarModel();
//        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
//        model.setWorkCenter((String) map.get("workCenter"));
//        model.setTime((String) map.get("time"));
//        // 查询工作时间
//        List<PubWorkCalendarModel> tempWorkTimeModels = pubWorkCalendarManager.queryWorkTime(model);
//        if (tempWorkTimeModels != null && tempWorkTimeModels.size() > 0) {
//            List<PubWorkCalendarModel> workTimeModels = new ArrayList<>();
//            for (int i = 0; i < tempWorkTimeModels.size(); i++) {
//                PubWorkCalendarModel workTimeModel = tempWorkTimeModels.get(i);
//                // 查询该班次下的休息时间
//                List<PubWorkCalendarModel> restTimeModels = pubWorkCalendarManager.queryRestTime(workTimeModel);
//                if (restTimeModels != null && restTimeModels.size() > 0) {
//                    workTimeModel.setRestTimeList(restTimeModels);
//                }
//                workTimeModels.add(workTimeModel);
//            }
//            rtn.put("workTimeModels", workTimeModels);
//        }
//        JSONArray j = (JSONArray) map.get("dayData");
//        List<PubWorkCalendarModel> models = j.toJavaList(PubWorkCalendarModel.class);
//        List<PubWorkCalendarModel> newPubWorkCalendarModels = new ArrayList<>();
//        try {
//            if (models != null && models.size() > 0) {
//                for (int i = 0; i < models.size(); i++) {
//                    PubWorkCalendarModel tempModel = models.get(i);
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
//                    Date d = format.parse(tempModel.getTime());
//                    Calendar calendar = new GregorianCalendar();
//                    calendar.setTime(d);
//                    if ("prev".equals(tempModel.getType())) {
//                        calendar.add(calendar.MONTH, -1);
//                    } else if ("next".equals(tempModel.getType())) {
//                        calendar.add(calendar.MONTH, 1);
//                    }
//                    Date date = calendar.getTime();
//                    String yearMonth = format.format(date);
//                    String day = String.format("%02d", Integer.parseInt(tempModel.getValue()));
//                    tempModel.setTime(yearMonth + "-" + day);
//                    System.out.println(tempModel.getTime());
//                    tempModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
//                    newPubWorkCalendarModels.add(tempModel);
//                }
//            }
//            List<PubWorkCalendarModel> isWorkDayList = null;
//            if (newPubWorkCalendarModels != null && newPubWorkCalendarModels.size() > 0) {
//                isWorkDayList = pubWorkCalendarManager.queryIsWorkDayForList(newPubWorkCalendarModels);
//                for (int i = 0; i < isWorkDayList.size(); i++) {
//                    PubWorkCalendarModel tempModel = isWorkDayList.get(i);
//                    if (tempModel != null) {
//                        System.out.println(tempModel.getIsWorkDay());
//                        if (Integer.parseInt(tempModel.getIsWorkDay()) > 0) {
//                            tempModel.setIsWorkDay("");
//                        } else {
//                            tempModel.setIsWorkDay("休");
//                        }
//                    }
//                }
//            }
//            rtn.put("newPubWorkCalendarModels", isWorkDayList);
//            writeResultMessage(response.getWriter(), "", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//            writeResultMessage(response.getWriter(), "数据查询失败", "", null, ResultMessage.FAIL);
//        }
//    }
}
