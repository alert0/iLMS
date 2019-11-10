/**
 * 
 */
package com.hanthink.gps.pub.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *Create on 2016-4-18
 *@author 谭皓文
 *
 */
public class Node {
	/**
	 * 节点ID
	 */
	public String id;
	/**
	 * 节点等级
	 */
	public String level;
	/**
	 * 节点描述
	 */
	public String menuName;
	/**
	 * 是否勾选
	 */
	public boolean checked;
	/**
	 * 父节点编号
	 */
	public String parentId;
	// 添加孩子节点   
	public void addChild(Node node) {   
		this.children.addChild(node);   
    }   


	
	/**
	 * 孩子节点列表
	 */
	private Children children = new Children();
	
	public String toString() {     
		String result = "{"   
		+ "id : '" + id + "'"   
		+ ", level : '" + level + "'"
		+ ", text : '" + menuName + "'"
		+ ", checked : " + checked + ""
		+ ", parentId : '" + parentId + "'";
		
		if (children != null && children.getSize() != 0) {   
			result += ", children : " + children.toString();
			result += ", leaf : false";
		} else {   
			result += ", leaf : true";   
		}   
		      
			return result + "}";   
		 }   

	
	class Children {   
		private List list = new ArrayList();   
		  
		public int getSize() {   
			return list.size();   
		}   
		    
		public void addChild(Node node) {   
			list.add(node);   
		}
		
		// 拼接孩子节点的JSON字符串   
		public String toString() {   
			String result = "[";     
			for (Iterator it = list.iterator(); it.hasNext();) {   
			    result += ((Node) it.next()).toString();   
				result += ",";   
			}   
			result = result.substring(0, result.length() - 1);   
			result += "]";   
			return result;   
		}   

	}
	

}
