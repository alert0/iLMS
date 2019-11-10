package com.hotent.bpmx.plugin.usercalc.cuserrel.context;

import org.w3c.dom.Element;

import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractUserCalcPluginContext;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.UserRelPluginDef;
import com.hotent.bpmx.plugin.usercalc.cuserrel.runtime.UserRelPlugin;

import net.sf.json.JSONObject;

public class UserRelPluginContext extends AbstractUserCalcPluginContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4457193787305853780L;

	@Override
	public String getDescription() {
		UserRelPluginDef def=(UserRelPluginDef)this.getBpmPluginDef();
		if(def==null) return "";
		String source=def.getSource();
		String str="";
		if("start".equals(source)){
			str="发起人";
		}
		else if("prev".equals(source)){
			str="上一步执行人";
		}
		else if("spec".equals(source)){
			str=def.getUserName();
		}
		else if("var".equals(source)){
			str=def.getVar().getSource()+"["+def.getVar().getExecutorType()+":"+def.getVar().getName()+"]";
		}
		
		str=str +"关系类型[" + def.getRelationName() +"],关系方:" + def.getRelationPartyName(); 
		
		
		return str;
	}
	
	

	

	@Override
	public String getTitle() {
		return "用户关系";
	}

	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return UserRelPlugin.class;
	}

	//	<userRel xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/userRel"
	//    type="start" relationKey="grade" relationParty="xiaji" logicCal="or" extract="no">
	//    <members account="" userName=""/>
	//    <var name=""/>  
	//</userRel>
	@Override
	public String getPluginXml() {
		UserRelPluginDef def=(UserRelPluginDef)this.getBpmPluginDef();
		if(def==null) return "";
		StringBuffer sb=new StringBuffer();
		sb.append("<userRel xmlns=\"http://www.jee-soft.cn/bpm/plugins/userCalc/userRel\" ");
		sb.append(" source=\""+ def.getSource() +"\"  relationName=\""+def.getRelationName()+"\"  relationKey=\""+ def.getRelationKey() 
				+"\" relationParty=\""+def.getRelationParty()+"\" relationPartyName=\"" + def.getRelationPartyName()
				+		"\" logicCal=\""+ def.getLogicCal().getKey() 
				+"\" extract=\""+def.getExtract().getKey()+"\"> ");
		if("spec".equals(def.getSource())){
			sb.append("<members account=\""+ def.getAccount() +"\" userName=\""+ def.getUserName()+"\"/>");
		}
		
		if("var".equals(def.getSource())){
			sb.append("<var source=\""+def.getVar().getSource()+"\" name=\""+def.getVar().getName()+"\"   " +
					" executorType=\""+def.getVar().getExecutorType()+"\" userValType=\""+def.getVar().getUserValType()+"\" " +
					" groupValType=\""+def.getVar().getGroupValType()+"\"	dimension=\""+def.getVar().getDimension()+"\" />  ");
		}
		 
		sb.append("</userRel>");
		
		return sb.toString();
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		UserRelPluginDef def=new UserRelPluginDef();
		
		String type=element.getAttribute("source");
		String relationKey=element.getAttribute("relationKey");
		String relationName=element.getAttribute("relationName");
		
		String relationParty=element.getAttribute("relationParty");
		String relationPartyName=element.getAttribute("relationPartyName");
	
		
		def.setSource(type);
		def.setRelationKey(relationKey);
		def.setRelationName(relationName);
		def.setRelationParty(relationParty);
		def.setRelationPartyName(relationPartyName);
		
		if("spec".equals(type)){
			Element memberEl=XmlUtil.getChildNodeByName(element, "members");
			String account=memberEl.getAttribute("account");
			String userName=memberEl.getAttribute("userName");
			def.setAccount(account);
			def.setUserName(userName);
		}
		
		if("var".equals(type)){
			Element varEl=XmlUtil.getChildNodeByName(element, "var");
			String name=varEl.getAttribute("name");
			String source=varEl.getAttribute("source");
			String executorType=varEl.getAttribute("executorType");
			String userValType=varEl.getAttribute("userValType");
			String groupValType=varEl.getAttribute("groupValType");
			String dimension=varEl.getAttribute("dimension");
			ExecutorVar executorVar = new ExecutorVar(source, name, executorType,userValType,groupValType, dimension);
			def.setExecutorVar(executorVar); 
		}

		return def;
	}

	//{"account":"","extractType":"EXACT_NOEXACT","logicType":"OR",
	//"pluginName":"","relationKey":"","relationParty":"","type":"","userName":"","var":""}
	@Override
	protected BpmPluginDef parseJson(JSONObject obj) {
		UserRelPluginDef def=new UserRelPluginDef();
		
		String source=obj.getString("source");
		String relationKey=obj.getString("relationKey");
		String relationName=obj.getString("relationName");
		String relationParty=obj.getString("relationParty");
		String relationPartyName=obj.getString("relationPartyName");
		
		
		def.setSource(source);
		def.setRelationKey(relationKey);
		def.setRelationName(relationName);
		def.setRelationParty(relationParty);
		def.setRelationPartyName(relationPartyName);
		
		if("var".equals(source)){
			JSONObject var=obj.getJSONObject("var");
			ExecutorVar executorVar = (ExecutorVar) JSONObject.toBean(var,ExecutorVar.class);
			def.setExecutorVar(executorVar);
		}
		
		if("spec".equals("")){
			String account=obj.getString("account");
			String userName=obj.getString("userName");
			
			def.setAccount(account);
			def.setUserName(userName);
		}
		
		return def;
	}
	
	public static void main(String[] args) {

	}

}
