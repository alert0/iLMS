package com.hanthink.mp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hanthink.mp.manager.MpCalLogManager;
import com.hanthink.mp.manager.MpVehPlanTempManager;
import com.hanthink.mp.manager.impl.MpCalLogManagerImpl;
import com.hanthink.mp.manager.impl.MpVehPlanTempManagerImpl;
import com.hanthink.mp.model.MpCalLogModel;

public class VehPlanListener implements ServletContextListener {

    String factoryCode = "2000";

    MpVehPlanTempManager manager;

    MpCalLogManager calLogManager;

    // 定义自动获取计划定时器
    Timer autoGetPlanTimer = null;

    // 定义自动确认定时器
    Timer autoConfirmTimer = null;

    // 定义自动获取计划方法每天的12点执行
    public void autoGetPlan(ServletContextEvent sce) {
        manager = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(MpVehPlanTempManagerImpl.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 12, 00, 00);// 设置要执行的日期时间

        Date defaultdate = calendar.getTime();
        
        autoGetPlanTimer = new Timer();
        autoGetPlanTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 调用存储过程每天12点根据工作日历和周计划获取生产计划到临时表
                manager.autoGetPlan(factoryCode);
            }
        }, defaultdate, 24 * 60 * 60 * 1000);// 24* 60* 60 * 1000
    }

    // 定义自动确认计划方法每天16点执行
    public void autoConfirm(ServletContextEvent sce) {
        manager = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(MpVehPlanTempManagerImpl.class);
        calLogManager = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(MpCalLogManagerImpl.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 16, 00, 00);// 设置要执行的日期时间

        Date defaultdate = calendar.getTime();

        autoConfirmTimer = new Timer();
        autoConfirmTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 调用存储过程每天16点将临时表的数据写入正式表
                String outCode = manager.autoConfirm(factoryCode);
                if ("1".equals(outCode)) {
                    // 表明将临时表数据写入了正式表,需要执行需求计算的存储过程
                    // 判断是否有人正在执行计算
                    MpCalLogModel model = new MpCalLogModel();
                    model.setCalType(1);
                    model.setFactoryCode(factoryCode);
                    Integer num = calLogManager.selectStatus(factoryCode);
                    if (num > 0) {
                        manager.insertOpeLog(factoryCode, "存在订单没有BOM数据!", "SYSTEM");
                        return;
                    }
                    String isLock = calLogManager.isLockCheck(model);
                    String checkStr = "";
                    if ("1".equals(isLock)) {
                        checkStr = "IS_LOCK";
                    } else {
                        checkStr = "";
                    }

                    /**
                     * 执行需求计算
                     */
                    if (checkStr.isEmpty() || "".equals(checkStr)) {
                        String typeLock = "1";
                        calLogManager.demandCal(UUID.randomUUID().toString().toUpperCase(), "SYSTEM", typeLock, factoryCode);
                    } else {
                        manager.insertOpeLog(factoryCode, "需求计算或者订单生成处于锁定状态!", "SYSTEM");
                    }
                }
            }
        }, defaultdate, 24 * 60 * 60 * 1000);// 设置24小时执行一次
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        autoConfirmTimer.cancel();
        autoGetPlanTimer.cancel();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        autoGetPlan(sce);
        autoConfirm(sce);
    }

}
