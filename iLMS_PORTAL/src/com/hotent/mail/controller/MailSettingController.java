package com.hotent.mail.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：外部邮件用户设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:52:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mail/mail/mailSetting")
public class MailSettingController extends GenericController{
	@Resource
	MailSettingManager mailSettingManager;
	
	/**
	 * 外部邮件用户设置列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("USER_ID_", ContextUtil.getCurrentUserId(), QueryOP.EQUAL);
		PageList<MailSetting> mailSettingList=(PageList<MailSetting>)mailSettingManager.query(queryFilter);
		return new PageJson(mailSettingList);
	}
	
	
	
	/**
	 * 外部邮件用户设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody MailSetting getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new MailSetting();
		}
		MailSetting mailSetting=mailSettingManager.get(id);
		if(BeanUtils.isNotEmpty(mailSetting)){
			mailSetting.setPassword("");
		}
		return mailSetting;
	}
	
	/**
	 * 保存外部邮件用户设置信息
	 * @param request
	 * @param response
	 * @param mailSetting
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MailSetting mailSetting) throws Exception{
		String resultMsg=null;
		String id= mailSetting.getId();
		if(mailSettingManager.isExistMail(id,mailSetting)==true){
			resultMsg="该邮箱地址已经设置过，不能重复设置";
		}else{
			if(StringUtil.isEmpty(id)){
				mailSetting.setId(UniqueIdUtil.getSuid());
				String userId=ContextUtil.getCurrentUserId();
				mailSetting.setUserId(userId);
				//设置密码加密
				mailSetting.setPassword(EncryptUtil.encrypt(mailSetting.getPassword()));
				int count=mailSettingManager.getCountByUserId(userId);
				if(count==0){
					mailSetting.setIsDefault((short)1);
				}else{
					mailSetting.setIsDefault((short)0);
				}
				mailSettingManager.create(mailSetting);
				resultMsg="添加邮箱设置成功";
			}else{
				//设置密码加密
				String mailPass = mailSetting.getPassword();
				if(StringUtil.isEmpty(mailPass)){
					mailSetting.setPassword(mailSettingManager.get(id).getPassword());
				}else {
					mailSetting.setPassword(EncryptUtil.encrypt(mailPass));
				}
				mailSettingManager.update(mailSetting);
				resultMsg="更新邮箱设置成功";
			}
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
	}
	
	/**
	 * 批量删除外部邮件用户设置记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			mailSettingManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS,"删除外部邮件用户设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除外部邮件用户设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 测试接收/发送 服务器连接情况
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("test")
	public void test(HttpServletRequest request,HttpServletResponse response,MailSetting mailSetting)throws Exception
	{
		String id=RequestUtil.getString(request, "id");
		String settingId = mailSetting.getId();
		String password = mailSetting.getPassword();
		int isEdit=RequestUtil.getInt(request, "isEdit", 0);
		ResultMessage resultMessage=null;
		if(isEdit==0){
			mailSetting= mailSettingManager.get(id);
		} else if(StringUtil.isNotEmpty(password)){// 如果密码不为空，则使用密码进行连接测试
			mailSetting.setPassword(EncryptUtil.encrypt(password));
		} else if(StringUtil.isNotZeroEmpty(settingId)){
			mailSetting= mailSettingManager.get(settingId);
		}
		try{
			mailSettingManager.testConnection(mailSetting);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS,"测试通过!"));
		}catch (Exception e) {
			String str = ThreadMsgUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.FAIL,"测试通过失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
//				String message = ExceptionUtil.getExceptionMessage(e);
				String message = "测试连接失败，请检查邮箱设置是否正确！";
				resultMessage = new ResultMessage(ResultMessage.FAIL, message);
				response.getWriter().print(resultMessage);
			}
		} 
	}
	
	/**
	 * 更改默认邮箱设置;
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	public void setupDefault(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		String id=RequestUtil.getString(request, "id");
		MailSetting mailSetting=mailSettingManager.get(id);
		mailSetting.setIsDefault((short)1);
		try{
			mailSettingManager.setDefault(mailSetting);
			message=new ResultMessage(ResultMessage.SUCCESS, "设置成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.FAIL, "设置失败:" + ex.getMessage());
		}
		response.getWriter().print(message);
		response.sendRedirect(preUrl);
	}
	
}
