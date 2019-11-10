package com.hanthink.mon.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonEmptyReturnKbManager;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.sys.util.ContextUtil;

@Controller
@RequestMapping("/mon/emptyReturnKb")
public class MonEmptyReturnKbController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(MonEmptyReturnKbController.class);

	@Resource
	private MonEmptyReturnKbManager monEmptyReturnKbManager;

	/**
	 * 空箱返还看板
	 * 
	 * @param request
	 * @param response
	 *            Lxh
	 * @throws IOException
	 */
	/**
	 * 看板头部信息
	 * <p>
	 * return: List<EmptyReturnKbModel>
	 * </p>
	 * <p>
	 * Description: MonEmptyReturnKbController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2019年3月11日
	 * @version 1.0
	 */
	@RequestMapping("/queryEmptyReturnTop")
	public @ResponseBody List<EmptyReturnKbModel> queryEmptyReturnTop(HttpServletRequest request,
			HttpServletResponse response, EmptyReturnKbModel emptyReturnKbModel) throws Exception {
		ResultMessage message = null;
		/**
		 * 根据IP获取站台
		 */
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("ip", RequestUtil.getIpAddr(request));
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("opeUser", ContextUtil.getCurrentUser().getAccount());
		EmptyReturnKbModel model = monEmptyReturnKbManager.getShowMessage(paramMap);
		if (null != model) {
			model.setIp(RequestUtil.getIpAddr(request));
		}
		/**
		 * IP地址
		 */
		String ip = RequestUtil.getIpAddr(request);
		String workCenter = emptyReturnKbModel.getWorkCenter();
		if (("").equals(workCenter) || null == workCenter) {
			workCenter = monEmptyReturnKbManager.selectWorkCenterByIp(ip);
		}
		String retEmptyPlatform = emptyReturnKbModel.getRet();
		if ((("").equals(retEmptyPlatform) || null == retEmptyPlatform) && null != model) {
			retEmptyPlatform = model.getRetEmptyPlatform();
		}
		try {
			/**
			 * 根据站台获取DCS单头信息
			 */
			List<EmptyReturnKbModel> topList = (List<EmptyReturnKbModel>) monEmptyReturnKbManager
					.selectDCS(retEmptyPlatform, workCenter);
			if (StringUtil.isEmpty(retEmptyPlatform)) {
				retEmptyPlatform = "00";
			} else {
				if (Integer.parseInt(retEmptyPlatform) < 10) {
					retEmptyPlatform = "0" + retEmptyPlatform;
				}
			}
			int delFlag = 3 - topList.size();
			if (topList.size() > 0) {
				topList.get(0).setRetEmptyPlatform(retEmptyPlatform);
			}
			for (int i = 0; i < delFlag; i++) {
				EmptyReturnKbModel empModel = new EmptyReturnKbModel();
				empModel.setPlanSheetNo("");
				empModel.setPlateNum("");
				empModel.setRetEmptyPlatform(retEmptyPlatform);
				empModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				empModel.setActualArriveTimeStr("");
				topList.add(empModel);

			}
			return topList;
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

	/**
	 * 看板底部信息
	 * <p>
	 * return: List<EmptyReturnKbModel>
	 * </p>
	 * <p>
	 * Description: MonEmptyReturnKbController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2019年3月11日
	 * @version 1.0
	 */
	@RequestMapping("/queryEmptyReturnBottom")
	public @ResponseBody List<EmptyReturnKbModel> queryEmptyReturnBottom(HttpServletRequest request,
			HttpServletResponse response, EmptyReturnKbModel eModel) throws Exception {
		ResultMessage message = null;
		/**
		 * 根据IP获取站台
		 */
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("ip", RequestUtil.getIpAddr(request));
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("opeUser", ContextUtil.getCurrentUser().getAccount());
		EmptyReturnKbModel model = monEmptyReturnKbManager.getShowMessage(paramMap);
		if (null != model) {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		}
		String retEmptyPlatform = eModel.getRetEmptyPlatform();
		if ((("{}").equals(retEmptyPlatform)||("").equals(retEmptyPlatform) || null == retEmptyPlatform) && (null != model)) {
			retEmptyPlatform = model.getRetEmptyPlatform();
		}
		/**
		 * IP地址
		 */
		String ip = RequestUtil.getIpAddr(request);
		String workCenter = eModel.getWorkCenter();
		if (("{}").equals(workCenter)||("").equals(workCenter) || null == workCenter) {
			workCenter = monEmptyReturnKbManager.selectWorkCenterByIp(ip);
		}
		try {
			/**
			 * 根据站台获取DCS单头信息
			 */
			List<EmptyReturnKbModel> topList = (List<EmptyReturnKbModel>) monEmptyReturnKbManager
					.selectDCS(retEmptyPlatform, workCenter);
			int delFlag = 3 - topList.size();
			for (int i = 0; i < delFlag; i++) {
				EmptyReturnKbModel empModel = new EmptyReturnKbModel();
				empModel.setPlanSheetNo("");
				empModel.setPlateNum("");
				empModel.setRetEmptyPlatform(retEmptyPlatform);
				empModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				empModel.setActualArriveTimeStr("");
				topList.add(empModel);

			}

			List<EmptyReturnKbModel> showList = new ArrayList<EmptyReturnKbModel>();
			for (int p = 0; p < topList.size(); p++) {
				EmptyReturnKbModel dcsModel = topList.get(p);
				/**
				 * 记录从数据库中查到的出发时间
				 */
				String arriveTime = dcsModel.getPlanArriveTimeStr();
				dcsModel.setWorkCenter(workCenter);
				dcsModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				/**
				 * 根据DCS单号获取DCS单明细信息
				 */
				List<EmptyReturnKbModel> list = new ArrayList<EmptyReturnKbModel>();
				List<EmptyReturnKbModel> detailList = (List<EmptyReturnKbModel>) monEmptyReturnKbManager
						.selectDCSDetail(dcsModel);
				/**
				 * 判断该订单是同步，拉动，还是取货
				 */
				if (detailList.size() == 0) {
					list = monEmptyReturnKbManager.selectOrderNoByNull();
				} else {
					for (int j = 0; j < detailList.size(); j++) {
						List<EmptyReturnKbModel> swList = monEmptyReturnKbManager.selectOrderNoBySw(detailList.get(j));
						if (swList.size() == 0) {
							List<EmptyReturnKbModel> jitList = monEmptyReturnKbManager
									.selectOrderNoByJit(detailList.get(j));
							if (jitList.size() == 0) {
								List<EmptyReturnKbModel> jisoList = monEmptyReturnKbManager
										.selectOrderNoByJiso(detailList.get(j));
								if (jisoList.size() != 0) {
									for (int s = 0; s < jisoList.size(); s++) {
										list.add(jisoList.get(s));
									}
								}
							} else {
								for (int t = 0; t < jitList.size(); t++) {
									list.add(jitList.get(t));
								}
							}
						} else {
							for (int w = 0; w < swList.size(); w++) {
								list.add(swList.get(w));
							}
						}
					}
				}
				/**
				 * 获取各类时间
				 */
				int bigWorkTime = 0;
				int smallWorkTime = 0;
				int restWorkTime = 0;
				int downWorkTime = 0;
				Integer totalTime = 0;
				List<EmptyReturnKbModel> timeList = monEmptyReturnKbManager.selectPubSysParam();
				for (int m = 0; m < timeList.size(); m++) {
					EmptyReturnKbModel timeModel = timeList.get(m);
					if (("ER_REST_WORK_TIME").equals(timeModel.getParamCode())) {
						restWorkTime = Integer.parseInt(timeModel.getParamVal());
					} else if (("ER_SMALL_WORK_TIME").equals(timeModel.getParamCode())) {
						smallWorkTime = Integer.parseInt(timeModel.getParamVal());
					} else if (("ER_DOWN_WORK_TIME").equals(timeModel.getParamCode())) {
						downWorkTime = Integer.parseInt(timeModel.getParamVal());
					} else if (("ER_BIG_WORK_TIME").equals(timeModel.getParamCode())) {
						bigWorkTime = Integer.parseInt(timeModel.getParamVal());
					}
				}
				/**
				 * 统计时间
				 */
				for (int k = 0; k < list.size(); k++) {
					/**
					 * 大物时间*大物托数+小物时间*小物托数
					 */
					EmptyReturnKbModel emptyReturnKbModel = list.get(k);
					if (("").equals(emptyReturnKbModel.getPalletIron()) || null == emptyReturnKbModel.getPalletIron()
							|| ("").equals(emptyReturnKbModel.getPalletIron())
							|| null == emptyReturnKbModel.getPalletIron()) {
						emptyReturnKbModel.setWorkTime("");
					} else {
						int palletIron = Integer.parseInt(emptyReturnKbModel.getPalletIron());
						int palletBox = Integer.parseInt(emptyReturnKbModel.getPalletBox());
						Integer workTime = bigWorkTime * palletIron + smallWorkTime * palletBox;
						totalTime += workTime;
					}
				}
				/**
				 * 显示数据
				 */
				for (int h = 0; h < list.size(); h++) {
					EmptyReturnKbModel emptyReturnKbModel = list.get(h);
					/**
					 * 取DCS单头的到达时间
					 */

					if (!("").equals(arriveTime) && null != arriveTime) {
						/**
						 * 转化成时间格式
						 */
						SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						DateFormat dateFormat = DateFormat.getDateInstance();
						dateFormat = new SimpleDateFormat("HH:mm:ss");
						/**
						 * 转化时间为数字
						 */
						long tolTimeNum = totalTime * 1000;
						long downTimeNum = downWorkTime * 1000;
						long restTimeNum = restWorkTime * 1000;
						long nowTimeNum = new Date().getTime();
						/**
						 * 到达时间
						 */
						long arrTimeNum = sDateFormat.parse(arriveTime).getTime();
						Date arrTime = new Date(arrTimeNum);
						String arrTimeDate = dateFormat.format(arrTime);
						/**
						 * 装箱开始时间
						 */
						long startTimeNum = arrTimeNum + downTimeNum;
						Date startTime = new Date(startTimeNum);
						String startTimeDate = dateFormat.format(startTime);
						
						/**
						 * 装箱结束时间
						 */
						long endTimeNum = startTimeNum + tolTimeNum;
						Date endTime = new Date(endTimeNum);
						String endTimeDate = dateFormat.format(endTime);
						
						/**
						 * 判断是否在作业时间
						 */
						if(nowTimeNum>startTimeNum && nowTimeNum<endTimeNum ) {
							emptyReturnKbModel.setWorkStatus("1");
						}else if(nowTimeNum>endTimeNum) {
							emptyReturnKbModel.setWorkStatus("2");
						}else {
							emptyReturnKbModel.setWorkStatus("0");
						}
						/**
						 * 出发时间
						 */
						long goTimeNum = endTimeNum + restTimeNum;
						Date goTime = new Date(goTimeNum);
						String goTimeDate = dateFormat.format(goTime);

						String finalTime = startTimeDate + "-" + endTimeDate;
						emptyReturnKbModel.setWorkTime(finalTime);
						/**
						 * 修改单头的出发和到达时间
						 */
						emptyReturnKbModel.setActualArriveTimeStr(arrTimeDate);
						emptyReturnKbModel.setPlanStartTimeStr(goTimeDate);
					}

					/**
					 * 判断list的长度是否大于8
					 */
					if (list.size() < 8) {
						EmptyReturnKbModel emptyModel = new EmptyReturnKbModel();
						list.add(emptyModel);
					}
					showList.add(emptyReturnKbModel);
				}

			}
			return showList;
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

}
