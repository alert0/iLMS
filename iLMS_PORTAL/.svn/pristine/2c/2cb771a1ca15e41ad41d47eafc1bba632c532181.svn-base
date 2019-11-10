package com.hanthink.pkg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pkg.dao.PkgBoxDao;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: PkgBoxDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/
@Repository
public class PkgBoxDaoImpl extends MyBatisDaoImpl<String, PkgBoxModel> implements PkgBoxDao{

	@Override
	public String getNamespace() {
		
		return PkgBoxModel.class.getName();
	}

	/**
	 * 
	* @Title: queryPkgBoxForPage 
	* @Description: 分页查询包装管理箱种信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<DpmItemModel> 
	* @throws
	 */
	@Override
	public PageList<PkgBoxModel> queryPkgBoxForPage(PkgBoxModel model, DefaultPage p) {
		
		return (PageList<PkgBoxModel>) this.getByKey("queryPkgBoxForPage", model, p);
	}

	/**
	 * 
	* @Title: getByCode 
	* @Description: 新增时判断箱种代码是否重复 
	* @param @param boxCode
	* @param @return    
	* @return PkgBoxModel 
	* @throws
	 */
	@Override
	public PkgBoxModel getByCode(PkgBoxModel pkgBoxModel) {

		return this.getUnique("getByCode", pkgBoxModel);
	}

	/**
	 * 加载可使用的箱CODE信息
	 * @param pkgBox
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午12:45:30
	 */
	@Override
	public List<PkgBoxModel> loadBoxCodeData(PkgBoxModel pkgBox) {
		return this.getByKey("loadBoxCodeData", pkgBox);
	}

}
