package com.hanthink.mp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mp.manager.MpVehPlanTempManager;
import com.hanthink.mp.model.MpVehPlanTempModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * <pre>
 * 描述：车辆计划  控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpVehPlanTemp")
public class MpVehPlanTempController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(MpVehPlanTempController.class);
   
    @Resource
    FileManager fileManager;
    @Resource
    MpVehPlanTempManager mpVehPlanTempManager;

    /**
     * 分页查询车辆计划
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, MpVehPlanTempModel model) {
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<MpVehPlanTempModel> pageList = (PageList<MpVehPlanTempModel>) mpVehPlanTempManager.queryMpVehPlanTempForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载导出MpVehPlanTemp数据信息
     * 
     * @param request
     * @param response
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:25:20
     */
    @RequestMapping("downloadMpVehPlanTempModel")
    public void downloadMpVehPlanTempModel(HttpServletRequest request, HttpServletResponse response, MpVehPlanTempModel model) {
        try {
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            List<MpVehPlanTempModel> list = mpVehPlanTempManager.queryMpVehPlanTempByKey(model);
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

            String[] headers = { "类型", "生产排序号", "车型", "生产单号",
                    "总装下线时间", "焊装下线时间", "生产阶段", 
                    "计算时间" };
            String[] columns = { "type", "sortIdStr", "carType", "orderNo",
                    "afoffTimeStr","weOnTimeStr", "codeValueNameD", 
                    "writeTime" };
            int[] widths = { 120, 150, 80, 150, 150, 150, 80, 200 };
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
            ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "临时车辆计划" + df.format(new Date()), list, headers, widths, columns);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
        }

    }

    /**
     * 计划确认点击确认导入按钮将数据写入正式表
     * 
     * @param request
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping("confirmVehPlan")
    public void confirmVehPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ResultMessage message;
            Integer count = mpVehPlanTempManager.queryVehPlanTemp(ContextUtil.getCurrentUser().getCurFactoryCode());
            if (count <= 0) {
                message = new ResultMessage(ResultMessage.FAIL, "没有数据!");
            } else {
                // 点击确认导入将临时表的数据导入正式表
                String outCode = mpVehPlanTempManager.confirmVehPlan(ContextUtil.getCurrentUser().getCurFactoryCode());

                if ("1".equals(outCode)) {
                    message = new ResultMessage(ResultMessage.FAIL, "操作执行失败！");
                }
                message = new ResultMessage(ResultMessage.SUCCESS, "操作执行成功！");
            }
            writeResultMessage(response.getWriter(), message);
        } catch (Exception e) {
            e.printStackTrace();
            ResultMessage message = new ResultMessage(ResultMessage.FAIL, "操作执行失败！");
            writeResultMessage(response.getWriter(), message);
        }

    }
    
	/**
	 * 接收附件以及标题和文字
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("getMail")
	public void getMail(HttpServletRequest request, HttpServletResponse response, MpVehPlanTempModel model) throws Exception {
		String resultMsg = null;
		String title = model.getTitle();
		String content = model.getContent();
		String userEmail = "156857947@qq.com";
		String fileIdStr = model.getFileIdStr();
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		if (("").equals(title)||title ==null || ("").equals(content)||content ==null) {
			resultMsg = "标题和内容不能为空";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
		} else {
			String result;
			if (("").equals(fileIdStr) || fileIdStr == null) {
				// 不带附件邮件发送
				result = mpVehPlanTempManager.sendMail(request, title, content, null, userEmail, factoryCode);
				if (("1").equals(result)) {
					resultMsg = "该邮箱不存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				} else {
					resultMsg = "邮件发送成功";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
				}
			} else {
				/**
				 * 根据id取附件名
				 */
				DefaultFile sendFile = fileManager.get(fileIdStr);
				// 带附件邮件发送
				result = mpVehPlanTempManager.sendMail(request, title, content, sendFile, userEmail, factoryCode);
				if (("1").equals(result)) {
					resultMsg = "该邮箱不存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				} else {
					resultMsg = "邮件发送成功";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
				}
			}
		}
	}

}
