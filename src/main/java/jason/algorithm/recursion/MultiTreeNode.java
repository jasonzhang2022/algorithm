package jason.algorithm.recursion;

import java.util.ArrayList;
import java.util.LinkedList;

import jason.datastructure.disjointset.TarjanOfflineLCA.Node;


public class MultiTreeNode  {
	String name;
	LinkedList< MultiTreeNode> children=new LinkedList<>();
	 MultiTreeNode parent;
	public  MultiTreeNode(String name,  MultiTreeNode parent) {
		super();
		this.name = name;
		this.parent = parent;
	}
	
	
	public MultiTreeNode(String name) {
		super();
		this.name = name;
	}


	public String toString(){
		StringBuilder sb=new StringBuilder();
		if (this.name!=null){
			sb.append(this.name);
		}
		if (!this.children.isEmpty()){
			if (this.name!=null){
				sb.append("(");
			}
		
			boolean firstone=true;
			for(MultiTreeNode node: this.children){
				if (!firstone){
					sb.append(",");
				}
				sb.append(node.toString());
				if (firstone){
					firstone=false;
				}
			}
			if (this.name!=null){
				sb.append(")");
			}
		}
		
		return sb.toString();
	}
	
	public Node ToTarjanNode(){
		Node node=new Node();
		node.c=this.name;
		node.children=new ArrayList<>(children.size());
		for (MultiTreeNode m: children){
			Node c=m.ToTarjanNode();
			c.parent=node;
			node.children.add(c);
		}
		return node;
	}
	

}