package com.hanthink.mp.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mp.manager.MpCalLogManager;
import com.hanthink.mp.model.MpCalLogModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre>
 * 描述：订单计算 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpCalLog")
public class MpCalLogController extends GenericController {

    private static Logger log = LoggerFactory.getLogger(MpCalLogController.class);

    /**
     * 需求计算的UUID
     */
    private String NEED_CACULATE_KEY = "NEED_CACULATE_KEY";

    /**
     * 订单发布的UUID
     */
    private String ORDER_CACULATE_KEY = "ORDER_CACULATE_KEY";

    /**
     * 锁定信息：净需求计算
     */
    public static final Integer LOCK_TYPE_NEED_DEMAND_CAL = 1;

    /** 锁定信息：生成采购订单 */
    public static final Integer LOCK_TYPE_MAKE_PURCHASE_ORDER = 2;
    
    /** 锁定信息： 生成订单号*/
    public static final Integer LOCK_tYPE_GENERATE_ORDER_NO = 3;

    /** 锁定信息：已锁定 */
    public static final String IS_LOCK_YES = "1";

    /** 锁定信息：未锁定 */
    public static final String IS_LOCK_NO = "0";

    @Resource
    MpCalLogManager mpCalLogManager;

    /**
     * 分页查询订单计算
     * 
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody
    PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse, 
    MpCalLogModel model) {
        String resultMsg = null;
        try {
            DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                    .getPageSize()));
            model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
            PageList<MpCalLogModel> pageList = (PageList<MpCalLogModel>) mpCalLogManager.queryMpCalLogForPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 需求计算
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
    @RequestMapping("demandCal")
    public void demandCal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        /**
         * 根据当前登录人获取到工厂信息
         */
        IUser user = ContextUtil.getCurrentUser();
        String arrFactory = user.getCurFactoryCode();
        String opeId = user.getAccount();
        HttpSession session = request.getSession();
        String uuid = UUID.randomUUID().toString().toUpperCase();
        /**
         * 保存本次的uuid
         */
        session.setAttribute(NEED_CACULATE_KEY, uuid);

        /**
         * 
         */
        Integer num = mpCalLogManager.selectStatus(arrFactory);
        if(num > 0) {
        	 message = new ResultMessage(ResultMessage.FAIL, "存在订单没有BOM数据");
        	 writeResultMessage(response.getWriter(), message);
        	 return ;
        }
        /**
         * 需求计算前的基础数据检查
         */
        String checkStr = this.beforeCalculateCheck(LOCK_TYPE_NEED_DEMAND_CAL, arrFactory);

        if (checkStr.isEmpty() || "".equals(checkStr)) {
            String typeLock = "1";
            Integer count = mpCalLogManager.demandCal(uuid, opeId, typeLock, arrFactory);
            message = new ResultMessage(ResultMessage.SUCCESS, count + "");
        } else {
            message = new ResultMessage(ResultMessage.SUCCESS, checkStr);
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * 计算前锁定检查
     * <p>
     * return: String
     * </p>
     * <p>
     * Description: MpCalLogController.java
     * </p>
     * 
     * @author linzhuo
     * @date 2018年9月25日
     * @version 1.0
     */
    public String beforeCalculateCheck(Integer calType, String curFactory) {

        MpCalLogModel mpCalLogModel = new MpCalLogModel();
        mpCalLogModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        mpCalLogModel.setCalType(calType);

        /**
         * 点击“需求计算”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
         */
        String isLock = mpCalLogManager.isLockCheck(mpCalLogModel);
        /**
         * 已锁定的情况
         */
        if (IS_LOCK_YES.equals(isLock)) {
            return "IS_LOCK";
        }
        return "";

    }
    
    /**
     * 订单号生成
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("generateOrderNo")
    public void generateOrderNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;

        /**
         * 根据当前登录人获取到工厂信息
         */
        IUser user = ContextUtil.getCurrentUser();
        String arrFactory = user.getCurFactoryCode();
        String opeId = user.getAccount();

        HttpSession session = request.getSession();
        String uuid = UUID.randomUUID().toString().toUpperCase();
        /**
         * 保存本次的uuid
         */
        session.setAttribute(ORDER_CACULATE_KEY, uuid);
        String typeLock = "3";

        try {
            /**
             * 点击“订单发布”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
             */
            String checkStr = this.beforeCalculateCheck(LOCK_tYPE_GENERATE_ORDER_NO, arrFactory);
            if (checkStr.isEmpty() || "".equals(checkStr)) {
                /**
                 * 生成采购订单
                 */
                Integer i = mpCalLogManager.generateOrderNo(uuid, opeId, typeLock, arrFactory);
                message = new ResultMessage(ResultMessage.SUCCESS, i+"");
            }else{
                message = new ResultMessage(ResultMessage.SUCCESS, "IS_LOCK");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "1");
        }

        writeResultMessage(response.getWriter(), message);
    }
    

    /**
     * 订单发布
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
    @RequestMapping("releaseOrder")
    public void releaseOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;

        /**
         * 根据当前登录人获取到工厂信息
         */
        IUser user = ContextUtil.getCurrentUser();
        String arrFactory = user.getCurFactoryCode();
        String opeId = user.getAccount();

        HttpSession session = request.getSession();
        String uuid = UUID.randomUUID().toString().toUpperCase();
        
        /**
         * 增加逻辑判断,前一次操作是不是订单号生成,而且MM_MP_PUR_ORDER_TEMP这个表要有数据
         */
        boolean b = mpCalLogManager.isAvailable(arrFactory);
        //不能执行后续的动作
        if(!b){
            message = new ResultMessage(ResultMessage.SUCCESS, "3"); 
            writeResultMessage(response.getWriter(), message);
            return;
        }
        
        /**
         * 保存本次的uuid
         */
        session.setAttribute(ORDER_CACULATE_KEY, uuid);
        String typeLock = "2";

        try {
            /**
             * 点击“订单发布”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
             */
            String checkStr = this.beforeCalculateCheck(LOCK_TYPE_MAKE_PURCHASE_ORDER, arrFactory);
            if (checkStr.isEmpty() || "".equals(checkStr)) {
                /**
                 * 生成采购订单
                 */
                Integer i = mpCalLogManager.releaseOrder(uuid, opeId, typeLock, arrFactory);
                message = new ResultMessage(ResultMessage.SUCCESS, i+"");
            }else{
                message = new ResultMessage(ResultMessage.SUCCESS, "IS_LOCK");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = new ResultMessage(ResultMessage.FAIL, "1");
        }

        writeResultMessage(response.getWriter(), message);
    }
}
