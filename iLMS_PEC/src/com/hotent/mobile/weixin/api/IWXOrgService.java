package com.hotent.mobile.weixin.api;

import java.util.List;

import com.hotent.org.persistence.model.Org;

public interface IWXOrgService {
	/**
	 * 新增组织
	 * @param org
	 */
	public void create(Org org);
	/**
	 * 更新组织
	 * @param org
	 */
	public void update(Org org);
	
	/**
	 * 删除组织
	 * @param orgId 用户账户
	 */
	public void delete(String orgId);
	/**
	 * 批量删除
	 * @param orgIds
	 */
	public void deleteAll(String orgIds);
	/**
	 * 批量添加组织
	 * @param sysOrgList
	 */
	void addAll(List<Org> orgList);
	
	public String getDepartmentUser(String orgCode);

	/**
	 * 微信组织机构同步
	 */
	void syncAllOrg();
}
