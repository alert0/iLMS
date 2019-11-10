package com.hotent.mail.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.Mail;

/**
 * 
 * <pre> 
 * 描述：外部邮件 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:49:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailDao extends Dao<String, Mail> {
	
	/**
	 * 发件人列表
	 * @param senderAddress
	 * @return
	 */
	public List<Mail> getListBySender(String senderAddress);

	/**
	 * 获取分类邮件（收件箱，发件箱，草稿箱，垃圾箱)
	 * @param queryFilter
	 * @param types
	 * @return
	 */
	public List<Mail> getFolderList(QueryFilter queryFilter);
	

	/**
	 * 更新邮箱分类类型
	 * @param mailId
	 * @param types
	 * @return
	 */
	public int updateTypes(String mailId,int types);
	
	/**
	 * 根据邮箱和邮箱类型得到邮件数量
	 * @param address
	 * @param type
	 * @return
	 */
	public int getFolderCount(String id, int type);
	
	/**
	 *  获得用户默认邮箱的邮件列表
	 * @param queryFilter
	 * @return
	 */
	public List<Mail> getDefaultMailList(QueryFilter queryFilter);	
	
	/**
	 * 判断是否存在当前邮件uid
	 * @param uid
	 * @return
	 */
	public boolean getByEmailId(String uid,String setId);
	
	/**
	 * 根据邮件uid删除本地邮件
	 * @param uid
	 */
	public void delByEmailid(String uid);
	
	/**
	 * 根据用户ID得到最新已发的邮件
	 * @param userId
	 */
	public List<Mail> getMailByUserId(String userId,Page pb);
	
	/**
	 * 根据setId获取所有的UID
	 * @param setId
	 * @return
	 */
	public List<String> getUIDBySetId(String setId);

	public void delBySetId(String setId);	
	
	/**
	 * 
	 * 根据发送次数获取收件人邮件名称集
	 * @return 
	 * List<Mail>
	 */
	public List<Mail> getReceiveraddesses(String userId);
	
	
}
