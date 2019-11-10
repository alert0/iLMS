package com.hanthink.mon.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonAbnormalTrackManager;
import com.hanthink.mon.model.MonAbnormalTrackModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.sys.util.ContextUtil;

@Controller
@RequestMapping("/mon/monAbnormalTrack")
public class MonAbnormalTrackController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(MonAbnormalTrackController.class);

	@Resource
	private MonAbnormalTrackManager monAbnormalTrackManager;

	/**
	 * 异常跟踪看板
	 * <p>return: List<MonAbnormalTrackModel></p>  
	 * <p>Description: MonAbnormalTrackController.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月10日
	 * @version 1.0
	 */
	@RequestMapping("/queryAbnormalForPage")
	public @ResponseBody List<MonAbnormalTrackModel> queryAbnormalForPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String workCenter = RequestUtil.getString(request, "workCenter");
		MonAbnormalTrackModel model = new MonAbnormalTrackModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setWorkCenter(workCenter);
		try {
			List<MonAbnormalTrackModel> list = (List<MonAbnormalTrackModel>) monAbnormalTrackManager
					.queryAbnormalForPage(model);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

}
