package com.hanthink.mp.manager.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpAdjOrderDiffDao;
import com.hanthink.mp.manager.MpAdjOrderDiffManager;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hanthink.util.mail.MailImage;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 *  
 * 描述：计划对比 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpAdjOrderDiffManager")
public class MpAdjOrderDiffManagerImpl extends AbstractManagerImpl<String, MpAdjOrderDiffModel>
		implements MpAdjOrderDiffManager {
	@Resource
	MpAdjOrderDiffDao mpAdjOrderDiffDao;

	public static String SMTP = "smtp.exmail.qq.com";

	@Override
	protected Dao<String, MpAdjOrderDiffModel> getDao() {
		return mpAdjOrderDiffDao;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param uuid
	 * @author linzhuo
	 * @DATE 2018年9月10日 下午4:17:56
	 */
	@Override
	public PageList<MpAdjOrderDiffModel> queryMpAdjOrderDiffForPage(MpAdjOrderDiffModel model, DefaultPage p) {
		return (PageList<MpAdjOrderDiffModel>) mpAdjOrderDiffDao.queryMpAdjOrderDiffForPage(model, p);
	}

	/**
	 * 导出数据查询方法
	 * 
	 * @param uuid
	 * @author linzhuo
	 * @DATE 2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpAdjOrderDiffModel> queryMpAdjOrderDiffByKey(MpAdjOrderDiffModel model) {
		return mpAdjOrderDiffDao.queryMpAdjOrderDiffByKey(model);
	}

	/**
	 * 执行计划对比
	 */
	@Override
	public Integer getAdjOrderDiff(String curFactoryCode, String account) {
		return mpAdjOrderDiffDao.getAdjOrderDiff(curFactoryCode, account);
	}

	/**
	 * 根据供应商代码查询相关信息
	 */
	@Override
	public String sendMail(HttpServletRequest request, MpAdjOrderDiffModel[] model) throws Exception {
		/**
		 * 供应商代码去重复
		 */
		List<String> supplierList = new ArrayList<>();
		List<String> idArrList = new ArrayList<>();
		List<String> sendFlagList = new ArrayList<>();
		List<String> supCheckList = new ArrayList<>();
		List<String> partCheckList = new ArrayList<>();
		for (int i = 0; i < model.length; i++) {
			/**
			 * 将选中的id放入idArr集合
			 */
			idArrList.add(model[i].getIdStr());
			if (model[i].getCodeValueName().equals("未发送")) {
				sendFlagList.add(model[i].getCodeValueName());
				supCheckList.add(model[i].getSupFactory());
				partCheckList.add(model[i].getPartNo());
				if (!supplierList.contains(model[i].getSupplierNo())) {
					supplierList.add(model[i].getSupplierNo());
				}
			}
		}
		if (supCheckList.size() == 0 || supCheckList.contains(null) || partCheckList.size() == 0
				|| partCheckList.contains(null) || supplierList.size() == 0 || supplierList.contains(null)) {
			return "4";
		}
		if (sendFlagList.size() == 0) {
			return "2";
		}
		for (int j = 0; j < supplierList.size(); j++) {
			// 查询该供应商的邮箱
			List<MpAdjOrderDiffModel> email = mpAdjOrderDiffDao.queryEmail(supplierList.get(j));
			if (email.size() != 1) {
				return "1";
			}
			String to = email.get(0).getImportMail() + "," + email.get(0).getPtMail() + email.get(0).getMassMail()
					+ email.get(0).getExcepMailA() + email.get(0).getExcepMailB() + email.get(0).getDeviceMail()
					+ email.get(0).getImportMailA() + email.get(0).getPtMailA() + email.get(0).getMassMailA()
					+ email.get(0).getDeviceMailA() + email.get(0).getPackMailA() + email.get(0).getPackMailB()
					+ email.get(0).getPtLogisticsMail() + email.get(0).getPtLogisticsMailA() + email.get(0).getMassLogisticsMail()
					+ email.get(0).getMassLogisticsMailA();
			List<String> idList = new ArrayList<>();
			List<String> idStrList = new ArrayList<>();
			List<String> supFactoryList = new ArrayList<>();
			List<String> factoryCodeList = new ArrayList<>();
			List<String> supList = new ArrayList<>();
			List<String> partList = new ArrayList<>();
			List<String> adjDateList = new ArrayList<>();
			List<String> calDiffNumList = new ArrayList<>();
			List<String> adjDiffNumList = new ArrayList<>();
			List<String> userList = new ArrayList<>();
			/**
			 * 转化时间格式
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// Date date= sdf.parse(model[i].getAdjDateStr());//字符串转成date对象类型
			DateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
			// String str2= sdf2.format(date);//date类型转换成字符串

			for (int i = 0; i < model.length; i++) {
				if (supplierList.get(j).equals(model[i].getSupplierNo())) {
					idList.add(UniqueIdUtil.getSuid());
					idStrList.add(model[i].getIdStr());
					supFactoryList.add(model[i].getSupFactory());
					factoryCodeList.add(model[i].getFactoryCode());
					supList.add(model[i].getSupplierNo());
					partList.add(model[i].getPartNo());
					adjDateList.add(sdf2.format(sdf.parse(model[i].getAdjDateStr())));
					calDiffNumList.add(model[i].getCalDiffNum());
					adjDiffNumList.add(model[i].getAdjDiffNum());
					userList.add(ContextUtil.getCurrentUser().getAccount());
				}
			}
			String content = "尊敬的供应商，您好，";
			List<MpAdjOrderDiffModel> pageList = new ArrayList<>();
			for (int i = 0; i < supList.size(); i++) {
				if (adjDiffNumList.get(i).equals("0")) {
					content += adjDateList.get(i) + "，零件:" + partList.get(i) + "，调整数量为：" + calDiffNumList.get(i)
							+ "<br/>";
				} else {
					content += adjDateList.get(i) + "，零件:" + partList.get(i) + "，调整数量为：" + adjDiffNumList.get(i)
							+ "<br/>";
				}
				/**
				 * 将List集合中的数据放入到对象中
				 */
				MpAdjOrderDiffModel teMpAdjOrderDiffModel = new MpAdjOrderDiffModel();
				teMpAdjOrderDiffModel.setId(idList.get(i));
				teMpAdjOrderDiffModel.setIdStr(idStrList.get(i));
				teMpAdjOrderDiffModel.setSupFactory(supFactoryList.get(i));
				teMpAdjOrderDiffModel.setFactoryCode(factoryCodeList.get(i));
				teMpAdjOrderDiffModel.setSupplierNo(supList.get(i));
				teMpAdjOrderDiffModel.setPartNo(partList.get(i));
				teMpAdjOrderDiffModel.setAdjDateStr(adjDateList.get(i));
				teMpAdjOrderDiffModel.setCalDiffNum(calDiffNumList.get(i));
				teMpAdjOrderDiffModel.setAdjDiffNum(adjDiffNumList.get(i));
				teMpAdjOrderDiffModel.setCreationUser(userList.get(i));
				pageList.add(teMpAdjOrderDiffModel);
			}
			content += ",请登录到iLMS信息共享平台(ilms.gacne.com.cn:1126)的供应商管理菜单下供应商能力反馈界面进行反馈。";
			String from = "i-lms@gacne.com.cn";
			String username = "i-lms@gacne.com.cn";
			String password = "gacne@2017";
			String subject = "计划变更导致零件差异";
			boolean b;

			b = MailImage.sendMore(SMTP, from, to, subject, content, username, password);
			if (b) {

				mpAdjOrderDiffDao.insertMpAdjSupFeedback(pageList);
				/**
				 * 更新发送标识
				 */
				String[] idArr = new String[idArrList.size()];
				idArrList.toArray(idArr);
				MpAdjOrderDiffModel idVo = new MpAdjOrderDiffModel();
				idVo.setIdArr(idArr);
				mpAdjOrderDiffDao.updateSendFlag(idVo);
			}

		}
		return "0";

	}

	/**
	 * 调整数量并记录日志
	 */
	@Override
	public void updateAndLog(MpAdjOrderDiffModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("调整数量");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_ADJ_ORDER_DIFF");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		mpAdjOrderDiffDao.update(model);

	}

}
