package com.hotent.system.worktime.manager;

import java.io.Serializable;
import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.CalendarShift;


public interface CalendarShiftManager  extends Manager<String, CalendarShift>{
	
	void setDefaultShift(String id);
	
}

