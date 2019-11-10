package com.hanthink.mp.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mp.manager.MpVehPlanTempManager;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hanthink.mp.model.MpVehPlanTempModel;
import com.hanthink.util.mail.MailImage;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;
/**
 * 根据表名实现的类
 */
import com.hanthink.mp.dao.MpVehPlanTempDao;

/**
 * <pre>
 * 
 * 描述：车辆计划 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpVehPlanTempManager")
public class MpVehPlanTempManagerImpl extends AbstractManagerImpl<String, MpVehPlanTempModel> implements MpVehPlanTempManager {

    public static String SMTP = "smtp.exmail.qq.com";

    @Resource
    MpVehPlanTempDao mpVehPlanTempDao;

    @Override
    protected Dao<String, MpVehPlanTempModel> getDao() {
        return mpVehPlanTempDao;
    }

    @Override
    public PageList<MpVehPlanTempModel> queryMpVehPlanTempForPage(MpVehPlanTempModel model, DefaultPage p) {
        return (PageList<MpVehPlanTempModel>) mpVehPlanTempDao.queryMpVehPlanTempForPage(model, p);
    }

    /**
     * 导出数据查询方法
     * 
     * @param uuid
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:17:56
     */
    @Override
    public List<MpVehPlanTempModel> queryMpVehPlanTempByKey(MpVehPlanTempModel model) {
        return mpVehPlanTempDao.queryMpVehPlanTempByKey(model);
    }

    @Override
    public void autoGetPlan(String factoryCode) {
        mpVehPlanTempDao.autoGetPlan(factoryCode);
    }

    @Override
    public String autoConfirm(String factoryCode) {
        return mpVehPlanTempDao.autoConfirm(factoryCode);
    }

    @Override
    public void insertOpeLog(String factoryCode, String reason, String opeUser) {
        mpVehPlanTempDao.insertOpeLog(factoryCode, reason, opeUser);
    }

    @Override
    public String confirmVehPlan(String curFactoryCode) {
        return mpVehPlanTempDao.confirmVehPlan(curFactoryCode);
    }

    @Override
    public Integer queryVehPlanTemp(String curFactoryCode) {
        return mpVehPlanTempDao.queryVehPlanTemp(curFactoryCode);
    }

    /**
     * 发送邮件
     * 
     * @throws Exception
     */
    @Override
    public String sendMail(HttpServletRequest request, String title, String content, DefaultFile sendFile, String userEmail, String factoryCode) throws Exception {
        String from = "zhuo.lin@hanthink.com";
        String username = "zhuo.lin@hanthink.com";
        String password = "Lgh686855@@@";
        String subject = title;
        boolean b;
        if (sendFile == null) {
            b = MailImage.send(request, SMTP, from, userEmail, subject, content, username, password);
        } else {
            b = MailImage.sendTo(request, SMTP, from, userEmail, subject, content, username, password, sendFile, sendFile.getFilePath());
        }
        if (b) {
            //表示发送成功
            mpVehPlanTempDao.deleteAllTempVehPlan(factoryCode);
            return "0";
        } else {
            //表示发送失败
            return "1";
        }
    }

}
