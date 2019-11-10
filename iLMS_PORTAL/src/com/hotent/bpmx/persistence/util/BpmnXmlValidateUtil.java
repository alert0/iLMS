package com.hotent.bpmx.persistence.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import net.sf.json.JSONObject;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SubProcessNodeDef;
import com.hotent.bpmx.core.defxml.DefXmlUtil;
import com.hotent.bpmx.core.defxml.entity.Definitions;
import com.hotent.bpmx.core.defxml.entity.Process;
import com.hotent.bpmx.core.defxml.entity.RootElement;
import com.hotent.bpmx.core.defxml.entity.ext.ExtDefinitions;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDef;
public class BpmnXmlValidateUtil {
	
	public static JSONObject vilateBpmXml(List<BpmNodeDef> nodeDefs){
		boolean isTrue = false;
		JSONObject msg = new JSONObject();
		JSONObject errors = new JSONObject();
		isTrue = vilate(nodeDefs,errors);
		msg.accumulate("isTrue", isTrue);
		String errorMsg = isTrue? "":getResultMsg(errors);
		msg.accumulate("errorMsgs",errorMsg);
		return msg;
	}
	
	private static boolean vilate(List<BpmNodeDef> nodeDefs,JSONObject errors){
		boolean isTrue = false;
		//验证流程连线完整性
		boolean isSequenceTrue = vilateSequence(nodeDefs,errors);
		//验证网关是否成对出现(同步网关和条件网关)
		boolean isGateway = vilateGateways(nodeDefs,errors);
		//验证子流程
		boolean isSubProcess = vilateSubProcess(nodeDefs,errors);
		
		if(isSequenceTrue&&isGateway&&isSubProcess){
			isTrue = true;
		}
		return isTrue;
	}
	
	@SuppressWarnings("unchecked")
	private static String getResultMsg(JSONObject errors){
		StringBuffer msg = new StringBuffer();
		int index = 1;
		Iterator<String> sIterator = errors.keys();  
		while(sIterator.hasNext()){  
		    String key = sIterator.next();  
		    String value = errors.getString(key);  
		    msg.append("错误消息"+index+"、");
		    msg.append(value);
		    msg.append("；");
		    ++index;
		}  
		return msg.toString();
	}
	
	public static List<BpmNodeDef> getNodeDefs(String bpmnXml){
		DefaultBpmProcessDef bpmProcessDef = null;
		try {
			bpmProcessDef = getByBpmnXml(bpmnXml);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bpmProcessDef.getBpmnNodeDefs();
	}
	
	/**
	 * 根据流程的xml获取流程定义访问接口类。
	 * @param processDefinitionId	流程定义ID
	 * @param bpmnXml				流程定义文件。
	 * @return
	 * @throws JAXBException
	 * @throws IOException 
	 * DefaultBpmProcessDef
	 */
	public static DefaultBpmProcessDef getByBpmnXml(String bpmnXml) throws JAXBException, IOException{
		ExtDefinitions definition = DefXmlUtil.getDefinitionsByXml(bpmnXml);
		if(definition==null)
			return null;
		List<Process> list = getProcess(definition);
		if(list.size()!=1)
			return null;
		Process process=list.get(0);
		
		DefaultBpmProcessDef bpmProcessDef=new DefaultBpmProcessDef();
		bpmProcessDef.setName(process.getName());
		bpmProcessDef.setDefKey(process.getId());
		
		BpmProcessDefExtParse processDefExtParse = BpmProcessDefExtParse.getInstance();
		processDefExtParse.handProcessDef(bpmProcessDef,definition,process);
		return bpmProcessDef;
	}
	
	/**
	 * 根据 ExtDefinitions 获取Process。
	 * @param definitions
	 * @return
	 * @throws JAXBException
	 * @throws IOException 
	 * List<Process>
	 */
	private static  List<Process> getProcess(Definitions definitions) throws JAXBException, IOException{
		List<Process> processes = new ArrayList<Process>();
		List<JAXBElement<? extends RootElement>> bPMNElements =definitions.getRootElement();
		for(JAXBElement<? extends RootElement> jAXBe:bPMNElements){
			RootElement element =  jAXBe.getValue();
			if(element instanceof Process){
				processes.add((Process)element);
			}
		}
		return processes;
	}	
	
	private static boolean vilateGateways(List<BpmNodeDef> nodeDefs,JSONObject errors){
		boolean isTrue = true;
		int inclus = 0;
		int parallels = 0;
		for (BpmNodeDef bpmNodeDef : nodeDefs) {
			if(bpmNodeDef.getType().equals(NodeType.PARALLELGATEWAY)){
				++parallels;
			}else if(bpmNodeDef.getType().equals(NodeType.INCLUSIVEGATEWAY)){
				++inclus;
			}
		}
		if(parallels>0 && parallels%2!=0){
			isTrue = false;
			putMsgs(errors,"流程定义中同步网关未成对出现","tbwg");
		}
		if(inclus>0 && inclus%2!=0){
			isTrue = false;
			putMsgs(errors,"流程定义中条件网关未成对出现","tjwg");
		}
		return isTrue;
	}
	
	
	private static boolean vilateSequence(List<BpmNodeDef> nodeDefs,JSONObject errors){
		boolean isTrue = true;
		boolean isStart = false;
		boolean isEnd = false;
		BpmNodeDef parentBpmNodeDef = null;
		for (BpmNodeDef bpmNodeDef : nodeDefs) {
			if(!(BeanUtils.isNotEmpty(bpmNodeDef.getIncomeNodes())&&BeanUtils.isNotEmpty(bpmNodeDef.getOutcomeNodes()))
					&&!bpmNodeDef.getType().equals(NodeType.START) && !bpmNodeDef.getType().equals(NodeType.END) ){
				if(BeanUtils.isEmpty(bpmNodeDef.getIncomeNodes())&&BeanUtils.isEmpty(bpmNodeDef.getOutcomeNodes())){
					putMsgs(errors,"节点【"+bpmNodeDef.getName()+"】缺少流入和流出",bpmNodeDef.getName());
				}else if(BeanUtils.isEmpty(bpmNodeDef.getIncomeNodes())&&BeanUtils.isNotEmpty(bpmNodeDef.getOutcomeNodes())){
					putMsgs(errors,"节点【"+bpmNodeDef.getName()+"】缺少流入",bpmNodeDef.getName());
				}else{
					putMsgs(errors,"节点【"+bpmNodeDef.getName()+"】缺少流出",bpmNodeDef.getName());
				}
				isTrue = false;
				if(BeanUtils.isEmpty(parentBpmNodeDef)&&BeanUtils.isNotEmpty(bpmNodeDef.getParentBpmNodeDef())){
					parentBpmNodeDef = bpmNodeDef.getParentBpmNodeDef();
				}
			}else if(BeanUtils.isEmpty(bpmNodeDef.getOutcomeNodes())&&bpmNodeDef.getType().equals(NodeType.START)){
				putMsgs(errors,"流程定义中存在无效的开始节点【"+bpmNodeDef.getName()+"】（缺少流出）",bpmNodeDef.getName());
				isTrue = false;
				isStart = true;
			}else if(BeanUtils.isEmpty(bpmNodeDef.getIncomeNodes())&&bpmNodeDef.getType().equals(NodeType.END)){
				putMsgs(errors,"流程定义中存在无效的结束节点【"+bpmNodeDef.getName()+"】（缺少流入）",bpmNodeDef.getName());
				isTrue = false;
				isEnd = true;
			}else if(bpmNodeDef.getType().equals(NodeType.END)){
				isEnd = true;
			}else if(bpmNodeDef.getType().equals(NodeType.START)){
				isStart = true;
				List<BpmNodeDef> outNodes = bpmNodeDef.getOutcomeNodes();
				for (BpmNodeDef outNodeDef : outNodes) {
					if(!outNodeDef.getType().equals(NodeType.USERTASK)){
						isTrue = false;
						putMsgs(errors,"开始节点【"+bpmNodeDef.getName()+"】连接的节点【"+outNodeDef.getName()+"】为非用户任务（开始节点只能连接用户任务）",bpmNodeDef.getName());
					}
				}
			}
		}
		if(!isEnd){
			if(BeanUtils.isNotEmpty(parentBpmNodeDef)){
				putMsgs(errors,"【"+parentBpmNodeDef.getName()+"】中缺少结束节点","end"+parentBpmNodeDef.getNodeId());
			}else{
				putMsgs(errors,"流程定义中缺少结束节点","end");
			}
			isTrue = false;
		}
		if(!isStart){
			if(BeanUtils.isNotEmpty(parentBpmNodeDef)){
				putMsgs(errors,"【"+parentBpmNodeDef.getName()+"】中缺少开始节点","start"+parentBpmNodeDef.getNodeId());
			}else{
				putMsgs(errors,"流程定义中缺少开始节点","start");
			}
			isTrue = false;
		}
		return isTrue;
	}
	
	
	private static boolean vilateSubProcess(List<BpmNodeDef> nodeDefs,JSONObject errors){
		boolean isTrue = true;
		for (BpmNodeDef bpmNodeDef : nodeDefs) {
			if(bpmNodeDef.getType().equals(NodeType.SUBPROCESS)&&bpmNodeDef instanceof SubProcessNodeDef){
				SubProcessNodeDef node = (SubProcessNodeDef)bpmNodeDef;
				List<BpmNodeDef> subProNodes =  node.getChildBpmProcessDef().getBpmnNodeDefs();
				if(!vilate(subProNodes,errors)){
					isTrue = false;
				}
			}
		}
		return isTrue;
	}
	
	private static void putMsgs(JSONObject errors,String msg,String node){
		errors.accumulate(node,msg);
	}
}