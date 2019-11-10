package com.hanthink.mon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonColScreenManager;
import com.hanthink.mon.model.MonColScreenModel;
import com.hotent.base.core.web.GenericController;

@Controller
@RequestMapping("/mon/colScreen")
public class MonColScreenController extends GenericController{
	
	@Resource
	private MonColScreenManager colScreenManager;
	
	@RequestMapping("/getShowMessageMap")
	public @ResponseBody Map<String, List<MonColScreenModel>> getShowMessageMap(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, List<MonColScreenModel>> resMap = colScreenManager.getShowMessageMap();
		return resMap;
	}
}
