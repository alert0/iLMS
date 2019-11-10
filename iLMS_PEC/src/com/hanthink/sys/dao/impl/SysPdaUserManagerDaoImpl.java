package com.hanthink.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysPdaUserManagerDao;
import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：用户数据权限DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SysPdaUserManagerDaoImpl extends MyBatisDaoImpl<String, SysPdaUserManagerModel> implements SysPdaUserManagerDao{

    @Override
    public String getNamespace() {
        return SysPdaUserManagerModel.class.getName();
    }

    /**
     * 分页查询
     */
	@Override
	public PageList<SysPdaUserManagerModel> querySysPdaUserManagerForPage(SysPdaUserManagerModel model, DefaultPage p) {
		return (PageList<SysPdaUserManagerModel>) this.getByKey("querySysPdaUserManagerForPage", model, p);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void deleteByIds(String[] aryIds) {
		this.deleteByKey("deleteByIds", aryIds);
	}

	/**
	 * 避免主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKey", sysPdaUserManagerModel);
	}

	/**
	 * 新增数据
	 */
	@Override
	public void insert(SysPdaUserManagerModel sysPdaUserManagerModel) {
		this.insertByKey("insert", sysPdaUserManagerModel);
	}

	/**
	 * 删除用户关系
	 */
	@Override
	public void deletePdaMenuByIds(String[] aryIds) {
		this.deleteByKey("deletePdaMenuByIds", aryIds);
	}

	/**
	 * 更新打印状态
	 */
	@Override
	public void updatePrintStatus(SysPdaUserManagerModel model) {
		this.updateByKey("updatePrintStatus", model);
	}

	/**
	 * 打印用查询
	 */
	@Override
	public SysPdaUserManagerModel querySysPdaUserManagerLabel(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return this.getUnique("querySysPdaUserManagerLabel", sysPdaUserManagerModel);
	}

	/**
	 * 先查询系统参数表里面是否有图片信息
	 */
	@Override
	public Integer queryPicture(SysPdaUserManagerModel model) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryPicture",model);
	}

	/**
	 * 更新图片
	 */
	@Override
	public void updateImageId(SysPdaUserManagerModel model) {
		this.updateByKey("updateImageId", model);
	}

	/**
	 * 新增图片
	 */
	@Override
	public void insertImageId(SysPdaUserManagerModel model) {
		this.insertByKey("insertImageId", model);
	}

	/**
	 * 查询之前的图片ID
	 */
	@Override
	public List<SysPdaUserManagerModel> queryOlderPicture(SysPdaUserManagerModel model) {
		return (List<SysPdaUserManagerModel>) this.getList("queryOlderPicture", model);
	}

	/**
	 * 新增PEC表用户
	 */
	@Override
	public void insertPEC(SysPdaUserManagerModel sysPdaUserManagerModel) {
		this.insertByKey("insertPEC", sysPdaUserManagerModel);
	}

	/**
	 * 更新PEC表用户
	 */
	@Override
	public void updatePEC(SysPdaUserManagerModel sysPdaUserManagerModel) {
		this.updateByKey("updatePEC",sysPdaUserManagerModel);
	}

	/**
	 * 删除PEC表用户
	 */
	@Override
	public void deleteByIdsPEC(String[] aryIds) {
		this.deleteByKey("deleteByIdsPEC", aryIds);
	}

	/**
	 * 避免PEC用户冲突
	 */
	@Override
	public Integer selectPrimaryKeyPEC(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKeyPEC", sysPdaUserManagerModel);
	}
	
	
}

