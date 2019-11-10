package com.hotent.bpmx.persistence.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.poi.ss.formula.functions.T;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.FileUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SubProcessNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.DefaultJumpRule;
import com.hotent.bpmx.core.defxml.DefXmlUtil;
import com.hotent.bpmx.core.defxml.entity.Process;
import com.hotent.bpmx.core.defxml.entity.RootElement;
import com.hotent.bpmx.core.defxml.entity.ext.ExtDefinitions;
import com.hotent.bpmx.persistence.model.BpmDefData;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDef;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.util.BpmProcessDefExtParse;

/**
 * 
 * <pre> 
 * 描述：流程定义描述访问器
 * 构建组：x5-bpmx-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-2-11-下午8:43:14
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 *
 */
public class DefaultBpmDefinitionAccessor implements BpmDefinitionAccessor {
	
	
	@Resource
	ICache<String> iCache;
	
	@Resource
	private BpmDefinitionManager bpmDefinitionManager;
	

	@Override
	public List<BpmNodeDef> getNodeDefs(String processDefinitionId) {
		DefaultBpmProcessDef processDef= getProcessDefByDefId(processDefinitionId);
		return processDef.getBpmnNodeDefs();
	}

	@Override
	public BpmNodeDef getBpmNodeDef(String processDefinitionId, String nodeId) {
		List<BpmNodeDef> list=  getNodeDefs(processDefinitionId);
		List<SubProcessNodeDef> listSub=new ArrayList<SubProcessNodeDef>();
		for(BpmNodeDef nodeDef:list){
			if(nodeDef.getNodeId().equals(nodeId)){
				if(nodeDef instanceof UserTaskNodeDef){//解决nullPoint exception的BUG
					UserTaskNodeDef utn = (UserTaskNodeDef)nodeDef;
					if(utn.getJumpRuleList()==null){
						utn.setJumpRuleList(new ArrayList<DefaultJumpRule>());
					}
				}
				return nodeDef;
			}
			if(nodeDef instanceof SubProcessNodeDef){
				listSub.add((SubProcessNodeDef)nodeDef);
			}
		}
		if(listSub.size()>0)
			return findSubProcessNodeDefByNodeId(listSub,nodeId);
		return null;
	}
	
	private BpmNodeDef findSubProcessNodeDefByNodeId(List<SubProcessNodeDef> subList,String nodeId){
		for(SubProcessNodeDef nodeDef:subList){
			List<BpmNodeDef> nodeList= nodeDef.getChildBpmProcessDef().getBpmnNodeDefs();
			List<SubProcessNodeDef> nestSub=new ArrayList<SubProcessNodeDef>();
			for(BpmNodeDef tmpDef:nodeList){
				if(tmpDef.getNodeId().equals(nodeId)){
					return tmpDef;
				}
				if(tmpDef instanceof SubProcessNodeDef){
					nestSub.add((SubProcessNodeDef)tmpDef);
				}
			}
			if(nestSub.size()>0) //如果多级，进行递归
				return findSubProcessNodeDefByNodeId(nestSub,nodeId);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BpmProcessDef getBpmProcessDef(String processDefId) {
		return getProcessDefByDefId(processDefId);
	}

	
	
	@Override
	public BpmNodeDef getStartEvent(String processDefId) {
		DefaultBpmProcessDef processDef = getProcessDefByDefId(processDefId);
		List<BpmNodeDef> list= processDef.getBpmnNodeDefs();
		for(BpmNodeDef nodeDef:list){
			if(nodeDef.getType().equals(NodeType.START))
				return nodeDef;
		}
		return null;
	}

	@Override
	public List<BpmNodeDef> getEndEvents(String processDefId) {
		List<BpmNodeDef> nodeList=new ArrayList<BpmNodeDef>();
		DefaultBpmProcessDef processDef=getProcessDefByDefId(processDefId);
		List<BpmNodeDef> list= processDef.getBpmnNodeDefs();
		for(BpmNodeDef nodeDef:list){
			if(nodeDef.getType().equals(NodeType.END)){
				nodeList.add(nodeDef);
			}
		}
		return nodeList;
	}

	@Override
	public void clean(String processDefId) {
		iCache.delByKey(BpmConstants.PROCDEF_PREFIX +processDefId);
	}

	@Override
	public List<BpmNodeDef> getStartNodes(String processDefId) {
		BpmNodeDef nodeDef=getStartEvent(processDefId);
		return nodeDef.getOutcomeNodes();
		
	}

	@Override
	public boolean isStartNode(String defId, String nodeId) {
		List<BpmNodeDef> nodes= getStartNodes(defId);
		for(BpmNodeDef node:nodes){
			if(node.getNodeId().equals(nodeId)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validNodeDefType(String defId, String nodeId,
			NodeType nodeDefType) {
		BpmNodeDef nodeDef= getBpmNodeDef(defId,nodeId);
		return nodeDef.getType().equals(nodeDefType);
		
	}

	@Override
	public boolean isContainCallActivity(String defId) {
		DefaultBpmProcessDef processDef=getProcessDefByDefId(defId);
		List<BpmNodeDef> list= processDef.getBpmnNodeDefs();
		for(BpmNodeDef nodeDef:list){
			if(nodeDef.getType().equals(NodeType.CALLACTIVITY)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 根据流程定义ID取得流程定义数据。
	 * @param processDefinitionId
	 * @return	DefaultBpmProcessDef
	 */
	private synchronized  DefaultBpmProcessDef getProcessDefByDefId(String processDefinitionId){
		return getProcessDefByDefId(processDefinitionId, true);
	}
	
	private synchronized  DefaultBpmProcessDef getProcessDefByDefId(String processDefinitionId,Boolean isCache){
		DefaultBpmProcessDef bpmProcessDef = null;
		if(isCache)	{
			try {
				String xml = (String) iCache.getByKey(BpmConstants.PROCDEF_PREFIX + processDefinitionId);
				if(!StringUtils.isEmpty(xml)){
					bpmProcessDef = getByBpmnXml(processDefinitionId,xml);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		if(bpmProcessDef!=null)
			return bpmProcessDef ;
		DefaultBpmDefinition bpmDef= bpmDefinitionManager.getById(processDefinitionId);
	    BpmDefData data= bpmDef.getBpmDefData();
		try {
			bpmProcessDef = getByBpmnXml(processDefinitionId,data.getBpmnXml());
			// 暂时这样， 正常的做法要将testStatus写入到xml中
			bpmProcessDef.getProcessDefExt().getExtProperties().setTestStatus(bpmDef.getTestStatus());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//if( bpmProcessDef instanceof Serializable) return true;
		if(isCache)
			iCache.add(BpmConstants.PROCDEF_PREFIX + processDefinitionId,data.getBpmnXml());
		return bpmProcessDef;
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
	public DefaultBpmProcessDef getByBpmnXml(String processDefinitionId,String bpmnXml) throws JAXBException, IOException{
		
		ExtDefinitions definition = DefXmlUtil.getDefinitionsByXml(bpmnXml);
		if(definition==null)
			return null;
		List<Process> list = getProcess(definition);
		if(list.size()!=1)
			return null;
		Process process=list.get(0);
		
		DefaultBpmProcessDef bpmProcessDef=new DefaultBpmProcessDef();
		bpmProcessDef.setProcessDefinitionId(processDefinitionId);
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
	private  List<Process> getProcess(ExtDefinitions definitions) throws JAXBException, IOException{
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
	
	
	@Override
	public List<BpmNodeDef> getNodesByType(String processDefinitionId,NodeType nodeType) {
		DefaultBpmProcessDef processDef=getProcessDefByDefId(processDefinitionId);
		List<BpmNodeDef> list= processDef.getBpmnNodeDefs();
		List<BpmNodeDef> rtnList=new ArrayList<BpmNodeDef>();
		for(BpmNodeDef nodeDef:list){
			if(nodeDef.getType().equals(nodeType)){
				rtnList.add(nodeDef);
			}
		}
		return rtnList;
	}

	

	@Override
	public List<BpmNodeDef> getAllNodeDef(String processDefinitionId) {
		List<BpmNodeDef> bpmNodeDefs=this.getNodeDefs(processDefinitionId);
		List<BpmNodeDef> rtnList=new ArrayList<BpmNodeDef>();
		this.getBpmNodeDefs(bpmNodeDefs,rtnList);
		return rtnList;
	}
	
	
	
	private void getBpmNodeDefs(List<BpmNodeDef> bpmNodeDefs, List<BpmNodeDef> rtnList) {
		for(BpmNodeDef def:bpmNodeDefs){
			rtnList.add(def);
			if(!NodeType.SUBPROCESS.equals(def.getType())) continue;
			SubProcessNodeDef subProcessNodeDef=(SubProcessNodeDef)def;
			BpmProcessDef<? extends BpmProcessDefExt> processDef= subProcessNodeDef.getChildBpmProcessDef();
			if(processDef == null) continue; 
			List<BpmNodeDef> subBpmNodeDefs=processDef.getBpmnNodeDefs();
			this.getBpmNodeDefs(subBpmNodeDefs,rtnList);
		}
	}
	
	/**
	 * 仅仅获取开始、普通、会签的节点定义
	 * @param processDefinitionId
	 * @return 
	 */
	public List<BpmNodeDef> getSignUserNode(String processDefinitionId){
		List<BpmNodeDef> bpmNodeDefs = this.getAllNodeDef(processDefinitionId);
		List<BpmNodeDef> rtnList = new ArrayList<BpmNodeDef>();
		for(BpmNodeDef bnd :bpmNodeDefs){
			if(bnd.getType().equals(NodeType.START) || bnd.getType().equals(NodeType.SIGNTASK) || bnd.getType().equals(NodeType.USERTASK)){
				rtnList.add(bnd);
			}
		}
		return rtnList;
	}
	
	/**
	 * 测试的方法
	 * @param args
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static void main(String[] args) throws JAXBException, IOException {
		String fileName="G:/bpmnXml.xml";
		String bpmnXml=FileUtil.readFile(fileName);
		DefaultBpmDefinitionAccessor p=new DefaultBpmDefinitionAccessor();
		DefaultBpmProcessDef def=p.getByBpmnXml("aaaa0001", bpmnXml);
		DefaultBpmProcessDefExt ext= def.getProcessDefExt();
		//	Form frm= ext.getGlobalForm();
		BpmDefExtProperties prop= ext.getExtProperties();
		
		System.out.println(prop.isAllowExecutorEmpty() +"," + prop.isSkipExecutorEmpty());
	}
}


