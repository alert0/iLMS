package com.hanthink.pub.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubSysParamManager;
import com.hanthink.pub.model.PubSysParamModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 *  
 * 描述：系统参数维护 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubSysParam")
public class PubSysParamController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(PubSysParamController.class);

	@Resource
	PubSysParamManager pubSysParamManager;

	/**
	 * 分页查询系统参数维护
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			PubSysParamModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setUserId(ContextUtil.getCurrentUser().getUserId());
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<PubSysParamModel> pageList = (PageList<PubSysParamModel>) pubSysParamManager
					.queryPubSysParamForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存系统参数维护信息 @param request @param response @param PubSysParam @throws
	 * Exception void @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PubSysParamModel pubSysParamModel) throws Exception {
		String resultMsg = null;
		String id = pubSysParamModel.getId();
		String factoryCode = pubSysParamModel.getFactoryCode();
		try {
			if (StringUtil.isEmpty(id)) {
				if (null == factoryCode || ("").equals(factoryCode)) {
					pubSysParamModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				}
				/**
				 * 判断是否主键冲突
				 */
				Integer count = pubSysParamManager.selectPrimaryKey(pubSysParamModel);
				if (count > 0) {
					resultMsg = "该数据已存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				String paramVal = pubSysParamModel.getParamVal();
				String checkBy = pubSysParamModel.getCheckBy();

				if (("SQL").equals(checkBy)) {
					if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
						resultMsg = "校验表达式不能为空";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String checkComp = pubSysParamModel.getCheckComp();
					if (checkComp.indexOf("?") == -1) {
						resultMsg = "校验表达式中至少包含一个？";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String param = "'" + paramVal + "'";
					String sql = checkComp.replaceAll("[?]", param);
					/**
					 * 验证SQL
					 */
					try {
						List<LinkedHashMap<String, Object>> addList = pubSysParamManager.sheckParamVal(sql);
						if (addList.size() == 0) {
							if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
								resultMsg = "该参数值错误";
							} else {
								resultMsg = pubSysParamModel.getMessage();
							}
							writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
							return;
						}else if (addList.size() == 1 && (addList.get(0).toString().indexOf("COUNT") != -1 || addList.get(0).toString().indexOf("count") != -1)) {
							Integer addReturnNum = pubSysParamManager.sheckParamValReturn(sql);
							if (addReturnNum == 0) {
								if (null == pubSysParamModel.getMessage()
										|| ("").equals(pubSysParamModel.getMessage())) {
									resultMsg = "该参数值错误";
								} else {
									resultMsg = pubSysParamModel.getMessage();
								}
								writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
								return;
							}
						}
					} catch (Exception e) {
						if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
							resultMsg = "该参数值错误";
						} else {
							resultMsg = pubSysParamModel.getMessage();
						}
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					pubSysParamModel.setCheckComp(checkComp);
				}
				/**
				 * 若为正则表达式
				 */
				if (("ZZ").equals(checkBy)) {
					if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
						resultMsg = "校验表达式不能为空";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String checkComp = pubSysParamModel.getCheckComp();
					boolean isMatch = Pattern.matches(checkComp, paramVal);
					if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
						resultMsg = "该参数值错误";
					} else {
						resultMsg = pubSysParamModel.getMessage();
					}
					/**
					 * 如果校验不通过返回错误信息
					 */
					if (!isMatch) {
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
				}
				pubSysParamModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
				pubSysParamManager.create(pubSysParamModel);
				resultMsg = "添加系统参数维护成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			} else {
				String paramVal = pubSysParamModel.getParamVal();
				String checkBy = pubSysParamModel.getCheckBy();
				/**
				 * 若为SQL
				 */
				if (("SQL").equals(checkBy)) {
					if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
						resultMsg = "校验表达式不能为空";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String checkComp = pubSysParamModel.getCheckComp();
					if (checkComp.indexOf("?") == -1) {
						resultMsg = "校验表达式中至少包含一个？";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String param = "'" + paramVal + "'";
					String sql = checkComp.replaceAll("[?]", param);
					/**
					 * 验证SQL
					 */
					try {
						List<LinkedHashMap<String, Object>> editList = pubSysParamManager.sheckParamVal(sql);
						if (editList.size() == 0) {
							if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
								resultMsg = "该参数值错误";
							} else {
								resultMsg = pubSysParamModel.getMessage();
							}
							writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
							return;
						} else if (editList.size() > 0 && (sql.indexOf("COUNT") != -1 || sql.indexOf("count") != -1)) {
							Integer editReturnNum = pubSysParamManager.sheckParamValReturn(sql);
							if (editReturnNum == 0) {
								if (null == pubSysParamModel.getMessage()
										|| ("").equals(pubSysParamModel.getMessage())) {
									resultMsg = "该参数值错误";
								} else {
									resultMsg = pubSysParamModel.getMessage();
								}
								writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
								return;
							}
						}
					} catch (Exception e) {
						if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
							resultMsg = "该参数值错误";
						} else {
							resultMsg = pubSysParamModel.getMessage();
						}
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					pubSysParamModel.setCheckComp(checkComp);
				}
				/**
				 * 若为正则表达式
				 */
				if (("ZZ").equals(checkBy)) {
					if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
						resultMsg = "校验表达式不能为空";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					String checkComp = pubSysParamModel.getCheckComp();
					boolean isMatch = Pattern.matches(checkComp, paramVal);
					if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
						resultMsg = "该参数值错误";
					} else {
						resultMsg = pubSysParamModel.getMessage();
					}
					/**
					 * 如果校验不通过返回错误信息
					 */
					if (!isMatch) {
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
				}
				pubSysParamModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				pubSysParamModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
				pubSysParamManager.updateAndLog(pubSysParamModel, RequestUtil.getIpAddr(request));
				resultMsg = "更新系统参数维护成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}

		} catch (Exception e) {
			log.error(e.toString());
			if (StringUtil.isEmpty(id)) {
				resultMsg = "新增失败";
			} else {
				resultMsg = "修改失败";
			}
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除系统参数维护记录 @param request @param response @throws Exception void @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			pubSysParamManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除系统参数维护成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除系统参数维护失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 用户修改系统参数维护信息 @param request @param response @param PubSysParam @throws
	 * Exception void @exception
	 */
	@RequestMapping("userUpdateObj")
	public void userUpdateObj(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PubSysParamModel pubSysParamModel) throws Exception {
		String resultMsg = null;
		try {
			String paramVal = pubSysParamModel.getParamVal();
			String checkBy = pubSysParamModel.getCheckBy();
			/**
			 * 若为SQL
			 */
			if (("SQL").equals(checkBy)) {
				if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
					resultMsg = "校验表达式不能为空";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				String checkComp = pubSysParamModel.getCheckComp();
				if (checkComp.indexOf("?") == -1) {
					resultMsg = "校验表达式中至少包含一个？";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				String param = "'" + paramVal + "'";
				String sql = checkComp.replaceAll("[?]", param);
				/**
				 * 验证SQL
				 */
				try {
					List<LinkedHashMap<String, Object>> editList = pubSysParamManager.sheckParamVal(sql);
					if (editList.size() == 0) {
						if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
							resultMsg = "该参数值错误";
						} else {
							resultMsg = pubSysParamModel.getMessage();
						}
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}else if (editList.size() == 1 && (editList.get(0).toString().indexOf("COUNT") != -1 || editList.get(0).toString().indexOf("count") != -1)) {
						Integer editReturnNum = pubSysParamManager.sheckParamValReturn(sql);
						if (editReturnNum == 0) {
							if (null == pubSysParamModel.getMessage()
									|| ("").equals(pubSysParamModel.getMessage())) {
								resultMsg = "该参数值错误";
							} else {
								resultMsg = pubSysParamModel.getMessage();
							}
							writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
							return;
						}
					}
				} catch (Exception e) {
					if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
						resultMsg = "该参数值错误";
					} else {
						resultMsg = pubSysParamModel.getMessage();
					}
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}

			}
			/**
			 * 若为正则表达式
			 */
			if (("ZZ").equals(checkBy)) {
				if (null == pubSysParamModel.getCheckComp() || ("").equals(pubSysParamModel.getCheckComp())) {
					resultMsg = "校验表达式不能为空";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				String checkComp = pubSysParamModel.getCheckComp();
				boolean isMatch = Pattern.matches(checkComp, paramVal);
				if (null == pubSysParamModel.getMessage() || ("").equals(pubSysParamModel.getMessage())) {
					resultMsg = "该参数值错误";
				} else {
					resultMsg = pubSysParamModel.getMessage();
				}
				/**
				 * 如果校验不通过返回错误信息
				 */
				if (!isMatch) {
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
			}
			pubSysParamModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
			pubSysParamModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
			pubSysParamManager.userUpdateAndLog(pubSysParamModel, RequestUtil.getIpAddr(request));
			resultMsg = "更新系统参数维护成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "修改失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

}
