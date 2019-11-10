package com.hotent.portal.persistence.dao;

import com.hotent.base.db.api.Dao;
import com.hotent.portal.persistence.model.SysIndexMyLayout;

public interface SysIndexMyLayoutDao extends Dao<String, SysIndexMyLayout> {


	SysIndexMyLayout getByUserId(String userId);

}
