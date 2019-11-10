package com.hotent.sys.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.DefaultFile;



public interface FileDao extends Dao<String, DefaultFile> {

	List<DefaultFile> getAllByExt(String[] allowFiles);

	String getFileSuffixInParams(Map<String, String> map);
}
