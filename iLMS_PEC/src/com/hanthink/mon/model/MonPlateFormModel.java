package com.hanthink.mon.model;

import java.util.List;

import com.hotent.base.core.model.AbstractModel;

/**
 * 站台调度Model
 * @author WY
 *
 */
public class MonPlateFormModel extends AbstractModel<String>{

	private static final long serialVersionUID = 4843548741968260031L;
	
	private String id;
	
	/**
	 * 站台
	 */
	private String plateForm;
	
	private List<MonRouteModel> routes; 
	
	public String getPlateForm() {
		return plateForm;
	}
	public void setPlateForm(String plateForm) {
		this.plateForm = plateForm;
	}
	public List<MonRouteModel> getRoutes() {
		return routes;
	}
	public void setRoutes(List<MonRouteModel> routes) {
		this.routes = routes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
