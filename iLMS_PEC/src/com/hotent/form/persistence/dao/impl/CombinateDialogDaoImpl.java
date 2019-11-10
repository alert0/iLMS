package com.hotent.form.persistence.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.CombinateDialogDao;
import com.hotent.form.persistence.model.CombinateDialog;

/**
 * 
 * <pre> 
 * 描述：combinate_dialog DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:liyijun@jee-soft.cn
 * 日期:2016-03-04 15:48:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class CombinateDialogDaoImpl extends MyBatisDaoImpl<String, CombinateDialog> implements CombinateDialogDao{

    @Override
    public String getNamespace() {
        return CombinateDialog.class.getName();
    }
	
}

