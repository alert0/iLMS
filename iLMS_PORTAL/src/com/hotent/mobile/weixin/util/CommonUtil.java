package com.hotent.mobile.weixin.util;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mobile.weixin.model.ListModel;

public class CommonUtil {
	
	public static ListModel getListModel(PageList list){
		ListModel model=new ListModel();
		model.setRowList(list);
		PageList pageList=(PageList)list;
		model.setTotal(pageList.getPageResult().getTotalPages());
		return model;
	}

}
