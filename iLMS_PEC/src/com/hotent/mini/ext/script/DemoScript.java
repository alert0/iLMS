package com.hotent.mini.ext.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.script.IScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.webservice.api.IBoService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.sys.api.identity.IdentityService;

@Component
public class DemoScript implements IScript {
	private static final String Integer = null;
	@Resource
	IdentityService identityService;
	@Resource
	RoleManager roleManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoDataService boDataService;
	@Resource
	OrgManager orgManager;
	@Resource
	OrgRelManager orgRelManager;

	private IBoService boServive;

	/**
	 * 根据流程实例ID更新数据
	 * @param instanceId 流程实例id
	 */
	public void updateData(String instanceId ){
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(instanceId);
		List<BoData> boDatas =boDataService.getDataByInst(instance);
		JSONObject jsondata=BoDataUtil.hanlerData(boDatas);
		Double deductionsMoney=0.0;
		if(jsondata!=null ){
			if(jsondata.containsKey("jzddx")){
				JSONObject jobj=jsondata.getJSONObject("jzddx");
				deductionsMoney=jobj.getDouble("jkje");
				String	bmid=jobj.getString("jkbmID");
				String sql="select count(*)  from W_ysgl  where F_bmmcID=?";
				JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
				Object[] obj=new Object[]{bmid};
				Integer count=jdbcTemplate.queryForObject(sql, obj,Integer.class);
				if(count>0){
					//更新预算表数据
					if(deductionsMoney>0 && !"".equals(bmid)){
						obj=new Object[]{bmid};
						sql="SELECT F_ysje from W_ysgl WHERE F_bmmcID=?";
						Double rtn=jdbcTemplate.queryForObject(sql, obj,Double.class);
						if(rtn>=deductionsMoney){
							Object[] aryObj=new Object[]{deductionsMoney,deductionsMoney,bmid};
							sql="UPDATE W_ysgl SET F_ysje=F_ysje-?,F_djje=F_djje+?  where F_bmmcID=?";
							jdbcTemplate.update(sql, aryObj);
						}else{
							throw new RuntimeException(jobj.getString("jkbm")+"预算不足,流程提交失败");
						}
					}
				}
			}else if(jsondata.containsKey("bxsqd")){
				JSONObject jobj=jsondata.getJSONObject("bxsqd");
				String jzdbh=jobj.getString("jzdbh");
				String sql="SELECT F_jkbmID,F_jkje FROM W_jzdst  WHERE F_jzdhm  in (?)";
				Object[] obj=new Object[]{jzdbh};
				JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
				List<Map<String, Object>>  list= jdbcTemplate.queryForList(sql, obj);
				//得到借款部门ID和借款金额
				Map<String, Double> mapList=new HashMap<String, Double>();
				for (Map<String, Object> map : list) {
					if(!mapList.containsKey(map.get("F_jkbmID"))){
						mapList.put(map.get("F_jkbmID")+"",Double.parseDouble(map.get("F_jkje")+""));
					}else{
						double num=mapList.get(map.get("F_jkbmID"))+Double.parseDouble(map.get("F_jkje")+"");
						mapList.put(map.get("F_jkbmID")+"", num);
					}
				}
				//得到费用分配明细
				JSONArray array=jobj.getJSONArray("sub_fyfpmx");
				int frequency=array.size();
				for (int i = 0; i < array.size(); i++) {
					JSONObject j=array.getJSONObject(i);
					String bmid=j.getString("bmid");
					double cdfyje=j.getDoubleValue("cdfyje");
					obj=new Object[]{bmid};
					jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
					sql="SELECT count(*)  from W_ysgl WHERE F_bmmcID=?";
					Integer count=jdbcTemplate.queryForObject(sql, obj,Integer.class);
					if(count<=0) return;
					sql="SELECT F_ysje from W_ysgl WHERE F_bmmcID=?";
					Double rtn=jdbcTemplate.queryForObject(sql, obj,Double.class);
					if(rtn<cdfyje){
						frequency=frequency-1; 
						return;
					}
				}
				if(frequency<array.size()){
					throw new RuntimeException("预算不足,流程提交失败");
				}else{
					for (int i = 0; i < array.size(); i++) {
						JSONObject j=array.getJSONObject(i);
						String bmid=j.getString("bmid");
						sql="UPDATE W_ysgl SET F_ysje=F_ysje-?,F_ybxje=F_ybxje+?  where F_bmmcID=?";
						obj=new Object[]{j.getDouble("cdfyje"),j.getDouble("cdfyje"),bmid};
						//如果该部门有借支
						if(mapList.containsKey(bmid)){
							sql="UPDATE W_ysgl SET F_ysje=F_ysje-?+?+,F_ybxje=F_ybxje+?, F_djje=F_djje-? where F_bmmcID=?";
							double jzje=mapList.get(bmid);
							obj=new Object[]{j.getDouble("cdfyje"),jzje,j.getDouble("cdfyje"),jzje,bmid};
						}
						jdbcTemplate.update(sql, obj);
					}
				}

			}
		}
	}
}
