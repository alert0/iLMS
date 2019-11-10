package com.hanthink.mon.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonPlateFormSchduleManager;
import com.hanthink.mon.model.MonPlateFormModel;
import com.hanthink.mon.model.MonPlateFormSchduleModel;
import com.hanthink.mon.model.MonRouteModel;
import com.hotent.base.core.web.GenericController;

@Controller
@RequestMapping("/mon/plateFormSchdule")
public class MonPlateFormSchduleController extends GenericController {

	@Resource
	private MonPlateFormSchduleManager manager;

	private static Logger log = LoggerFactory
			.getLogger(MonPlateFormSchduleController.class);

	public static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 查询站台调度看板数据
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDataForView")
	private @ResponseBody
	MonPlateFormSchduleModel queryDataForView(HttpServletRequest request,
			HttpServletResponse reponse,
			@ModelAttribute("model") MonPlateFormSchduleModel model)
			throws Exception {
		log.debug("查询站台调度看板数据开始");
		if (model.getLogisticsModel() == null
				|| "".equals(model.getLogisticsModel())
				|| model.getWorkcenter() == null
				|| "".equals(model.getWorkcenter())) {
			return null;
		}
		// 返回当前时间
		MonPlateFormSchduleModel returnModel = manager.queryCurrentTime();
		returnModel.setLogisticsModel(model.getLogisticsModel());
		returnModel.setWorkcenter(model.getWorkcenter());
		// 查询工作时间
		MonPlateFormSchduleModel tempModel = manager.queryWorkTime(returnModel);
		returnModel.setInTransTime(tempModel.getInTransTime());
		returnModel.setInEmptyTime(tempModel.getInEmptyTime());
		returnModel.setInUnloadTime(tempModel.getInUnloadTime());
		returnModel.setInUnloadBigTime(tempModel.getInUnloadBigTime());
		returnModel.setInUnloadSmallTime(tempModel.getInUnloadSmallTime());

		Integer inTransTime = Integer.parseInt(tempModel.getInTransTime());
		Integer inEmptyTime = Integer.parseInt(tempModel.getInEmptyTime());
		Integer inUnloadTime = Integer.parseInt(tempModel.getInUnloadTime());
		
		// 定义卸货站台信息
		List<MonPlateFormModel> downPf = manager
				.queryDownPlateFormData(returnModel);

		// 定义返空站台信息
		List<MonPlateFormModel> upPf = manager
				.queryUpPlateFormData(returnModel);

		// 查询路线数据
		List<MonRouteModel> unloadRouteModels = new ArrayList<>();

		List<MonPlateFormModel> downPlateForms = new ArrayList<>();
		List<MonPlateFormModel> upPlateForms = new ArrayList<>();
		if (returnModel.getLogisticsModel() != null
				&& "SW".equals(returnModel.getLogisticsModel())) {
			// 如果为循环取货
			unloadRouteModels = manager.querySWMonRouteData(returnModel);
		} else if (returnModel.getLogisticsModel() != null
				&& "JIT".equals(returnModel.getLogisticsModel())) {
			// 如果为拉动
			unloadRouteModels = manager.queryJITMonRouteData(returnModel);
		}

		// 复制一个路线队列数据,用户返空的数据使用
		List<MonRouteModel> emptyRouteModels = new ArrayList<>();
		// 直接赋值会出现引用的情况,前面清除了unloadRouteModels的数据会导致emptyRouteModels数据清除
		// for (int i = 0; i < unloadRouteModels.size(); i++) {
		// emptyRouteModels.add(unloadRouteModels.get(i));
		// }

		// 循环每个卸货站台信息
		for (int i = 0; i < downPf.size(); i++) {
			MonPlateFormModel m = new MonPlateFormModel();
			m.setPlateForm(downPf.get(i).getPlateForm());
			if (downPf.get(i).getPlateForm() != null
					&& !"".equals(downPf.get(i).getPlateForm())) {
				downPlateForms.add(m);
			}
		}
		// 循环每个空箱返还站台信息
		for (int i = 0; i < upPf.size(); i++) {
			MonPlateFormModel m = new MonPlateFormModel();
			m.setPlateForm(upPf.get(i).getPlateForm());
			if (upPf.get(i).getPlateForm() != null
					&& !"".equals(upPf.get(i).getPlateForm())) {
				upPlateForms.add(m);
			}
		}

		// 循环所有的路线信息数据
		if (unloadRouteModels != null && unloadRouteModels.size() > 0) {
			for (int i = 0; i < unloadRouteModels.size(); i++) {
				MonRouteModel route = unloadRouteModels.get(i);
				if (route.getPlateForm() != null
						&& !"".equals(route.getPlateForm())) {
					// 在当前站台列表中找是否存在这个特殊站台,并且这段时间没有被占用计数器
					int e = 0;
					// 如果返空站台不为空,特殊处理
					// 里层循环卸货站台信息数据
					for (int j = 0; j < downPlateForms.size(); j++) {
						MonPlateFormModel plateFormModel = downPlateForms
								.get(j);
						// 先找到这个特殊的站台
						if (plateFormModel.getPlateForm() == null
								&& "".equals(plateFormModel.getPlateForm())
								&& !plateFormModel.getPlateForm().equals(
										route.getPlateForm())) {
							e++;
							// 站台信息不匹配
							continue;
						}

						// 获取这个站台的Routes
						List<MonRouteModel> routes = plateFormModel.getRoutes();
						if (routes != null && routes.size() > 0) {
							// 重叠的个数
							int notOverLapCount = 0;
							// 获取这个站台的最后一个
							for (int m = 0; m < routes.size(); m++) {
								MonRouteModel tempRoute = routes.get(m);
								// 判断当前是否有重叠
								boolean b = isOverlap(route.getDownStartTime(),
										route.getDownEndTime(),
										tempRoute.getDownStartTime(),
										tempRoute.getDownEndTime());

								if (!b) {
									// 如果有重叠
									notOverLapCount++;
									break;
								}
							}
							// 都没有重叠,将该路线信息加入到站台路线队列里面
							if (notOverLapCount <= 0) {
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.add(route);
								unloadRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								// 已经找到跳出内层循环
								break;
							} else {
								e++;
							}

						} else {
							// 之前是空的
							routes = new ArrayList<>();
							routes.add(route);
							// 把这个model放入到空箱返还的Model中
							emptyRouteModels.add(route);
							unloadRouteModels.remove(i);
							plateFormModel.setRoutes(routes);
							i--;
							// 已经找到跳出内层循环
							break;
						}
					}
					// 如果e < 所有站台信息的数量,表明已经添加进去,否则没有添加进去
					// e = downPlateForms.size()表明未添加
					if (e == downPlateForms.size()) {
						// 记录这些返空站台在这段时间是否空闲
						int f = 0;
						// 记录最小的结束时间
						String firstEndTime = null;
						String plateForm = null;
						// 再次循环downPlateForms
						for (int k = 0; k < downPlateForms.size(); k++) {
							MonPlateFormModel plateFormModel = downPlateForms
									.get(k);
							List<MonRouteModel> routes = plateFormModel
									.getRoutes();
							if (routes != null && routes.size() > 0) {
								// 重叠的个数
								int notOverLapCount = 0;
								// 获取这个站台的最后一个
								for (int m = 0; m < routes.size(); m++) {
									MonRouteModel tempRoute = routes.get(m);
									// 判断当前是否有重叠
									boolean b = isOverlap(
											route.getDownStartTime(),
											route.getDownEndTime(),
											tempRoute.getDownStartTime(),
											tempRoute.getDownEndTime());

									if (!b) {
										// 如果有重叠
										notOverLapCount++;
										break;
									}
								}
								// 获取这个站台的最后一个
								MonRouteModel lastRoute = routes.get(routes
										.size() - 1);
								// 第一次进循环
								if (k == 0) {
									firstEndTime = lastRoute.getDownEndTime();
									plateForm = plateFormModel.getPlateForm();
								} else {
									// 判断firstEndTime是否大于lastRoute.getDownEndTime()
									boolean bo = isLess(firstEndTime,
											lastRoute.getDownEndTime());
									if (bo) {
										// 如果为true,替换
										firstEndTime = lastRoute
												.getDownEndTime();
										plateForm = plateFormModel
												.getPlateForm();
									}

								}
								// // 判断当前是否有重叠
								// b = isOverlap(route.getDownStartTime(),
								// route.getDownEndTime(),
								// lastRoute.getDownStartTime(),
								// lastRoute.getDownEndTime());
								// 都没有重叠,将该路线信息加入到站台路线队列里面
								if (notOverLapCount <= 0) {
									routes.add(route);
									// 把这个model放入到空箱返还的Model中
									emptyRouteModels.add(route);
									unloadRouteModels.remove(i);
									plateFormModel.setRoutes(routes);
									i--;
									break;
								} else {
									// 有重叠,需要进入下一个站台判断
									f++;
									continue;
								}

							} else {
								routes = new ArrayList<>();
								// 之前是空的
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.add(route);
								unloadRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								break;
							}
						}

						// 再次循环downPlateForms
						if (f == downPlateForms.size()) {
							for (int l = 0; l < downPlateForms.size(); l++) {
								MonPlateFormModel plateFormModel = downPlateForms
										.get(l);
								// 找到最早结束时间的plateForm
								if (!plateForm.equals(plateFormModel
										.getPlateForm())) {
									continue;
								} else {
									List<MonRouteModel> routes = plateFormModel
											.getRoutes();
									// 拿到该站台的最后一个路线信息
									MonRouteModel lastRouteModel = plateFormModel
											.getRoutes().get(
													plateFormModel.getRoutes()
															.size() - 1);
									Calendar c = new GregorianCalendar();
									Date downStartTime = format
											.parse(lastRouteModel
													.getDownEndTime());
									route.setDownStartTime(format
											.format(downStartTime));
									c.setTime(downStartTime);
									// 往后推卸货的时间
									c.add(Calendar.MINUTE, inUnloadTime);
									Date downEndTime = c.getTime();
									route.setDownEndTime(format
											.format(downEndTime));
									c.setTime(downEndTime);
									// 往后推转移时间
									c.add(Calendar.MINUTE, inTransTime);
									Date upStartTime = c.getTime();
									route.setUpStartTime(format
											.format(upStartTime));
									c.setTime(upStartTime);
									// 往后推装空箱时间
									c.add(Calendar.MINUTE, inEmptyTime);
									Date upEndTime = c.getTime();
									route.setUpEndTime(format.format(upEndTime));
									// 设置为推迟的状态
									route.setFlag("1");
									routes.add(route);
									// 把这个model放入到空箱返还的Model中
									emptyRouteModels.add(route);
									unloadRouteModels.remove(i);
									plateFormModel.setRoutes(routes);
									i--;
									break;
								}
							}
						}
					}
				} else {
					// 定义当前站台中是否能容纳这个路线代码,判断每个站台下面最大路线对应的时间和该route的时间是否有交集
					int e = 0;
					// 特殊返空站台为空的情况,循环站台信息
					for (int j = 0; j < downPlateForms.size(); j++) {
						MonPlateFormModel plateFormModel = downPlateForms
								.get(j);
						// 获取这个站台的Routes
						List<MonRouteModel> routes = plateFormModel.getRoutes();
						if (routes != null && routes.size() > 0) {
							// 重叠的个数
							int notOverLapCount = 0;
							// 获取这个站台的最后一个
							for (int m = 0; m < routes.size(); m++) {
								MonRouteModel tempRoute = routes.get(m);
								// 判断当前是否有重叠
								boolean b = isOverlap(route.getDownStartTime(),
										route.getDownEndTime(),
										tempRoute.getDownStartTime(),
										tempRoute.getDownEndTime());

								if (!b) {
									// 如果有重叠
									notOverLapCount++;
									break;
								}
							}
							// // 判断当前是否有重叠
							// b = isOverlap(route.getDownStartTime(),
							// route.getDownEndTime(),
							// lastRoute.getDownStartTime(),
							// lastRoute.getDownEndTime());
							// 都没有重叠,将该路线信息加入到站台路线队列里面
							if (notOverLapCount <= 0) {
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.add(route);
								unloadRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								// 已经找到跳出内层循环
								break;
							} else {
								e++;
								continue;
							}

						} else {
							// 之前是空的
							routes = new ArrayList<>();
							routes.add(route);
							// 把这个model放入到空箱返还的Model中
							emptyRouteModels.add(route);
							unloadRouteModels.remove(i);
							plateFormModel.setRoutes(routes);
							i--;
							// 已经找到跳出内层循环
							break;
						}
					}

					// 如果e < 所有站台信息的数量,表明已经添加进去,否则没有添加进去
					// e = downPlateForms.size()表明未添加
					if (e == downPlateForms.size()) {
						// 记录这些返空站台在这段时间是否空闲
						int f = 0;
						// 记录最小的结束时间
						String firstEndTime = null;
						String plateForm = null;
						// 再次循环downPlateForms
						for (int k = 0; k < downPlateForms.size(); k++) {
							MonPlateFormModel plateFormModel = downPlateForms
									.get(k);
							List<MonRouteModel> routes = plateFormModel
									.getRoutes();
							if (routes != null && routes.size() > 0) {
								// 重叠的个数
								int notOverLapCount = 0;
								// 获取这个站台的最后一个
								for (int m = 0; m < routes.size(); m++) {
									MonRouteModel tempRoute = routes.get(m);
									// 判断当前是否有重叠
									boolean b = isOverlap(
											route.getDownStartTime(),
											route.getDownEndTime(),
											tempRoute.getDownStartTime(),
											tempRoute.getDownEndTime());

									if (!b) {
										// 如果有重叠
										notOverLapCount++;
										break;
									}
								}
								// 获取这个站台的最后一个
								MonRouteModel lastRoute = routes.get(routes
										.size() - 1);
								// 第一次进循环
								if (k == 0) {
									firstEndTime = lastRoute.getDownEndTime();
									plateForm = plateFormModel.getPlateForm();
								} else {
									// 判断firstEndTime是否大于lastRoute.getDownEndTime()
									boolean bo = isLess(firstEndTime,
											lastRoute.getDownEndTime());
									if (bo) {
										// 如果为true,替换
										firstEndTime = lastRoute
												.getDownEndTime();
										plateForm = plateFormModel
												.getPlateForm();
									}

								}
								// // 判断当前是否有重叠
								// b = isOverlap(route.getDownStartTime(),
								// route.getDownEndTime(),
								// lastRoute.getDownStartTime(),
								// lastRoute.getDownEndTime());
								// 都没有重叠,将该路线信息加入到站台路线队列里面
								if (notOverLapCount <= 0) {
									routes.add(route);
									// 把这个model放入到空箱返还的Model中
									emptyRouteModels.add(route);
									unloadRouteModels.remove(i);
									plateFormModel.setRoutes(routes);
									i--;
									break;
								} else {
									// 有重叠,需要进入下一个站台判断
									f++;
									continue;
								}

							} else {
								// 之前是空的
								routes = new ArrayList<>();
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.add(route);
								unloadRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								break;
							}
						}

						// 如果f ==
						// downPlateForms表明前面没有处理这条路线信息或者时间都被占用,所以需要往后推
						if (f == downPlateForms.size()) {
							// 再次循环downPlateForms
							for (int l = 0; l < downPlateForms.size(); l++) {
								MonPlateFormModel plateFormModel = downPlateForms
										.get(l);
								// 找到最早结束时间的plateForm
								if (!plateForm.equals(plateFormModel
										.getPlateForm())) {
									continue;
								} else {
									List<MonRouteModel> routes = plateFormModel
											.getRoutes();
									// 拿到该站台的最后一个路线信息
									MonRouteModel lastRouteModel = plateFormModel
											.getRoutes().get(
													plateFormModel.getRoutes()
															.size() - 1);
									Calendar c = new GregorianCalendar();
									Date downStartTime = format
											.parse(lastRouteModel
													.getDownEndTime());
									route.setDownStartTime(format
											.format(downStartTime));
									c.setTime(downStartTime);
									// 往后推卸货的时间
									c.add(Calendar.MINUTE, inUnloadTime);
									Date downEndTime = c.getTime();
									route.setDownEndTime(format
											.format(downEndTime));
									c.setTime(downEndTime);
									// 往后推转移时间
									c.add(Calendar.MINUTE, inTransTime);
									Date upStartTime = c.getTime();
									route.setUpStartTime(format
											.format(upStartTime));
									c.setTime(upStartTime);
									// 往后推装空箱时间
									c.add(Calendar.MINUTE, inEmptyTime);
									Date upEndTime = c.getTime();
									route.setUpEndTime(format.format(upEndTime));
									// 设置为推迟的状态
									route.setFlag("1");
									routes.add(route);
									// 把这个model放入到空箱返还的Model中
									emptyRouteModels.add(route);
									unloadRouteModels.remove(i);
									plateFormModel.setRoutes(routes);
									i--;
									break;
								}
							}
						}

					}
				}
			}
		}

		// 返空数据处理
		if (emptyRouteModels != null && emptyRouteModels.size() > 0) {
			for (int i = 0; i < emptyRouteModels.size(); i++) {
				MonRouteModel route = emptyRouteModels.get(i);
				// 定义当前站台中是否能容纳这个路线代码,判断每个站台下面最大路线对应的时间和该route的时间是否有交集
				int e = 0;
				// 循环返空站台信息
				for (int j = 0; j < upPlateForms.size(); j++) {
					MonPlateFormModel plateFormModel = upPlateForms.get(j);
					// 获取这个站台的Routes
					List<MonRouteModel> routes = plateFormModel.getRoutes();
					if (routes != null && routes.size() > 0) {
						// 重叠的个数
						int notOverLapCount = 0;
						// 获取这个站台的最后一个
						for (int m = 0; m < routes.size(); m++) {
							MonRouteModel tempRoute = routes.get(m);
							// 判断当前是否有重叠
							boolean b = isOverlap(route.getUpStartTime(),
									route.getUpEndTime(),
									tempRoute.getUpStartTime(),
									tempRoute.getUpEndTime());

							if (!b) {
								// 如果有重叠
								notOverLapCount++;
								break;
							}
						}
						// MonRouteModel lastRoute =
						// routes.get(routes.size() -
						// 1);
						// 判断当前是否有重叠
						// b = isOverlap(route.getUpStartTime(),
						// route.getUpEndTime(),
						// lastRoute.getUpStartTime(),
						// lastRoute.getUpEndTime());
						// 都没有重叠,将该路线信息加入到站台路线队列里面
						if (notOverLapCount <= 0) {
							routes.add(route);
							// 把这个model放入到空箱返还的Model中
							emptyRouteModels.remove(i);
							plateFormModel.setRoutes(routes);
							i--;
							// 已经找到跳出内层循环
							break;
						} else {
							e++;
						}

					} else {
						// 之前是空的
						routes = new ArrayList<>();
						routes.add(route);
						// 把这个model放入到空箱返还的Model中
						emptyRouteModels.remove(i);
						plateFormModel.setRoutes(routes);
						i--;
						// 已经找到跳出内层循环
						break;
					}
				}

				// 如果e < 所有站台信息的数量,表明已经添加进去,否则没有添加进去
				// e = upPlateForms.size()表明未添加
				if (e == upPlateForms.size()) {
					// 记录这些返空站台在这段时间是否空闲
					int f = 0;
					// 记录最小的结束时间
					String firstEndTime = null;
					String plateForm = null;
					// 再次循环downPlateForms
					for (int k = 0; k < upPlateForms.size(); k++) {
						MonPlateFormModel plateFormModel = upPlateForms.get(k);
						List<MonRouteModel> routes = plateFormModel.getRoutes();
						if (routes != null && routes.size() > 0) {
							// 重叠的个数
							int notOverLapCount = 0;
							// 获取这个站台的最后一个
							for (int m = 0; m < routes.size(); m++) {
								MonRouteModel tempRoute = routes.get(m);
								// 判断当前是否有重叠
								boolean b = isOverlap(route.getUpStartTime(),
										route.getUpEndTime(),
										tempRoute.getUpStartTime(),
										tempRoute.getUpEndTime());

								if (!b) {
									// 如果有重叠
									notOverLapCount++;
									break;
								}
							}
							// 获取这个站台的最后一个
							MonRouteModel lastRoute = routes
									.get(routes.size() - 1);
							// 第一次进循环
							if (k == 0) {
								firstEndTime = lastRoute.getUpEndTime();
								plateForm = plateFormModel.getPlateForm();
							} else {
								// 判断firstEndTime是否大于lastRoute.getDownEndTime()
								boolean bo = isLess(firstEndTime,
										lastRoute.getUpEndTime());
								if (bo) {
									// 如果为true,替换
									firstEndTime = lastRoute.getUpEndTime();
									plateForm = plateFormModel.getPlateForm();
								}

							}
							// 判断当前是否有重叠
							// b = isOverlap(route.getUpStartTime(),
							// route.getUpEndTime(),
							// lastRoute.getUpStartTime(),
							// lastRoute.getUpEndTime());
							// 都没有重叠,将该路线信息加入到站台路线队列里面
							if (notOverLapCount <= 0) {
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								break;
							} else {
								// 有重叠,需要进入下一个站台判断,f++然后判断f的值和站台的数量作比较来判断是否有空闲
								f++;
								continue;
							}

						} else {
							// 之前是空的
							routes = new ArrayList<>();
							routes.add(route);
							// 把这个model放入到空箱返还的Model中
							emptyRouteModels.remove(i);
							plateFormModel.setRoutes(routes);
							i--;
							break;
						}
					}

					if (f == upPlateForms.size()) {
						// 表明这条路线的返空时间在各个站台都已经被占用
						// 再次循环upPlateForms
						for (int l = 0; l < upPlateForms.size(); l++) {
							MonPlateFormModel plateFormModel = upPlateForms
									.get(l);
							// 找到最早结束时间的plateForm
							if (!plateForm
									.equals(plateFormModel.getPlateForm())) {
								continue;
							} else {
								List<MonRouteModel> routes = plateFormModel
										.getRoutes();
								// 拿到该站台的最后一个路线信息
								MonRouteModel lastRouteModel = plateFormModel
										.getRoutes().get(
												plateFormModel.getRoutes()
														.size() - 1);
								Calendar c = new GregorianCalendar();
								Date upStartTime = format.parse(lastRouteModel
										.getUpEndTime());
								route.setUpStartTime(format.format(upStartTime));
								c.setTime(upStartTime);
								// 往后推返空的时间
								c.add(Calendar.MINUTE, inEmptyTime);
								Date upEndTime = c.getTime();
								route.setUpEndTime(format.format(upEndTime));
								// 设置为推迟的状态
								route.setFlag("1");
								c.setTime(upEndTime);
								routes.add(route);
								// 把这个model放入到空箱返还的Model中
								emptyRouteModels.remove(i);
								plateFormModel.setRoutes(routes);
								i--;
								break;
							}
						}
					}
				}
			}
		}

		// 返空站台不为空的数据优先安排
		// for (int i = 0; i < downPlateForms.size(); i++) {
		// MonPlateFormModel tempPlateFormModel = downPlateForms.get(i);
		// List<MonRouteModel> routes = new ArrayList<>();
		// for (int j = 0; j < unloadRouteModels.size(); j++) {
		// MonRouteModel tempRouteModel = unloadRouteModels.get(j);
		// // 找到这些数据中返空站台不为空的数据
		// // 路线维护了返空站台,优先安排
		// if (tempRouteModel.getPlateForm() != null
		// && !"".equals(tempRouteModel.getPlateForm())
		// && tempPlateFormModel.getPlateForm().equals(
		// tempRouteModel.getPlateForm())) {
		// // 如果这条路线的站台不为空,并且站台相同,判断该数据和routes里面是否有重叠
		// if (routes.size() > 0) {
		// // 因为是按照到货时间排序过,所以可以通过最后一一条记录的数据来判断
		// // 如果routes有数据,取得最后一条记录,因为这条记录的截止时间肯定是最大的
		// MonRouteModel lastRouteModel = routes
		// .get(routes.size() - 1);
		// boolean b = isOverlap(
		// tempRouteModel.getDownStartTime(),
		// tempRouteModel.getDownEndTime(),
		// lastRouteModel.getDownStartTime(),
		// lastRouteModel.getDownEndTime());
		// if (b) {
		// routes.add(tempRouteModel);
		// unloadRouteModels.remove(j);
		// j--;
		// }
		// } else {
		// // 没有数据
		// // 直接添加
		// routes.add(tempRouteModel);
		// unloadRouteModels.remove(j);
		// j--;
		// }
		// }
		// }
		// tempPlateFormModel.setRoutes(routes);
		// }

		// 不能根据路线去循环,而是要根据所有的

		// 再次循环,处理返空站台为空的数据,剩下的数据都是返空站台为空的数据
		// for (int i = 0; i < downPlateForms.size(); i++) {
		// MonPlateFormModel tempPlateFormModel = downPlateForms.get(i);
		// // 获取已有的路线数据
		// List<MonRouteModel> routes = tempPlateFormModel.getRoutes();
		// for (int j = 0; j < unloadRouteModels.size(); j++) {
		// MonRouteModel tempRouteModel = unloadRouteModels.get(j);
		// if (routes.size() > 0) {
		// // 因为是按照到货时间排序过,所以可以通过最后一一条记录的数据来判断
		// // 如果routes有数据,取得最后一条记录,因为这条记录的截止时间肯定是最大的
		// MonRouteModel lastRouteModel = routes
		// .get(routes.size() - 1);
		// boolean b = isOverlap(tempRouteModel.getDownStartTime(),
		// tempRouteModel.getDownEndTime(),
		// lastRouteModel.getDownStartTime(),
		// lastRouteModel.getDownEndTime());
		// if (b) {
		// routes.add(tempRouteModel);
		// unloadRouteModels.remove(j);
		// j--;
		// }
		// } else {
		// // 没有数据
		// // 直接添加
		// routes.add(tempRouteModel);
		// unloadRouteModels.remove(j);
		// j--;
		// }
		// }
		// tempPlateFormModel.setRoutes(routes);
		// }

		// 返空站台数据处理
		// for (int i = 0; i < upPlateForms.size(); i++) {
		// MonPlateFormModel tempPlateFormModel = upPlateForms.get(i);
		// // 获取已有的路线数据
		// List<MonRouteModel> routes = new ArrayList<>();
		// for (int j = 0; j < emptyRouteModels.size(); j++) {
		// MonRouteModel tempRouteModel = emptyRouteModels.get(j);
		// if (routes.size() > 0) {
		// // 因为是按照到货时间排序过,所以可以通过最后一一条记录的数据来判断
		// // 如果routes有数据,取得最后一条记录,因为这条记录的截止时间肯定是最大的
		// MonRouteModel lastRouteModel = routes
		// .get(routes.size() - 1);
		// boolean b = isOverlap(tempRouteModel.getUpStartTime(),
		// tempRouteModel.getUpEndTime(),
		// lastRouteModel.getUpStartTime(),
		// lastRouteModel.getUpEndTime());
		// if (b) {
		// routes.add(tempRouteModel);
		// emptyRouteModels.remove(j);
		// j--;
		// }
		// } else {
		// // 没有数据
		// // 直接添加
		// routes.add(tempRouteModel);
		// emptyRouteModels.remove(j);
		// j--;
		// }
		// }
		// tempPlateFormModel.setRoutes(routes);
		// }
		// 设置所有站台信息
		returnModel.setUpPlateForms(upPlateForms);
		returnModel.setDownPlateForms(downPlateForms);
		log.debug("查询站台调度看板数据结束");
		return returnModel;
	}

	/**
	 * 判断两个时间是否有交叠
	 * 
	 * @param startdate1
	 * @param enddate1
	 * @param startdate2
	 * @param enddate2
	 * @return
	 */
	private static boolean isOverlap(String startdate1, String enddate1,
			String startdate2, String enddate2) {
		Date leftStartDate = null;
		Date leftEndDate = null;
		Date rightStartDate = null;
		Date rightEndDate = null;
		try {
			leftStartDate = format.parse(startdate1);
			leftEndDate = format.parse(enddate1);
			rightStartDate = format.parse(startdate2);
			rightEndDate = format.parse(enddate2);
		} catch (Exception e) {
			return false;
		}
		if (leftStartDate.getTime() >= rightEndDate.getTime()
				|| leftEndDate.getTime() <= rightStartDate.getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断enddate1 是否大于 enddate2
	 * 
	 * @param enddate1
	 * @param enddate2
	 * @return
	 */
	private static boolean isLess(String enddate1, String enddate2) {
		Date leftEndDate = null;
		Date rightEndDate = null;
		try {
			leftEndDate = format.parse(enddate1);
			rightEndDate = format.parse(enddate2);
		} catch (Exception e) {
			return false;
		}
		if (leftEndDate.getTime() >= rightEndDate.getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
